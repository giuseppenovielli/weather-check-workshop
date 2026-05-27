package com.weathercheck.features.weather.providers;

import com.weathercheck.features.weather.models.GeoLocation;

import java.util.List;

public interface GeocodingProvider {
    List<GeoLocation> search(String query);
}
