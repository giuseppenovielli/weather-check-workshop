package com.weathercheck.core;

import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.core.units.UnitSystemResolver;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class UnitSystemResolverTest {
    @Test
    void shouldResolveImperialForUs() {
        assertThat(UnitSystemResolver.resolve(Locale.US)).isEqualTo(UnitSystem.IMPERIAL);
    }

    @Test
    void shouldResolveMetricForItaly() {
        assertThat(UnitSystemResolver.resolve(Locale.ITALY)).isEqualTo(UnitSystem.METRIC);
    }
}
