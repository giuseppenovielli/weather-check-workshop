package com.weathercheck.core.config;

public record AppSettings(
        String languageTag,
        String theme,
        String locationName,
        double latitude,
        double longitude
) {
    public static AppSettings defaults() {
        return new AppSettings("it-IT", "light", "Roma", 41.8933, 12.4829);
    }
}
