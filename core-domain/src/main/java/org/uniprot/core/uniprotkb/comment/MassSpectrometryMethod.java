package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum MassSpectrometryMethod implements EnumDisplay<MassSpectrometryMethod> {
    // [Electrospray, FAB, LSI, MALDI, Plasma desorption, SELDI, Unknown]
    ELECTROSPRAY("Electrospray"),
    FAB("FAB"),
    LSI("LSI"),
    MALDI("MALDI"),
    PLASMA_DESORPTION("Plasma desorption"),
    SELDI("SELDI"),
    UNKNOWN("Unknown");

    private String value;

    MassSpectrometryMethod(String type) {
        this.value = type;
    }

    public static @Nonnull MassSpectrometryMethod toType(@Nonnull String type) {
        for (MassSpectrometryMethod method : MassSpectrometryMethod.values()) {
            if (method.getValue().equalsIgnoreCase(type)) return method;
        }
        return MassSpectrometryMethod.UNKNOWN;
    }

    public @Nonnull String getValue() {
        return value;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return value;
    }
}
