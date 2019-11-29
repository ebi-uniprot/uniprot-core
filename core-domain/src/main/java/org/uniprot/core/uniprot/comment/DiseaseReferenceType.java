package org.uniprot.core.uniprot.comment;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

/**
 * Enumeration of the possible external sources that have a references to diseases.
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @version 1.0
 * @see DiseaseReference
 */
public enum DiseaseReferenceType implements DatabaseType, EnumDisplay<DiseaseReferenceType> {
    MIM("MIM"),
    NONE("");

    private String displayName;

    DiseaseReferenceType(String displayName) {
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
    public static @Nonnull DiseaseReferenceType typeOf(@Nonnull String value) {
        if (value != null)
            for (DiseaseReferenceType referenceType : DiseaseReferenceType.values()) {
                if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                    return referenceType;
                }
            }

        throw new IllegalArgumentException(
                "The disease reference type: " + value + " doesn't exist");
    }

    /**
     * String representation of the external source
     *
     * @return the name of the source
     */
    public @Nonnull String toDisplayName() {
        return displayName;
    }

    @Override
    public @Nonnull String getName() {
        return displayName;
    }
}
