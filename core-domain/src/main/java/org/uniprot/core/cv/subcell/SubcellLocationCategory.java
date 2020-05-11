package org.uniprot.core.cv.subcell;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum SubcellLocationCategory implements EnumDisplay {
    LOCATION("Cellular component"),
    TOPOLOGY("Topology"),
    ORIENTATION("Orientation");

    String name;

    SubcellLocationCategory(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return this.name;
    }

    public static @Nonnull SubcellLocationCategory typeOf(@Nonnull String category) {
        return EnumDisplay.typeOf(category, SubcellLocationCategory.class);
    }
}
