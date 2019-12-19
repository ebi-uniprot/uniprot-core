package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author jluo
 * @date: 11 Jun 2019
 */
public enum ComponentType implements EnumDisplay<ComponentType> {
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

    @Override
    public @Nonnull String toDisplayName() {
        return name;
    }

    public static @Nonnull ComponentType fromValue(@Nonnull String type) {
        for (ComponentType gnType : ComponentType.values()) {
            if (gnType.getName().equalsIgnoreCase(type)) return gnType;
        }
        return ComponentType.UNPLACED;
    }
}
