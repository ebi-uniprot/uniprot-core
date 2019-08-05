package uk.ac.ebi.uniprot.domain.uniprot;

import org.uniprot.core.common.EnumDisplay;

public enum InactiveReasonType implements EnumDisplay<InactiveReasonType> {
    DELETED,
    MERGED,
    DEMERGED;

    @Override
    public String toDisplayName() {
        return name();
    }
}
