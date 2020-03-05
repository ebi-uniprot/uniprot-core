package org.uniprot.core.uniprot.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum ReactionDatabase implements Database, EnumDisplay<ReactionDatabase> {
    CHEBI("ChEBI"),
    RHEA("Rhea"),
    UNKNOWN("Unknown");
    private String name;

    ReactionDatabase(String name) {
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
    public static @Nonnull ReactionDatabase typeOf(@Nonnull String value) {
        if (value != null)
            for (ReactionDatabase referenceType : ReactionDatabase.values()) {
                if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                    return referenceType;
                }
            }
        return ReactionDatabase.UNKNOWN;
    }

    @Override
    public @Nonnull String getName() {
        return name;
    }

    /**
     * String representation of the external source
     *
     * @return the name of the source
     */
    public @Nonnull String toDisplayName() {
        return name;
    }
}
