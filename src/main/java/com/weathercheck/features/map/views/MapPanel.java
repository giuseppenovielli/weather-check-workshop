package com.weathercheck.features.map.views;

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
import java.util.function.Consumer;

public class MapPanel extends JPanel {
    private final JXMapViewer map = new JXMapViewer();

    public MapPanel() {
        setLayout(new java.awt.BorderLayout());

        OSMTileFactoryInfo info = new SecureOSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        map.setTileFactory(tileFactory);
        map.setZoom(4);
        map.setAddressLocation(new GeoPosition(41.8933, 12.4829));

        MouseInputListener mia = new PanMouseInputListener(map);
        map.addMouseListener(mia);
        map.addMouseMotionListener(mia);
        map.addMouseWheelListener(new ZoomMouseWheelListenerCursor(map));
        add(map, java.awt.BorderLayout.CENTER);
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
