package org.uniprot.core.uniref;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum GoTermType implements EnumDisplay<GoTermType> {
    FUNCTION("GO Molecular Function"),
    PROCESS("GO Biological Process"),
    COMPONENT("GO Cellular Component");
    private final String displayName;

    GoTermType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return this.displayName;
    }
}
