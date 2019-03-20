package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.common.EnumDisplay;
import uk.ac.ebi.uniprot.domain.DatabaseType;

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
     * @throws IllegalArgumentException is thrown when the provided value has no corresponding enum type.
     */
    public static DiseaseReferenceType typeOf(String value) {
        for (DiseaseReferenceType referenceType : DiseaseReferenceType
                .values()) {
            if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                return referenceType;
            }
        }

        throw new IllegalArgumentException("The disease reference type: " + value + " doesn't exist");
    }

    /**
     * String representation of the external source
     *
     * @return the name of the source
     */
    public String toDisplayName() {
        return displayName;
    }

    @Override
    public String getName() {
        return displayName;
    }
}
