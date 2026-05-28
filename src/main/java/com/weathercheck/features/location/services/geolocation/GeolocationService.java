package com.weathercheck.features.location.services.geolocation;

import java.util.Optional;

import com.weathercheck.shared.components.services.Service;

public interface GeolocationService extends Service {
    Optional<GeoCoordinates> locate();
}
