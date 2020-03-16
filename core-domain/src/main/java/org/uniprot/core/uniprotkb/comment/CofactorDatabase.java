package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum CofactorDatabase implements Database, EnumDisplay<CofactorDatabase> {
    CHEBI("ChEBI"),
    NONE("");
    private String displayName;

    CofactorDatabase(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Provided a string representation of a source, return the enum type.
     *
     * @param value string representation of a source.
     * @return the enum type of the provided source.
     * @throws IllegalArgumentException is thrown when the provided value has no corresponding enum
     *     type.
     */
    public static @Nonnull CofactorDatabase typeOf(@Nonnull String value) {
        for (CofactorDatabase referenceType : CofactorDatabase.values()) {
            if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                return referenceType;
            }
        }

        throw new IllegalArgumentException(
                "The cofactor reference type: " + value + " doesn't exist");
    }

    @Override
    public @Nonnull String getName() {
        return this.displayName;
    }

    /**
     * String representation of the external source
     *
     * @return the name of the source
     */
    public @Nonnull String toDisplayName() {
        return displayName;
    }
}
