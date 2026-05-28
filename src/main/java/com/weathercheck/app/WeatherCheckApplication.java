package com.weathercheck.app;

import com.weathercheck.features.settings.services.FileSettingsRepository;
import com.weathercheck.features.weather.services.OpenMeteoWeatherProvider;
import com.weathercheck.shared.http.JdkHttpJsonClient;
import com.weathercheck.shared.theme.ThemeManager;

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
