package com.weathercheck.core.config;

public record AppSettings(
        String language,
        String theme
) {
    public static AppSettings defaults() {
        return new AppSettings("it-IT", "light");
    }
}
