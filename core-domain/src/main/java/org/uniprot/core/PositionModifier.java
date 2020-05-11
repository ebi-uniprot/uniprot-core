package org.uniprot.core;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum PositionModifier implements EnumDisplay {
    EXACT,
    OUTSIDE,
    UNKNOWN,
    UNSURE;

    public @Nonnull String getName() {
        return name();
    }

    public static @Nonnull PositionModifier typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, PositionModifier.class);
    }
}
