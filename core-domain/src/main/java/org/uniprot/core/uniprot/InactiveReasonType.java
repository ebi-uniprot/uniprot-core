package org.uniprot.core.uniprot;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum InactiveReasonType implements EnumDisplay<InactiveReasonType> {
    DELETED,
    MERGED,
    DEMERGED;

    @Override
    public @Nonnull
    String toDisplayName() {
        return name();
    }
}
