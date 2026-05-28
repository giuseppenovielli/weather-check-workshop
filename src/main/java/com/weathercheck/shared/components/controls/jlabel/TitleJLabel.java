package com.weathercheck.shared.components.controls.jlabel;

import com.weathercheck.shared.theme.LabelStyles;

import javax.swing.*;

public class TitleJLabel extends JLabel {
    private static final LabelStyles LABEL_STYLES = new LabelStyles();
    private final Variant variant;

    public TitleJLabel(Variant variant) {
        this.variant = variant;
        applyCurrentStyle();
    }

    public TitleJLabel(Variant variant, String text) {
        super(text);
        this.variant = variant;
        applyCurrentStyle();
    }

    public TitleJLabel(Variant variant, String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.variant = variant;
        applyCurrentStyle();
    }

    protected final void applyCurrentStyle() {
        if (variant == null) {
            return;
        }
        variant.apply(LABEL_STYLES, this);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        applyCurrentStyle();
    }

    public enum Variant {
        LARGE {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyTitleLargeTextStyle(label);
            }
        },
        MEDIUM {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyTitleMediumTextStyle(label);
            }
        },
        DEFAULT {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyTitleDefaultTextStyle(label);
            }
        },
        TITLE {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyTitleText(label);
            }
        };

        abstract void apply(LabelStyles labelStyles, JLabel label);
    }
}
