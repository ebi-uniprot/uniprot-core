package org.uniprot.core.uniprotkb.comment;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum MichaelisConstantUnit implements EnumDisplay {
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

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull MichaelisConstantUnit typeOf(@Nonnull String name) {
        nullThrowIllegalArgument(name);

        for (MichaelisConstantUnit constant : MichaelisConstantUnit.values()) {
            if (constant.getCompareOn().equals(name.trim())) return constant;
        }
        throw new IllegalArgumentException(
                "The MichaelisConstantUnit with " + name + " doesn't exist");
    }
}
