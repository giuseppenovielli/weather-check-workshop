package com.weathercheck.core.services.geocoding;

import com.weathercheck.core.services.Service;
import com.weathercheck.core.services.geolocation.GeoCoordinates;

import java.util.Optional;

public interface GeocodingService extends Service {
    Optional<GeoCoordinates> getLocation(String query);
    Optional<String> getPlacemarks(double latitude, double longitude);
}
