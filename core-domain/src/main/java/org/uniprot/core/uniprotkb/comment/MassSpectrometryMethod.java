package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum MassSpectrometryMethod implements EnumDisplay {
    ELECTROSPRAY("Electrospray"),
    FAB("FAB"),
    LSI("LSI"),
    MALDI("MALDI"),
    PLASMA_DESORPTION("Plasma desorption"),
    SELDI("SELDI"),
    API("API"),
    UNKNOWN("Unknown");

    private String name;

    MassSpectrometryMethod(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull MassSpectrometryMethod typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, MassSpectrometryMethod.UNKNOWN);
    }
}
