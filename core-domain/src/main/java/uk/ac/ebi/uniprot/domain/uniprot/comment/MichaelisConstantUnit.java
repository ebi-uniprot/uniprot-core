package uk.ac.ebi.uniprot.domain.uniprot.comment;

import org.uniprot.core.common.EnumDisplay;

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
        for (MichaelisConstantUnit value : MichaelisConstantUnit
                .values()) {
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

    /**
     * Added for beans - just use this rather than toDisplayNameString?
     *
     * @return String - the display name
     */
    public String getDisplayString() {
        return toDisplayNameString();
    }

    public String toDisplayNameString() {
        return name;
    }
}
