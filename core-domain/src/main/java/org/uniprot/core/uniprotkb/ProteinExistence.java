package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum ProteinExistence implements EnumDisplay {
    PROTEIN_LEVEL("Evidence at protein level", "1: Evidence at protein level"),
    TRANSCRIPT_LEVEL("Evidence at transcript level", "2: Evidence at transcript level"),
    HOMOLOGY("Inferred from homology", "3: Inferred from homology"),
    PREDICTED("Predicted", "4: Predicted"),
    UNCERTAIN("Uncertain", "5: Uncertain"),
    UNKNOWN("UNKNOWN", "UNKNOWN");

    private String name;
    private String displayName;

    ProteinExistence(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public @Nonnull String getDisplayName() {
        return displayName;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull String toString() {
        return this.getName();
    }

    public static @Nonnull ProteinExistence typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, ProteinExistence.class, ProteinExistence.UNKNOWN);
    }
}
