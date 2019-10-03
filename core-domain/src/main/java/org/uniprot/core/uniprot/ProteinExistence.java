package org.uniprot.core.uniprot;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.util.EnumDisplay;

public enum ProteinExistence implements EnumDisplay<ProteinExistence> {
    PROTEIN_LEVEL("Evidence at protein level", "1: Evidence at protein level"),
    TRANSCRIPT_LEVEL("Evidence at transcript level", "2: Evidence at transcript level"),
    HOMOLOGY("Inferred from homology", "3: Inferred from homology"),
    PREDICTED("Predicted", "4: Predicted"),
    UNCERTAIN("Uncertain", "5: Uncertain"),
    UNKNOWN("UNKNOWN", "UNKNOWN");

    private String value;
    private String displayName;

    ProteinExistence(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public @Nonnull static ProteinExistence typeOf(@Nullable String value) {
        for (ProteinExistence proteinExistence : ProteinExistence.values()) {
            if (proteinExistence.getValue().equalsIgnoreCase(value)) {
                return proteinExistence;
            }
        }
        return ProteinExistence.UNKNOWN;
    }

    public @Nonnull String toDisplayName() {
        return displayName;
    }

    public @Nonnull String getValue() {
        return value;
    }

    public @Nonnull String toString() {
        return this.getValue();
    }
}
