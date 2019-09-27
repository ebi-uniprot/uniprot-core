package org.uniprot.core.uniprot.comment;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

public enum ReactionReferenceType implements DatabaseType, EnumDisplay<ReactionReferenceType> {
    CHEBI("ChEBI"),
    RHEA("Rhea"),
    UNKNOWN("Unknown");
    private String name;

    ReactionReferenceType(String name) {
        this.name = name;
    }

    /**
     * Provided a string representation of a source, return the enum type.
     *
     * @param value string representation of a source.
     * @return the enum type of the provided source.
     * @throws IllegalArgumentException is thrown when the provided value has no corresponding enum
     *     type.
     */
    public static ReactionReferenceType typeOf(String value) {
        for (ReactionReferenceType referenceType : ReactionReferenceType.values()) {
            if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                return referenceType;
            }
        }
        return ReactionReferenceType.UNKNOWN;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * String representation of the external source
     *
     * @return the name of the source
     */
    public String toDisplayName() {
        return name;
    }
}
