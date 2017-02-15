package uk.ac.ebi.uniprot.domain.uniprot.comments;



/**
 * Enumeration of the possible external sources that have a references to diseases.
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @see DiseaseReference
 * @version 1.0
 */
public enum DiseaseReferenceType {
    MIM("MIM"),
    NONE("");

    private String displayName;

    private DiseaseReferenceType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * String representation of the external source
     *
     * @return the name of the source
     */
    public String toDisplayName() {
        return displayName;
    }

    /**
     * Provided a string representation of a source, return the enum type.
     *
     * @param value string representation of a source.
     * @return the enum type of the provided source.
     * @throws IllegalArgumentException is thrown when the provided value has no corresponding enum type.
     */
    public static DiseaseReferenceType typeOf(String value) {
        for (DiseaseReferenceType referenceType : DiseaseReferenceType.values()) {
            if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                return referenceType;
            }
        }

        throw new IllegalArgumentException("The disease reference type: " + value + " doesn't exist");
    }
}
