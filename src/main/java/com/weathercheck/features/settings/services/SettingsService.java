package com.weathercheck.features.settings.services;

import com.weathercheck.core.config.AppSettings;
import com.weathercheck.core.config.SettingsRepository;

public class SettingsService {
    private final SettingsRepository repository;

    public SettingsService(SettingsRepository repository) {
        this.repository = repository;
    }

    public AppSettings load() {
        return repository.load();
    }

    public void save(AppSettings settings) {
        repository.save(settings);
    }
}
