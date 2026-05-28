package com.weathercheck.shared.components.controls.jlabel;

import com.weathercheck.shared.theme.LabelStyles;

import javax.swing.*;

public class BodyJLabel extends JLabel {
    private static final LabelStyles LABEL_STYLES = new LabelStyles();
    private final Variant variant;

    public BodyJLabel(Variant variant) {
        this.variant = variant;
        applyCurrentStyle();
    }

    public BodyJLabel(Variant variant, String text) {
        super(text);
        this.variant = variant;
        applyCurrentStyle();
    }

    public BodyJLabel(Variant variant, String text, int horizontalAlignment) {
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
                labelStyles.applyBodyLargeTextStyle(label);
            }
        },
        MEDIUM {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodyMediumTextStyle(label);
            }
        },
        DEFAULT {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodyTextStyle(label);
            }
        },
        SMALL {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodySmallTextStyle(label);
            }
        },
        MICRO {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodyMicroTextStyle(label);
            }
        },
        LARGE_INVERSE {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodyLargeInverseTextStyle(label);
            }
        },
        MEDIUM_INVERSE {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodyMediumInverseTextStyle(label);
            }
        },
        INVERSE {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodyInverseTextStyle(label);
            }
        },
        SMALL_INVERSE {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodySmallInverseTextStyle(label);
            }
        },
        MICRO_INVERSE {
            @Override
            void apply(LabelStyles labelStyles, JLabel label) {
                labelStyles.applyBodyMicroInverseTextStyle(label);
            }
        };

        abstract void apply(LabelStyles labelStyles, JLabel label);
    }
}
