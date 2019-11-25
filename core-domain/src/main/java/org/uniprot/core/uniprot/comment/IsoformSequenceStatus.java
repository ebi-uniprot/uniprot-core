package org.uniprot.core.uniprot.comment;

import org.uniprot.core.util.EnumDisplay;

public enum IsoformSequenceStatus implements EnumDisplay<IsoformSequenceStatus> {
    DISPLAYED("displayed", "Displayed"),
    EXTERNAL("external", "External"),
    NOT_DESCRIBED("not described", "Not described"),
    DESCRIBED("described", "Described");

    private String value;
    private String displayValue;

    IsoformSequenceStatus(String type, String displayValue) {
        this.value = type;
        this.displayValue = displayValue;
    }

    public static IsoformSequenceStatus typeOf(String value) {
        for (IsoformSequenceStatus status : IsoformSequenceStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(
                "the IsoformSequenceStatus with the description " + value + " doesn't exist");
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toDisplayName() {
        return displayValue;
    }
}
