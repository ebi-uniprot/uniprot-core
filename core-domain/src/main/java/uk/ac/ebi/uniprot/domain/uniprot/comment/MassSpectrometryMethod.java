package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.cv.common.EnumDisplay;

public enum MassSpectrometryMethod implements EnumDisplay<MassSpectrometryMethod> {
    //[Electrospray, FAB, LSI, MALDI, Plasma desorption, SELDI, Unknown]
    ELECTROSPRAY("Electrospray"),
    FAB("FAB"),
    LSI("LSI"),
    MALDI("MALDI"),
    PLASMA_DESORPTION("Plasma desorption"),
    SELDI("SELDI"),
    UNKNOWN("Unknown");

    private String value;

    MassSpectrometryMethod(String type) {
        this.value = type;
    }

    public static MassSpectrometryMethod toType(String type) {
        for (MassSpectrometryMethod method : MassSpectrometryMethod
                .values()) {
            if (method.getValue().equals(type))
                return method;
        }
        return MassSpectrometryMethod.UNKNOWN;

    }

    public String getValue() {
        return value;
    }

    @Override
    public String toDisplayName() {
        return value;
    }
}
