package uk.ac.ebi.uniprot.domain;

import org.uniprot.core.common.EnumDisplay;

public enum PositionModifier implements EnumDisplay<PositionModifier> {
    EXACT,
    OUTSIDE,
    UNKNOWN,
    UNSURE;

    @Override
    public String toDisplayName() {
        return this.name();
    }
}
