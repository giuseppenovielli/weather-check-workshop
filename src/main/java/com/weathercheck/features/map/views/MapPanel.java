package com.weathercheck.features.map.views;

import com.weathercheck.shared.geo.geolocation.GeoCoordinates;
import com.weathercheck.shared.components.controls.InsetsJPanel;
import com.weathercheck.shared.i18n.I18nManager;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class MapPanel extends InsetsJPanel {
    private static final GeoPosition FALLBACK_POSITION = new GeoPosition(41.8933, 12.4829);

    private final JXMapViewer map = new JXMapViewer();
    private final I18nManager i18n;
    private Runnable currentPositionListener = () -> {};

    public MapPanel(I18nManager i18n) {
        super(0);
        this.i18n = i18n;

        OSMTileFactoryInfo info = new SecureOSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        map.setTileFactory(tileFactory);
        map.setZoom(4);
        map.setAddressLocation(FALLBACK_POSITION);

        MouseInputListener mia = new PanMouseInputListener(map);
        map.addMouseListener(mia);
        map.addMouseMotionListener(mia);
        map.addMouseWheelListener(new ZoomMouseWheelListenerCursor(map));
    }

    @Override
    protected JComponent buildView() {
        JPanel container = new JPanel(new BorderLayout());
        container.add(map, BorderLayout.CENTER);
        container.add(createZoomControls(), BorderLayout.SOUTH);
        return container;
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

    public void onCurrentPositionRequest(Runnable listener) {
        this.currentPositionListener = listener;
    }

    public void centerOnCoordinates(GeoCoordinates coordinates) {
        map.setAddressLocation(toGeoPosition(coordinates));
        map.repaint();
    }

    private GeoPosition toGeoPosition(GeoCoordinates coordinates) {
        return new GeoPosition(coordinates.latitude(), coordinates.longitude());
    }

    private JComponent createZoomControls() {
        JPanel controls = new JPanel(new GridLayout(3, 1, 0, 6));
        controls.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 12));

        JButton zoomInButton = new JButton("+");
        zoomInButton.addActionListener(e -> changeZoom(-1));
        JButton zoomOutButton = new JButton("-");
        zoomOutButton.addActionListener(e -> changeZoom(1));
        JButton currentPositionButton = new JButton(i18n.tr("map.current_position"));
        currentPositionButton.addActionListener(e -> currentPositionListener.run());

        controls.add(zoomInButton);
        controls.add(zoomOutButton);
        controls.add(currentPositionButton);
        return controls;
    }

    private void changeZoom(int delta) {
        int minZoom = map.getTileFactory().getInfo().getMinimumZoomLevel();
        int maxZoom = map.getTileFactory().getInfo().getMaximumZoomLevel();
        int nextZoom = Math.max(minZoom, Math.min(maxZoom, map.getZoom() + delta));
        map.setZoom(nextZoom);
        map.repaint();
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
