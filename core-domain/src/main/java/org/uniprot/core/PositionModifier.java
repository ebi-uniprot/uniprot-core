package org.uniprot.core;

import org.uniprot.core.util.EnumDisplay;

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
