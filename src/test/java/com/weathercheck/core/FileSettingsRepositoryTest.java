package com.weathercheck.core;

import com.weathercheck.core.config.AppSettings;
import com.weathercheck.core.config.FileSettingsRepository;
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

        AppSettings input = new AppSettings("en-US", "dark", "London", 51.5074, -0.1278);
        repo.save(input);

        AppSettings loaded = repo.load();
        assertThat(loaded).isEqualTo(input);
    }
}
