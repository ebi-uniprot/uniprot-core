package org.uniprot.core.uniprot.comment;

import org.uniprot.core.util.EnumDisplay;

public enum PhysiologicalDirectionType implements EnumDisplay<PhysiologicalDirectionType> {
    // left-to-right, right-to-left
    LEFT_TO_RIGHT("left-to-right"),
    RIGHT_TO_LEFT("right-to-left");

    private final String name;

    PhysiologicalDirectionType(String name) {
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
    public static PhysiologicalDirectionType typeOf(String value) {
        for (PhysiologicalDirectionType referenceType : PhysiologicalDirectionType.values()) {
            if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                return referenceType;
            }
        }
        throw new IllegalArgumentException(
                "The physiological direction type: " + value + " doesn't exist");
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
