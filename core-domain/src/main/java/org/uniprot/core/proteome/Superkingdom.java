package org.uniprot.core.proteome;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum Superkingdom implements EnumDisplay {
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

    public static @Nonnull Superkingdom typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, Superkingdom.class);
    }

    public static boolean isSuperkingdom(@Nonnull String name) {
        return Arrays.stream(Superkingdom.values())
                .anyMatch(superkingdom -> superkingdom.name.equalsIgnoreCase(name));
    }
}
