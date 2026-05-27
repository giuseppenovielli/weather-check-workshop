package com.weathercheck.features.settings.services;

import com.weathercheck.core.config.AppSettings;

public interface SettingsService {
    AppSettings load();
    void save(AppSettings settings);
}
