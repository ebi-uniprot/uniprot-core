package org.uniprot.core.cv.go;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum GoAspect implements EnumDisplay<GoAspect> {
    FUNCTION("GO Molecular Function"),
    PROCESS("GO Biological Process"),
    COMPONENT("GO Cellular Component");
    private final String displayName;

    GoAspect(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return this.displayName;
    }
}
