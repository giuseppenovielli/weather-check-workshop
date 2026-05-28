package com.weathercheck.core;

import com.weathercheck.features.settings.services.FileSettingsRepository;
import com.weathercheck.shared.config.AppSettings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FileSettingsRepositoryTest {
    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadRoundTrip() {
        Path file = tempDir.resolve("settings.json");
        FileSettingsRepository repo = new FileSettingsRepository(file);

        AppSettings input = new AppSettings("en-US", "dark");
        repo.save(input);

        AppSettings loaded = repo.load();
        assertThat(loaded).isEqualTo(input);
    }
}
