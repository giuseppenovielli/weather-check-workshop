package com.weathercheck.app;

import com.weathercheck.core.config.AppSettings;
import com.weathercheck.core.controllers.Controller;
import com.weathercheck.core.http.HttpJsonClient;
import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.services.geocoding.GeocodingService;
import com.weathercheck.core.services.geocoding.OpenMeteoGeocodingService;
import com.weathercheck.core.services.geocoding.OpenStreetMapGeocodingServicce;
import com.weathercheck.core.services.geolocation.IpGeolocationService;
import com.weathercheck.core.services.geolocation.GeolocationService;
import com.weathercheck.core.services.Service;
import com.weathercheck.core.theme.ThemeManager;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.core.units.UnitSystemResolver;
import com.weathercheck.features.weather.controllers.WeatherController;
import com.weathercheck.features.weather.services.OpenMeteoWeatherProvider;
import com.weathercheck.features.weather.services.WeatherService;
import com.weathercheck.features.weather.views.WeatherView;
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

        setTitle("Weather Check");
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
