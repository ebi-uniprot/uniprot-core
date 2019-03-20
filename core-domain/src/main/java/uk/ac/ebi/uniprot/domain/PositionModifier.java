package uk.ac.ebi.uniprot.domain;

import uk.ac.ebi.uniprot.cv.common.EnumDisplay;

public enum PositionModifier implements EnumDisplay<PositionModifier> {
    EXACT,
    OUTSIDE,
    UNKOWN,
    UNSURE;

    @Override
    public String toDisplayName() {
        return this.name();
    }
}
