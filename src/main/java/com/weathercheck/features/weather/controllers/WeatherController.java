package com.weathercheck.features.weather.controllers;

import com.weathercheck.core.controllers.Controller;
import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.services.geocoding.GeocodingService;
import com.weathercheck.core.services.geolocation.GeoCoordinates;
import com.weathercheck.core.services.geolocation.GeolocationService;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.map.models.MapSelection;
import com.weathercheck.features.weather.models.CurrentWeather;
import com.weathercheck.features.weather.services.WeatherService;
import com.weathercheck.features.weather.views.WeatherView;

import javax.swing.*;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

public class WeatherController implements Controller {
    private final WeatherView view;
    private final WeatherService weatherService;
    private final GeocodingService geocodingService;
    private final GeolocationService geolocationService;
    private final I18nManager i18n;
    private final UnitSystem unitSystem;
    private MapSelection mapSelection;

    public WeatherController(WeatherView view, WeatherService weatherService, GeocodingService geocodingService, GeolocationService geolocationService, I18nManager i18n, UnitSystem unitSystem) {
        this.view = view;
        this.weatherService = weatherService;
        this.geocodingService = geocodingService;
        this.geolocationService = geolocationService;
        this.i18n = i18n;
        this.unitSystem = unitSystem;
    }

    @Override
    public void init() {
        view.mapPanel().onCurrentPositionRequest(this::centerOnCurrentPositionAsync);
        centerOnCurrentPositionAsync();
        bindMapClick();
        bindDownloadWeather();
    }

    private void bindMapClick() {
        view.mapPanel().onMapClick(pos -> {
            String label = String.format("%.4f, %.4f", pos.getLatitude(), pos.getLongitude());
            mapSelection = new MapSelection(pos.getLatitude(), pos.getLongitude(), label);
            view.locationLabel().setText(i18n.tr("home.loading"));

            CompletableFuture
                    .supplyAsync(() -> geocodingService.getPlacemarks(pos.getLatitude(), pos.getLongitude()))
                    .thenAccept(address -> SwingUtilities.invokeLater(() ->
                            view.locationLabel().setText(address.orElse(label))
                    ));
        });
    }

    private void bindDownloadWeather() {
        view.downloadButton().addActionListener(e -> {
            MapSelection selection = mapSelection;
            if (selection == null) {
                JOptionPane.showMessageDialog(view, i18n.tr("home.select_location"));
                return;
            }
            CompletableFuture
                    .supplyAsync(() -> loadCurrentWeather(selection))
                    .thenAccept(weather -> SwingUtilities.invokeLater(() -> view.weatherLabel().setText(weatherText(weather))))
                    .exceptionally(ex -> {
                        SwingUtilities.invokeLater(() -> view.weatherLabel().setText(i18n.tr("home.error")));
                        return null;
                    });
        });
    }

    private void centerOnCurrentPositionAsync() {
        CompletableFuture
                .supplyAsync(geolocationService::locate)
                .thenAccept(geoCoordinates -> geoCoordinates.ifPresent(this::centerMapOnEdt));
    }

    private void centerMapOnEdt(GeoCoordinates coordinates) {
        SwingUtilities.invokeLater(() -> view.mapPanel().centerOnCoordinates(coordinates));
    }

    private String weatherText(CurrentWeather weather) {
        String symbol = unitSystem == UnitSystem.IMPERIAL ? "°F" : "°C";
        return String.format("%s - %s%s", i18n.tr(weather.weatherKey()), weather.temperature(), symbol);
    }

    private CurrentWeather loadCurrentWeather(MapSelection selection) {
        return weatherService.loadCurrent(
                selection.latitude(),
                selection.longitude(),
                ZoneId.systemDefault(),
                unitSystem
        );
    }
}
