package com.weathercheck.features.settings.models;

import com.weathercheck.core.models.Model;

public record SettingsState(String languageTag, String theme, String locationName, double latitude, double longitude) implements Model {
}
