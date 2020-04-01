package org.uniprot.core.uniprotkb.comment;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum SequenceCautionType implements EnumDisplay {
    FRAMESHIFT("Frameshift"),
    ERRONEOUS_INITIATION("Erroneous initiation"),
    ERRONEOUS_TERMINATION("Erroneous termination"),
    ERRONEOUS_PREDICTION("Erroneous gene model prediction"),
    ERRONEOUS_TRANSLATION("Erroneous translation"),
    MISCELLANEOUS_DISCREPANCY("Miscellaneous discrepancy"),
    UNKNOWN("unknown");

    private String name;

    SequenceCautionType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull SequenceCautionType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, SequenceCautionType.class);
    }
}
