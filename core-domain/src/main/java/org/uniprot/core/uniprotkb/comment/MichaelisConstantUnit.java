package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum MichaelisConstantUnit implements EnumDisplay<MichaelisConstantUnit> {
    MOL("M"),
    MILLI_MOL("mM"),
    MICRO_MOL("uM"),
    NANO_MOL("nM"),
    MG_ML_2("mg/mL"),
    MG_ML("mg/ml");

    private String name;

    MichaelisConstantUnit(String name) {
        this.name = name;
    }

    public static @Nonnull MichaelisConstantUnit convert(@Nonnull String unit) {
        for (MichaelisConstantUnit value : MichaelisConstantUnit.values()) {
            if (value.name.equals(unit)) {
                return value;
            }
        }
        throw new RuntimeException("unknown Michaelis Constant Unit: " + unit);
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return getName();
    }

    public @Nonnull String toDisplayNameString() {
        return name;
    }
}
