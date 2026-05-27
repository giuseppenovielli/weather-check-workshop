package com.weathercheck.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSettingsRepository implements SettingsRepository {
    private static final Logger log = LoggerFactory.getLogger(FileSettingsRepository.class);
    private final Path settingsPath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public FileSettingsRepository(Path settingsPath) {
        this.settingsPath = settingsPath;
    }

    @Override
    public AppSettings load() {
        try {
            if (Files.exists(settingsPath)) {
                String json = Files.readString(settingsPath, StandardCharsets.UTF_8);
                AppSettings settings = gson.fromJson(json, AppSettings.class);
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
            Files.writeString(settingsPath, gson.toJson(settings), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save settings", ex);
        }
    }
}
