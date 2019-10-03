package org.uniprot.core.uniprot;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum InactiveReasonType implements EnumDisplay<InactiveReasonType> {
    DELETED,
    MERGED,
    DEMERGED;

    @Override
    public @Nonnull String toDisplayName() {
        return name();
    }
}
