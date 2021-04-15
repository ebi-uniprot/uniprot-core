package org.uniprot.core.interpro;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/** @author jluo */
public enum Status implements EnumDisplay {
    FALSE_NEGATIVE("N"),
    FALSE_POSITIVE("F"),
    MARGINAL("M"),
    TRUE_POSITIVE("T"),
    PARTIAL("P"),
    UNKNOWN("?");

    private String name;

    private Status(String type) {
        this.name = type;
    }

    public static @Nonnull Status typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, Status.class);
    }

    @Override
    public String getName() {
        return name;
    }
}
