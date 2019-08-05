package uk.ac.ebi.uniprot.domain.uniprot.comment;

import org.uniprot.core.common.EnumDisplay;

/**
 * User: mkleen@ebi.ac.uk
 * Date: 28-Nov-2005
 * Time: 11:24:21
 */
public enum IsoformSequenceStatus implements EnumDisplay<IsoformSequenceStatus> {

    DISPLAYED("displayed","Displayed"),
    EXTERNAL("external", "External"),
    NOT_DESCRIBED("not described", "Not described"),
    DESCRIBED("described", "Described");

    private String value;
    private String displayValue;

    IsoformSequenceStatus(String type,String displayValue) {
        this.value = type;
        this.displayValue = displayValue;
    }

    public static IsoformSequenceStatus typeOf(String value) {
        for (IsoformSequenceStatus status : IsoformSequenceStatus
                .values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("the IsoformSequenceStatus with the description " + value + " doesn't exist");
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toDisplayName() {
        return displayValue;
    }
}
