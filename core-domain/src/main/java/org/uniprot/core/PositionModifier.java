package org.uniprot.core;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum PositionModifier implements EnumDisplay<PositionModifier> {
    EXACT,
    OUTSIDE,
    UNKNOWN,
    UNSURE;

    @Override
    public @Nonnull String toDisplayName() {
        return this.name();
    }
}
