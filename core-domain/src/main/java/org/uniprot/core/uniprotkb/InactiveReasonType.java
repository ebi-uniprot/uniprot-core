package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum InactiveReasonType implements EnumDisplay {
    DELETED,
    MERGED,
    DEMERGED;

    public @Nonnull String getName() {
        return name();
    }

    public static @Nonnull InactiveReasonType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, InactiveReasonType.class);
    }
}
