package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.EnumDisplay;

public enum CofactorReferenceType implements DatabaseType, EnumDisplay<CofactorReferenceType> {
    CHEBI("ChEBI"),
    NONE("");
    private String displayName;

    private CofactorReferenceType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Provided a string representation of a source, return the enum type.
     *
     * @param value string representation of a source.
     * @return the enum type of the provided source.
     * @throws IllegalArgumentException is thrown when the provided value has no corresponding enum type.
     */
    public static CofactorReferenceType typeOf(String value) {
        for (CofactorReferenceType referenceType : CofactorReferenceType
                .values()) {
            if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
                return referenceType;
            }
        }

        throw new IllegalArgumentException("The cofactor reference type: " + value + " doesn't exist");
    }

    @Override
    public String getName() {
        return this.displayName;
    }

    /**
     * String representation of the external source
     *
     * @return the name of the source
     */
    public String toDisplayName() {
        return displayName;
    }


}
