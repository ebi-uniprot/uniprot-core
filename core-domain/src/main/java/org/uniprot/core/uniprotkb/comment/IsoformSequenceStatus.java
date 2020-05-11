package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum IsoformSequenceStatus implements EnumDisplay {
    DISPLAYED("displayed", "Displayed"),
    EXTERNAL("external", "External"),
    NOT_DESCRIBED("not described", "Not described"),
    DESCRIBED("described", "Described");

    private String name;
    private String displayValue;

    IsoformSequenceStatus(String name, String displayValue) {
        this.name = name;
        this.displayValue = displayValue;
    }

    public @Nonnull String getName() {
        return this.name;
    }

    public @Nonnull String getDisplayName() {
        return displayValue;
    }

    public static @Nonnull IsoformSequenceStatus typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, IsoformSequenceStatus.class);
    }
}
