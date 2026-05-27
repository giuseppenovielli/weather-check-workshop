package com.weathercheck.core.config;

public interface SettingsRepository {
    AppSettings load();
    void save(AppSettings settings);
}
