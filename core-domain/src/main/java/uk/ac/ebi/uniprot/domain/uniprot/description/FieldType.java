package uk.ac.ebi.uniprot.domain.uniprot.description;

/**
 * Defines the set of valid field types
 *
 * Current valid fields are: Full, Short, EC, Allergen, Biotech, CD_antigen, INN.
 * 
 */
public enum FieldType {

    FULL("Full"),
    SHORT("Short"),
    EC("EC"),
    ALLERGEN("Allergen"),
    BIOTECH("Biotech"),
    CD_ANTIGEN("CD_antigen"),
    INN("INN"), 
    UNKNOWN("Unknown");

    private String value;

    private FieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FieldType typeOf(String value) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.getValue().equalsIgnoreCase(value)) {
                return fieldType;
            }
        }
        throw new IllegalArgumentException(String.format("The FieldType with value %s does not exist", value));
    }

    public String toString() {
        return "FieldType{" +
                "value='" + value + '\'' +
                '}';
    }
}
