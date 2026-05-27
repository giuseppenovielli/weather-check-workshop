package com.weathercheck.features.settings.services;

import com.weathercheck.core.config.AppSettings;
import com.weathercheck.core.config.SettingsRepository;
import com.weathercheck.core.services.Service;

public class RepositorySettingsService implements Service, SettingsService {
    private final SettingsRepository repository;

    public RepositorySettingsService(SettingsRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppSettings load() {
        return repository.load();
    }

    @Override
    public void save(AppSettings settings) {
        repository.save(settings);
    }
}
