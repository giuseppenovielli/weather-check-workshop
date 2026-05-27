package com.weathercheck.app;

import com.weathercheck.core.config.FileSettingsRepository;
import com.weathercheck.core.http.JdkHttpJsonClient;
import com.weathercheck.core.theme.ThemeManager;
import com.weathercheck.features.weather.providers.OpenMeteoWeatherProvider;

import javax.swing.*;
import java.nio.file.Path;

public class WeatherCheckApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileSettingsRepository settingsRepository = new FileSettingsRepository(
                    Path.of(System.getProperty("user.home"), ".weather-check", "settings.json")
            );
            ThemeManager themeManager = new ThemeManager();
            themeManager.applyTheme(settingsRepository.load().theme());
            JdkHttpJsonClient httpClient = new JdkHttpJsonClient();

            MainFrame frame = new MainFrame(
                    settingsRepository,
                    themeManager,
                    new OpenMeteoWeatherProvider(httpClient),
                    httpClient
            );
            frame.setVisible(true);
        });
    }
}
