package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum DiseaseDatabase implements Database, EnumDisplay {
    MIM("MIM"),
    NONE("");

    private String name;

    DiseaseDatabase(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull DiseaseDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, DiseaseDatabase.class);
    }
}
