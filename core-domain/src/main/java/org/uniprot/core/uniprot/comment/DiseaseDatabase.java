package org.uniprot.core.uniprot.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

/**
 * Enumeration of the possible external sources that have a references to diseases.
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @version 1.0
 * @see DiseaseReference
 */
public enum DiseaseDatabase implements Database, EnumDisplay<DiseaseDatabase> {
    MIM("MIM"),
    NONE("");

    private String displayName;

    DiseaseDatabase(String displayName) {
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
    public static @Nonnull DiseaseDatabase typeOf(@Nonnull String value) {
        if (value != null)
            for (DiseaseDatabase referenceType : DiseaseDatabase.values()) {
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
