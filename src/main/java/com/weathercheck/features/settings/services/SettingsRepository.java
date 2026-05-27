package com.weathercheck.features.settings.services;

import com.weathercheck.core.config.AppSettings;

public interface SettingsRepository {
    AppSettings load();
    void save(AppSettings settings);
}
