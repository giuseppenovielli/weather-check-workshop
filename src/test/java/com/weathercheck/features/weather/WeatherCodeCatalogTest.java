package com.weathercheck.features.weather;

import com.weathercheck.features.weather.mappers.WeatherCodeCatalog;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherCodeCatalogTest {
    @Test
    void mapsKnownCode() {
        assertThat(WeatherCodeCatalog.keyFor(0)).isEqualTo("weather.clear_sky");
    }

    @Test
    void mapsUnknownCode() {
        assertThat(WeatherCodeCatalog.keyFor(999)).isEqualTo("weather.unknown");
    }
}
