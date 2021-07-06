package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum ProteinExistence implements EnumDisplay {
    PROTEIN_LEVEL(1, "Evidence at protein level", "1: Evidence at protein level"),
    TRANSCRIPT_LEVEL(2, "Evidence at transcript level", "2: Evidence at transcript level"),
    HOMOLOGY(3, "Inferred from homology", "3: Inferred from homology"),
    PREDICTED(4, "Predicted", "4: Predicted"),
    UNCERTAIN(5, "Uncertain", "5: Uncertain"),
    UNKNOWN(6, "UNKNOWN", "UNKNOWN");

    private int id;
    private final String name;
    private final String displayName;

    ProteinExistence(int id, String name, String displayName) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public @Nonnull String getDisplayName() {
        return displayName;
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull int getId() {
        return id;
    }

    public @Nonnull String toString() {
        return this.getName();
    }

    public static @Nonnull ProteinExistence typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, ProteinExistence.class, ProteinExistence.UNKNOWN);
    }
}
