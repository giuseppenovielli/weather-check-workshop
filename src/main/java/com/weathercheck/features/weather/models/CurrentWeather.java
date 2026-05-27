package com.weathercheck.features.weather.models;

public record CurrentWeather(String time, double temperature, int weatherCode, String weatherKey) {
}
