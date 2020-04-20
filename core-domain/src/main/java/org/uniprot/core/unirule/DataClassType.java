package org.uniprot.core.unirule;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/** @author sahmad */
public enum DataClassType implements EnumDisplay {
    PROTEIN("Protein"),
    DOMAIN("Domain");

    String name;

    DataClassType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    public static @Nonnull DataClassType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, DataClassType.class);
    }
}
