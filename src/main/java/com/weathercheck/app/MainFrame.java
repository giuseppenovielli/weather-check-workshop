package com.weathercheck.app;

import com.weathercheck.features.weather.controllers.WeatherController;
import com.weathercheck.features.weather.services.OpenMeteoWeatherProvider;
import com.weathercheck.features.weather.services.WeatherService;
import com.weathercheck.features.weather.views.WeatherView;
import com.weathercheck.shared.config.AppSettings;
import com.weathercheck.shared.components.controllers.Controller;
import com.weathercheck.shared.http.HttpJsonClient;
import com.weathercheck.shared.i18n.I18nManager;
import com.weathercheck.shared.components.services.Service;
import com.weathercheck.shared.geo.geocoding.GeocodingService;
import com.weathercheck.shared.geo.geocoding.OpenMeteoGeocodingService;
import com.weathercheck.shared.geo.geocoding.OpenStreetMapGeocodingServicce;
import com.weathercheck.shared.geo.geolocation.GeolocationService;
import com.weathercheck.shared.geo.geolocation.IpGeolocationService;
import com.weathercheck.shared.theme.ThemeManager;
import com.weathercheck.shared.units.UnitSystem;
import com.weathercheck.shared.units.UnitSystemResolver;
import com.weathercheck.features.settings.controllers.SettingsController;
import com.weathercheck.features.settings.services.SettingsRepository;
import com.weathercheck.features.settings.views.SettingsView;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MainFrame extends JFrame {
    public MainFrame(SettingsRepository settingsRepository, ThemeManager themeManager, OpenMeteoWeatherProvider provider, HttpJsonClient httpClient) {
        AppSettings appSettings = settingsRepository.load();
        I18nManager i18n = new I18nManager(Locale.forLanguageTag(appSettings.language()));
        UnitSystem unitSystem = UnitSystemResolver.resolve(i18n.getLocale());

        setTitle(i18n.tr("app.title"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        WeatherView weatherView = new WeatherView(i18n);
        SettingsView settingsView = new SettingsView(i18n);
        WeatherService weatherService = provider;
        GeocodingService geocodingService = Service.create(() -> new OpenStreetMapGeocodingServicce(httpClient));
        GeolocationService geolocationService = Service.create(() -> new IpGeolocationService(httpClient));

        WeatherController weatherController = Controller.create(() -> new WeatherController(
                weatherView,
                weatherService,
                geocodingService,
                geolocationService,
                i18n,
                unitSystem
        ));

        SettingsController settingsController = Controller.create(
                () -> new SettingsController(
                        settingsView,
                        settingsRepository,
                        themeManager,
                        i18n
                )
        );
        settingsController.addSaveListener(savedSettings -> {
            weatherView.applyTranslations();
            tabs.setTitleAt(0, i18n.tr("nav.home"));
            tabs.setTitleAt(1, i18n.tr("nav.settings"));
            JOptionPane.showMessageDialog(this, i18n.tr("settings.saved"));
        });

        tabs.addTab(i18n.tr("nav.home"), weatherView);
        tabs.addTab(i18n.tr("nav.settings"), settingsView);
        setContentPane(tabs);
    }
}
