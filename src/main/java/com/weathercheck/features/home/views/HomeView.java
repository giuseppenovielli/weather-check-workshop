package com.weathercheck.features.home.views;

import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.features.map.views.MapPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class HomeView extends JPanel {
    private final MapPanel mapPanel = new MapPanel();
    private final JLabel locationLabel = new JLabel("-");
    private final JLabel weatherLabel = new JLabel("-");
    private final JButton downloadButton = new JButton("Download");
    private final JLabel footer = new JLabel("© Giuseppe Novielli · Open-Meteo", SwingConstants.CENTER);

    public HomeView(I18nManager i18n) {
        setLayout(new MigLayout("insets 0, fill", "[grow]", "[grow][120!][26!]"));

        JPanel pinPanel = new JPanel(new MigLayout("insets 10, fillx", "[grow]", "[]8[]8[]"));
        pinPanel.setBorder(BorderFactory.createTitledBorder(i18n.tr("home.pin")));
        pinPanel.add(locationLabel, "growx, wrap");
        pinPanel.add(weatherLabel, "growx, wrap");
        pinPanel.add(downloadButton, "right");

        add(mapPanel, "grow, wrap");
        add(pinPanel, "growx, wrap");
        add(footer, "growx");
    }

    public MapPanel mapPanel() { return mapPanel; }
    public JButton downloadButton() { return downloadButton; }
    public JLabel locationLabel() { return locationLabel; }
    public JLabel weatherLabel() { return weatherLabel; }
}
