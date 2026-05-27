package com.weathercheck.core.units;

import java.util.Locale;
import java.util.Set;

public final class UnitSystemResolver {
    private static final Set<String> IMPERIAL_COUNTRIES = Set.of("US", "LR", "MM");

    private UnitSystemResolver() {}

    public static UnitSystem resolve(Locale locale) {
        if (locale == null) {
            return UnitSystem.METRIC;
        }
        return IMPERIAL_COUNTRIES.contains(locale.getCountry()) ? UnitSystem.IMPERIAL : UnitSystem.METRIC;
    }
}
