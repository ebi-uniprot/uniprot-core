package uk.ac.ebi.uniprot.domain.uniprot.comments;

public enum MassSpectrometryMethod {
    //[Electrospray, FAB, LSI, MALDI, Plasma desorption, SELDI, Unknown]
    ELECTROSPRAY("Electrospray"),
    FAB("FAB"),
    LSI("LSI"),
    MALDI("MALDI"),
    PLASMA_DESORPTION("Plasma desorption"),
    SELDI("SELDI"),
    UNKNOWN("Unknown");

    private String value;

    private MassSpectrometryMethod(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }

    public static MassSpectrometryMethod toType(String stringRepresentation) {
        MassSpectrometryMethod result = MassSpectrometryMethod.UNKNOWN;

        if (stringRepresentation.equals("Electrospray")) {
            result = MassSpectrometryMethod.ELECTROSPRAY;
        } else if (stringRepresentation.equals("FAB")) {
            result = MassSpectrometryMethod.FAB;
        } else if (stringRepresentation.equals("LSI")) {
            result = MassSpectrometryMethod.LSI;
        } else if (stringRepresentation.equals("MALDI")) {
            result = MassSpectrometryMethod.MALDI;
        } else if (stringRepresentation.equals("Plasma desorption")) {
            result = MassSpectrometryMethod.PLASMA_DESORPTION;
        } else if (stringRepresentation.equals("SELDI")) {
            result = MassSpectrometryMethod.SELDI;
        }
        return result;
    }
}
