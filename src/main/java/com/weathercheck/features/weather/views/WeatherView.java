package com.weathercheck.features.weather.views;

import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.controls.InsetsJPanel;
import com.weathercheck.core.theme.LabelStyles;
import com.weathercheck.features.map.views.MapPanel;

import javax.swing.*;
import java.awt.*;

public class WeatherView extends InsetsJPanel {
    private final MapPanel mapPanel;
    private final LabelStyles labelStyles = new LabelStyles();
    private final JLabel locationLabel = new JLabel("-");
    private final JLabel weatherLabel = new JLabel("-");
    private final JButton downloadButton = new JButton();
    private final JLabel footer = new JLabel("© Giuseppe Novielli · Open-Meteo", SwingConstants.CENTER);
    private final I18nManager i18n;

    public WeatherView(I18nManager i18n) {
        super(0);
        this.i18n = i18n;
        this.mapPanel = new MapPanel(i18n);
    }

    @Override
    protected JComponent buildView() {
        applyTranslations();
        labelStyles.applyTitleText(locationLabel);
        labelStyles.applyBodyMediumTextStyle(weatherLabel);
        labelStyles.applyBodyMicroTextStyle(footer);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        JPanel pinPanel = new JPanel(new GridBagLayout());
        pinPanel.setBorder(BorderFactory.createTitledBorder(i18n.tr("home.pin")));
        GridBagConstraints pinConstraints = new GridBagConstraints();
        pinConstraints.gridx = 0;
        pinConstraints.fill = GridBagConstraints.HORIZONTAL;
        pinConstraints.weightx = 1.0;

        pinConstraints.gridy = 0;
        pinPanel.add(locationLabel, pinConstraints);
        pinConstraints.gridy = 1;
        pinPanel.add(weatherLabel, pinConstraints);

        pinConstraints.gridy = 2;
        pinConstraints.anchor = GridBagConstraints.EAST;
        pinConstraints.fill = GridBagConstraints.NONE;
        pinConstraints.weightx = 0.0;
        pinPanel.add(downloadButton, pinConstraints);

        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.gridx = 0;
        mainConstraints.fill = GridBagConstraints.BOTH;
        mainConstraints.weightx = 1.0;

        mainConstraints.gridy = 0;
        mainConstraints.weighty = 1.0;
        content.add(mapPanel, mainConstraints);

        mainConstraints.gridy = 1;
        mainConstraints.weighty = 0.0;
        content.add(pinPanel, mainConstraints);

        mainConstraints.gridy = 2;
        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        content.add(footer, mainConstraints);

        return content;
    }

    public void applyTranslations() {
        downloadButton.setText(i18n.tr("home.download"));
    }

    public MapPanel mapPanel() { return mapPanel; }
    public JButton downloadButton() { return downloadButton; }
    public JLabel locationLabel() { return locationLabel; }
    public JLabel weatherLabel() { return weatherLabel; }
}
