package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum PhysiologicalDirectionType implements EnumDisplay {
    LEFT_TO_RIGHT("left-to-right"),
    RIGHT_TO_LEFT("right-to-left");

    private final String name;

    PhysiologicalDirectionType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull PhysiologicalDirectionType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, PhysiologicalDirectionType.class);
    }
}
