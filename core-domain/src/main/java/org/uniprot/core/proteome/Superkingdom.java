package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum Superkingdom implements EnumDisplay<Superkingdom> {
    ARCHAEA("archaea"),
    VIRUSES("viruses"),
    BACTERIA("bacteria"),
    EUKARYOTA("eukaryota");

    private final String name;

    Superkingdom(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return getName();
    }

    public static @Nonnull Superkingdom fromValue(@Nonnull String type) {
        for (Superkingdom gnType : Superkingdom.values()) {
            if (gnType.getName().equalsIgnoreCase(type)) return gnType;
        }
        throw new IllegalArgumentException(type);
    }
}
