package com.weathercheck.features.weather.services.geocoding;

import com.weathercheck.features.weather.models.GeoLocation;

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
