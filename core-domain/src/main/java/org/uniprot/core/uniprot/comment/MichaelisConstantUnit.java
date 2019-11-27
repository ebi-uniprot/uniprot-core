package org.uniprot.core.uniprot.comment;

import org.uniprot.core.util.EnumDisplay;

public enum MichaelisConstantUnit implements EnumDisplay<MichaelisConstantUnit> {
    MOL("M"),
    MILLI_MOL("mM"),
    MICRO_MOL("uM"),
    NANO_MOL("nM"),
    MG_ML_2("mg/mL"),
    MG_ML("mg/ml");

    private String name;

    MichaelisConstantUnit(String name) {
        this.name = name;
    }

    public static MichaelisConstantUnit convert(String unit) {
        for (MichaelisConstantUnit value : MichaelisConstantUnit.values()) {
            if (value.name.equals(unit)) {
                return value;
            }
        }
        throw new RuntimeException("unknown Michaelis Constant Unit: " + unit);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toDisplayName() {
        return getName();
    }

    public String toDisplayNameString() {
        return name;
    }
}
