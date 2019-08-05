package org.uniprot.core.uniprot;

import org.uniprot.core.util.EnumDisplay;

public enum InactiveReasonType implements EnumDisplay<InactiveReasonType> {
    DELETED,
    MERGED,
    DEMERGED;

    @Override
    public String toDisplayName() {
        return name();
    }
}
