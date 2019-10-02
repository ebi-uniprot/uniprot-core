package org.uniprot.core;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

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
