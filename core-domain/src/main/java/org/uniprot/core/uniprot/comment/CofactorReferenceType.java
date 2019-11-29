package org.uniprot.core.uniprot.comment;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum CofactorReferenceType implements DatabaseType, EnumDisplay<CofactorReferenceType> {
    CHEBI("ChEBI"),
    NONE("");
    private String displayName;

    CofactorReferenceType(String displayName) {
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
    public static @Nonnull CofactorReferenceType typeOf(@Nonnull String value) {
        for (CofactorReferenceType referenceType : CofactorReferenceType.values()) {
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
