package org.uniprot.core.cv.subcell;

import uk.ac.ebi.uniprot.common.EnumDisplay;

public enum SubcellLocationCategory implements EnumDisplay<SubcellLocationCategory> {
    LOCATION("Cellular component"),
    TOPOLOGY("Topology"),
    ORIENTATION("Orientation");

    String category;

    SubcellLocationCategory(String category) {
        this.category = category;
    }

    @Override
    public String toDisplayName() {
        return this.category;
    }

    public String getCategory() {
        return this.category;
    }
}
