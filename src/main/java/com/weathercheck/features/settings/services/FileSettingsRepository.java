package com.weathercheck.features.settings.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weathercheck.shared.config.AppSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSettingsRepository implements SettingsRepository {
    private static final Logger log = LoggerFactory.getLogger(FileSettingsRepository.class);
    private final Path settingsPath;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileSettingsRepository(Path settingsPath) {
        this.settingsPath = settingsPath;
    }

    @Override
    public AppSettings load() {
        try {
            if (Files.exists(settingsPath)) {
                String json = Files.readString(settingsPath, StandardCharsets.UTF_8);
                AppSettings settings = objectMapper.readValue(json, AppSettings.class);
                if (settings != null) {
                    return settings;
                }
            }
        } catch (Exception ex) {
            log.warn("Unable to load settings, fallback to default", ex);
        }
        return AppSettings.defaults();
    }

    @Override
    public void save(AppSettings settings) {
        try {
            Files.createDirectories(settingsPath.getParent());
            Files.writeString(
                    settingsPath,
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(settings),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save settings", ex);
        }
    }
}
