package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum ReferenceCommentType implements EnumDisplay {
    STRAIN,
    PLASMID,
    TRANSPOSON,
    TISSUE;

    public @Nonnull String getName() {
        return name();
    }

    public static @Nonnull ReferenceCommentType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, ReferenceCommentType.class);
    }
}
