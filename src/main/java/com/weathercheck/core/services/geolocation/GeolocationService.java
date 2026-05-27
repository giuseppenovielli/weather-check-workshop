package com.weathercheck.core.services.geolocation;

import java.util.Optional;

import com.weathercheck.core.services.Service;

public interface GeolocationService extends Service {
    Optional<GeoCoordinates> locate();
}
