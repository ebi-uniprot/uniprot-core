package org.uniprot.core.proteome;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum ProteomeType implements EnumDisplay {
    NORMAL("Complete proteome"),
    REFERENCE("Reference proteome"),
    REPRESENTATIVE("Representative proteome"),
    REDUNDANT("Redundant proteome"),
    IN_COMPLETE("Incomplete proteome");

    private final String name;

    ProteomeType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull ProteomeType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, ProteomeType.class);
    }
}
