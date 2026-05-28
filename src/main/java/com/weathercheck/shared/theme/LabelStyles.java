package com.weathercheck.shared.theme;

import javax.swing.*;
import java.awt.*;

public final class LabelStyles {
    // Dimensioni font di default
    private static final float DEFAULT_TITLE_LARGE_SIZE = 24f;
    private static final float DEFAULT_TITLE_MEDIUM_SIZE = 20f;
    private static final float DEFAULT_BODY_LARGE_SIZE = 18f;
    private static final float DEFAULT_BODY_MEDIUM_SIZE = 14f;
    private static final float DEFAULT_BODY_SMALL_SIZE = 12f;
    private static final float DEFAULT_BODY_MICRO_SIZE = 10f;

    public LabelStyles() {}

    // Stili testo title
    public void applyTitleLargeTextStyle(JLabel label) {
        apply(
                label,
                getFloat("wc.label.title.large.size", DEFAULT_TITLE_LARGE_SIZE),
                titleStyle(),
                getColor("wc.color.text.default", Color.BLACK)
        );
    }

    public void applyTitleMediumTextStyle(JLabel label) {
        apply(
                label,
                getFloat("wc.label.title.medium.size", DEFAULT_TITLE_MEDIUM_SIZE),
                titleStyle(),
                getColor("wc.color.text.default", Color.BLACK)
        );
    }

    public void applyTitleDefaultTextStyle(JLabel label) {
        applyTitleMediumTextStyle(label);
    }

    public void applyTitleText(JLabel label) {
        applyTitleDefaultTextStyle(label);
    }

    // Stili testo body
    public void applyBodyLargeTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.large.size", DEFAULT_BODY_LARGE_SIZE), bodyStyle(), getColor("wc.color.text.default", Color.BLACK));
    }

    public void applyBodyMediumTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.medium.size", DEFAULT_BODY_MEDIUM_SIZE), bodyStyle(), getColor("wc.color.text.default", Color.BLACK));
    }

    public void applyBodyTextStyle(JLabel label) {
        applyBodyMediumTextStyle(label);
    }

    public void applyBodySmallTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.small.size", DEFAULT_BODY_SMALL_SIZE), bodyStyle(), getColor("wc.color.text.default", Color.BLACK));
    }

    public void applyBodyMicroTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.micro.size", DEFAULT_BODY_MICRO_SIZE), bodyStyle(), getColor("wc.color.text.default", Color.BLACK));
    }

    // Stili testo inversi
    public void applyBodyLargeInverseTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.large.size", DEFAULT_BODY_LARGE_SIZE), bodyStyle(), getColor("wc.color.text.inverse", Color.WHITE));
    }

    public void applyBodyMediumInverseTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.medium.size", DEFAULT_BODY_MEDIUM_SIZE), bodyStyle(), getColor("wc.color.text.inverse", Color.WHITE));
    }

    public void applyBodyInverseTextStyle(JLabel label) {
        applyBodyMediumInverseTextStyle(label);
    }

    public void applyBodySmallInverseTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.small.size", DEFAULT_BODY_SMALL_SIZE), bodyStyle(), getColor("wc.color.text.inverse", Color.WHITE));
    }

    public void applyBodyMicroInverseTextStyle(JLabel label) {
        apply(label, getFloat("wc.label.body.micro.size", DEFAULT_BODY_MICRO_SIZE), bodyStyle(), getColor("wc.color.text.inverse", Color.WHITE));
    }

    // Metodi di supporto interni
    private void apply(JLabel label, float size, int style, Color color) {
        Font base = UIManager.getFont("Label.font");
        if (base == null) {
            base = label.getFont();
        }
        if (base == null) {
            base = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        }
        label.setFont(base.deriveFont(style, size));
        label.setForeground(color);
    }

    private float getFloat(String key, float fallback) {
        Object value = UIManager.get(key);
        if (value instanceof Number number) {
            return number.floatValue();
        }
        return fallback;
    }

    private int getFontStyle(String key, int fallback) {
        Object value = UIManager.get(key);
        if (!(value instanceof String styleName)) {
            return fallback;
        }
        if ("bold".equalsIgnoreCase(styleName)) {
            return Font.BOLD;
        }
        if ("plain".equalsIgnoreCase(styleName)) {
            return Font.PLAIN;
        }
        if ("italic".equalsIgnoreCase(styleName)) {
            return Font.ITALIC;
        }
        return fallback;
    }

    private int bodyStyle() {
        return getFontStyle("wc.label.body.style", Font.PLAIN);
    }

    private int titleStyle() {
        return getFontStyle("wc.label.title.style", Font.BOLD);
    }

    private Color getColor(String key, Color fallback) {
        Color color = UIManager.getColor(key);
        return color != null ? color : fallback;
    }
}
