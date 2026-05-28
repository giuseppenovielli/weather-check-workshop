package com.weathercheck.core;

import org.junit.jupiter.api.Test;

import com.weathercheck.shared.units.UnitSystem;
import com.weathercheck.shared.units.UnitSystemResolver;

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
