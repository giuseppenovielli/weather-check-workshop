package com.weathercheck.features.settings.models;

public record SettingsState(String languageTag, String theme, String locationName, double latitude, double longitude) {
}
