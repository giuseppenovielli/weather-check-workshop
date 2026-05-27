package com.weathercheck.features.settings.views;

import com.weathercheck.core.controls.InsetsJPanelBase;
import com.weathercheck.core.theme.LabelStyles;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends InsetsJPanelBase {
    private final LabelStyles labelStyles = new LabelStyles();
    private final JComboBox<String> language = new JComboBox<>(new String[]{"it-IT", "en-US", "fr-FR", "de-DE", "es-ES", "ja-JP"});
    private final JComboBox<String> theme = new JComboBox<>(new String[]{"light", "dark"});
    private final JButton save = new JButton("Save");
    private final JLabel languageLabel = new JLabel("Language");
    private final JLabel themeLabel = new JLabel("Theme");

    public SettingsView() {}

    @Override
    protected JComponent buildView() {
        labelStyles.applyBodyMediumTextStyle(languageLabel);
        labelStyles.applyBodyMediumTextStyle(themeLabel);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.insets = new Insets(0, 0, 0, 0);
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.gridx = 0;

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.insets = new Insets(0, 0, 0, 0);
        fieldConstraints.anchor = GridBagConstraints.WEST;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.gridx = 1;

        labelConstraints.gridy = 0;
        fieldConstraints.gridy = 0;
        content.add(languageLabel, labelConstraints);
        content.add(language, fieldConstraints);

        labelConstraints.gridy = 1;
        fieldConstraints.gridy = 1;
        content.add(themeLabel, labelConstraints);
        content.add(theme, fieldConstraints);

        labelConstraints.gridy = 2;
        fieldConstraints.gridy = 2;
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 2;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.weightx = 1.0;
        buttonConstraints.insets = new Insets(0, 0, 0, 0);
        buttonConstraints.anchor = GridBagConstraints.EAST;
        content.add(save, buttonConstraints);

        return content;
    }

    public JComboBox<String> language() { return language; }
    public JComboBox<String> theme() { return theme; }
    public JButton save() { return save; }
}
