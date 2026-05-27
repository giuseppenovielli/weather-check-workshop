package com.weathercheck.app;

import com.weathercheck.core.config.AppSettings;
import com.weathercheck.core.controllers.ControllerBase;
import com.weathercheck.core.http.HttpJsonClient;
import com.weathercheck.core.config.SettingsRepository;
import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.services.geolocation.IpGeolocationService;
import com.weathercheck.core.services.ServiceBase;
import com.weathercheck.core.theme.ThemeManager;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.core.units.UnitSystemResolver;
import com.weathercheck.features.home.controllers.HomeController;
import com.weathercheck.features.home.services.DefaultHomeService;
import com.weathercheck.features.home.services.HomeService;
import com.weathercheck.features.home.views.HomeView;
import com.weathercheck.features.map.services.InMemoryMapSelectionService;
import com.weathercheck.features.map.services.MapSelectionService;
import com.weathercheck.features.settings.controllers.SettingsController;
import com.weathercheck.features.settings.services.RepositorySettingsService;
import com.weathercheck.features.settings.services.SettingsService;
import com.weathercheck.features.settings.views.SettingsView;
import com.weathercheck.features.weather.services.openmeteo.OpenMeteoWeatherProvider;
import com.weathercheck.features.weather.services.weather.ProviderBackedWeatherService;
import com.weathercheck.features.weather.services.weather.WeatherService;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MainFrame extends JFrame {
    public MainFrame(SettingsRepository settingsRepository, ThemeManager themeManager, OpenMeteoWeatherProvider provider, HttpJsonClient httpClient) {
        SettingsService settingsService = ServiceBase.create(() -> new RepositorySettingsService(settingsRepository));
        AppSettings appSettings = settingsService.load();
        I18nManager i18n = new I18nManager(Locale.forLanguageTag(appSettings.languageTag()));
        UnitSystem unitSystem = UnitSystemResolver.resolve(i18n.getLocale());

        setTitle("weather-check");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        HomeView homeView = new HomeView(i18n, ServiceBase.create(() -> new IpGeolocationService(httpClient)));
        SettingsView settingsView = new SettingsView();

        HomeController homeController = ControllerBase.create(() -> new HomeController(
                homeView,
                ServiceBase.create(() -> new ProviderBackedWeatherService(provider)),
                ServiceBase.create(InMemoryMapSelectionService::new),
                ServiceBase.create(DefaultHomeService::new),
                i18n,
                unitSystem
        ));

        SettingsController settingsController = ControllerBase.create(
                () -> new SettingsController(
                        settingsView,
                        settingsService,
                        themeManager,
                        i18n,
                        () -> JOptionPane.showMessageDialog(this, "Settings saved")
                )
        );

        tabs.addTab(i18n.tr("nav.home"), homeView);
        tabs.addTab(i18n.tr("nav.settings"), settingsView);
        setContentPane(tabs);
    }
}
