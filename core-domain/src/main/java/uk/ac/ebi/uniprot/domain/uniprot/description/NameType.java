package uk.ac.ebi.uniprot.domain.uniprot.description;

/**
 * Conatins a list of acceptable values for the categories expressed in a DE line
 */
public enum NameType {

    RECNAME("RecName"),
    ALTNAME("AltName"),
    SUBNAME("SubName"),
    UNKNOWN("Unknown");

    private String value;

    private NameType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NameType typeOf(String value) {
        for (NameType nameType : NameType.values()) {
            if (nameType.getValue().equals(value)) {
                return nameType;
            }
        }
        throw new IllegalArgumentException(String.format("The NameType with value %s does not exist", value));
    }


    public String toString() {
        return "NameType{" +
                "value='" + value + '\'' +
                '}';
    }
}
