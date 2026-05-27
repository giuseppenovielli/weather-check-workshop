package com.weathercheck.features.weather.mappers;

import java.util.Map;

public final class WeatherCodeCatalog {
    private static final Map<Integer, String> CODES = Map.ofEntries(
            Map.entry(0, "weather.clear_sky"),
            Map.entry(1, "weather.mainly_clear"),
            Map.entry(2, "weather.partly_cloudy"),
            Map.entry(3, "weather.overcast"),
            Map.entry(45, "weather.fog"),
            Map.entry(48, "weather.rime_fog"),
            Map.entry(51, "weather.drizzle_light"),
            Map.entry(53, "weather.drizzle_moderate"),
            Map.entry(55, "weather.drizzle_dense"),
            Map.entry(61, "weather.rain_slight"),
            Map.entry(63, "weather.rain_moderate"),
            Map.entry(65, "weather.rain_heavy"),
            Map.entry(71, "weather.snow_slight"),
            Map.entry(73, "weather.snow_moderate"),
            Map.entry(75, "weather.snow_heavy"),
            Map.entry(80, "weather.showers_slight"),
            Map.entry(81, "weather.showers_moderate"),
            Map.entry(82, "weather.showers_violent"),
            Map.entry(95, "weather.thunderstorm")
    );

    private WeatherCodeCatalog() {}

    public static String keyFor(int code) {
        return CODES.getOrDefault(code, "weather.unknown");
    }
}
