package com.weathercheck.features.map.views;

import com.weathercheck.core.controls.InsetsJPanel;
import com.weathercheck.core.services.geolocation.GeoCoordinates;
import com.weathercheck.core.services.geolocation.GeolocationService;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MapPanel extends InsetsJPanel {
    private static final GeoPosition FALLBACK_POSITION = new GeoPosition(41.8933, 12.4829);

    private final JXMapViewer map = new JXMapViewer();
    private final GeolocationService geolocationService;

    public MapPanel(GeolocationService geolocationService) {
        super(0);
        this.geolocationService = geolocationService;

        OSMTileFactoryInfo info = new SecureOSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        map.setTileFactory(tileFactory);
        map.setZoom(4);
        map.setAddressLocation(FALLBACK_POSITION);

        MouseInputListener mia = new PanMouseInputListener(map);
        map.addMouseListener(mia);
        map.addMouseMotionListener(mia);
        map.addMouseWheelListener(new ZoomMouseWheelListenerCursor(map));

        centerOnUserLocationAsync();
    }

    @Override
    protected JComponent buildView() {
        return map;
    }

    public void onMapClick(Consumer<GeoPosition> callback) {
        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GeoPosition p = map.convertPointToGeoPosition(e.getPoint());
                callback.accept(p);
            }
        });
    }

    public JXMapViewer getMap() {
        return map;
    }

    private void centerOnUserLocationAsync() {
        CompletableFuture
                .supplyAsync(geolocationService::locate)
                .thenAccept(geoCoordinates -> geoCoordinates.ifPresent(coordinates ->
                        SwingUtilities.invokeLater(() -> {
                            map.setAddressLocation(toGeoPosition(coordinates));
                            map.repaint();
                        })
                ));
    }

    private GeoPosition toGeoPosition(GeoCoordinates coordinates) {
        return new GeoPosition(coordinates.latitude(), coordinates.longitude());
    }

    /**
     * Forces HTTPS tiles because some bundled JXMapViewer OSM infos still use HTTP.
     */
    private static class SecureOSMTileFactoryInfo extends OSMTileFactoryInfo {
        @Override
        public String getTileUrl(int x, int y, int zoom) {
            int z = getTotalMapZoom() - zoom;
            return String.format("https://tile.openstreetmap.org/%d/%d/%d.png", z, x, y);
        }
    }
}
