package com.weathercheck.features.weather.services;

import com.weathercheck.features.weather.models.GeoLocation;
import com.weathercheck.features.weather.providers.GeocodingProvider;

import java.util.List;

public class GeocodingService {
    private final GeocodingProvider provider;

    public GeocodingService(GeocodingProvider provider) {
        this.provider = provider;
    }

    public List<GeoLocation> search(String query) {
        return provider.search(query);
    }
}
