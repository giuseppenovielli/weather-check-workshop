package com.weathercheck.features.home.views;

import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.features.map.views.MapPanel;

import javax.swing.*;
import java.awt.*;

public class HomeView extends JPanel {
    private final MapPanel mapPanel = new MapPanel();
    private final JLabel locationLabel = new JLabel("-");
    private final JLabel weatherLabel = new JLabel("-");
    private final JButton downloadButton = new JButton("Download");
    private final JLabel footer = new JLabel("© Giuseppe Novielli · Open-Meteo", SwingConstants.CENTER);

    public HomeView(I18nManager i18n) {
        setLayout(new GridBagLayout());

        JPanel pinPanel = new JPanel(new GridBagLayout());
        pinPanel.setBorder(BorderFactory.createTitledBorder(i18n.tr("home.pin")));
        GridBagConstraints pinConstraints = new GridBagConstraints();
        pinConstraints.gridx = 0;
        pinConstraints.fill = GridBagConstraints.HORIZONTAL;
        pinConstraints.weightx = 1.0;
        pinConstraints.insets = new Insets(10, 10, 0, 10);

        pinConstraints.gridy = 0;
        pinPanel.add(locationLabel, pinConstraints);
        pinConstraints.gridy = 1;
        pinConstraints.insets = new Insets(8, 10, 0, 10);
        pinPanel.add(weatherLabel, pinConstraints);

        pinConstraints.gridy = 2;
        pinConstraints.anchor = GridBagConstraints.EAST;
        pinConstraints.fill = GridBagConstraints.NONE;
        pinConstraints.weightx = 0.0;
        pinConstraints.insets = new Insets(8, 10, 10, 10);
        pinPanel.add(downloadButton, pinConstraints);

        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.gridx = 0;
        mainConstraints.fill = GridBagConstraints.BOTH;
        mainConstraints.weightx = 1.0;

        mainConstraints.gridy = 0;
        mainConstraints.weighty = 1.0;
        add(mapPanel, mainConstraints);

        mainConstraints.gridy = 1;
        mainConstraints.weighty = 0.0;
        add(pinPanel, mainConstraints);

        mainConstraints.gridy = 2;
        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.insets = new Insets(4, 0, 0, 0);
        add(footer, mainConstraints);
    }

    public MapPanel mapPanel() { return mapPanel; }
    public JButton downloadButton() { return downloadButton; }
    public JLabel locationLabel() { return locationLabel; }
    public JLabel weatherLabel() { return weatherLabel; }
}
