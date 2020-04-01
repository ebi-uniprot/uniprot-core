package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum CofactorDatabase implements Database, EnumDisplay {
    CHEBI("ChEBI"),
    NONE("");
    private String displayName;

    CofactorDatabase(String displayName) {
        this.displayName = displayName;
    }

    public @Nonnull String getName() {
        return this.displayName;
    }

    public static @Nonnull CofactorDatabase typeOf(@Nonnull String displayName) {
        return EnumDisplay.typeOf(displayName, CofactorDatabase.class);
    }
}
