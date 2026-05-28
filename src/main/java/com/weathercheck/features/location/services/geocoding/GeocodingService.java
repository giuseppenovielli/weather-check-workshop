package com.weathercheck.features.location.services.geocoding;

import java.util.Optional;

import com.weathercheck.shared.components.services.Service;
import com.weathercheck.features.location.services.geolocation.GeoCoordinates;

public interface GeocodingService extends Service {
    Optional<GeoCoordinates> getLocation(String query);
    Optional<String> getPlacemarks(double latitude, double longitude);
}
