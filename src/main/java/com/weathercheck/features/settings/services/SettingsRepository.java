package com.weathercheck.features.settings.services;

import com.weathercheck.shared.config.AppSettings;

public interface SettingsRepository {
    AppSettings load();
    void save(AppSettings settings);
}
