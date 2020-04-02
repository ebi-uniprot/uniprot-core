package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum ReactionDatabase implements Database, EnumDisplay {
    CHEBI("ChEBI"),
    RHEA("Rhea");

    private String name;

    ReactionDatabase(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull ReactionDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, ReactionDatabase.class);
    }
}
