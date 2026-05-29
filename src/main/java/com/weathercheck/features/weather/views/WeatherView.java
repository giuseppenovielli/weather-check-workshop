package com.weathercheck.features.weather.views;

import com.weathercheck.features.map.views.MapPanel;
import com.weathercheck.shared.components.controls.ExtendedJPanel;
import com.weathercheck.shared.components.controls.ExtendedJLabel;
import com.weathercheck.shared.i18n.I18nManager;

import javax.swing.*;
import java.awt.*;

public class WeatherView extends ExtendedJPanel {
    private final MapPanel mapPanel;
    private final JLabel locationLabel = new ExtendedJLabel(ExtendedJLabel.Styles.TITLE, "-");
    private final JLabel weatherLabel = new ExtendedJLabel(ExtendedJLabel.Styles.BODY_MEDIUM, "-");
    private final JButton downloadButton = new JButton();
    private final JLabel footer = new ExtendedJLabel(ExtendedJLabel.Styles.BODY_MICRO, "-", SwingConstants.CENTER);
    private final I18nManager i18n;

    public WeatherView(I18nManager i18n) {
        super(0);
        this.i18n = i18n;
        this.mapPanel = new MapPanel(i18n);
    }

    @Override
    protected JComponent buildView() {
        applyTranslations();

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
        footer.setText(i18n.tr("home.footer"));
    }

    public MapPanel mapPanel() { return mapPanel; }
    public JButton downloadButton() { return downloadButton; }
    public JLabel locationLabel() { return locationLabel; }
    public JLabel weatherLabel() { return weatherLabel; }
}
