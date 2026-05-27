package com.weathercheck.features.weather.services.geocoding;

import com.weathercheck.core.services.Service;
import com.weathercheck.features.weather.models.GeoLocation;

import java.util.List;

public class ProviderBackedGeocodingService implements Service, GeocodingService {
    private final GeocodingProvider provider;

    public ProviderBackedGeocodingService(GeocodingProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<GeoLocation> search(String query) {
        return provider.search(query);
    }
}
