package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum ComponentType implements EnumDisplay {
    PRIMARY("Primary"),
    CON("CON"),
    SEGMENTED_GENOME("Segmented genome"),
    WGS("WGS"),
    GCA("GCA"),
    GCF("GCF"),
    UNPLACED("Unplaced");

    private final String name;

    ComponentType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull ComponentType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, ComponentType.UNPLACED);
    }
}
