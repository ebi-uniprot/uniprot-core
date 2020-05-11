package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum APEventType implements EnumDisplay {
    ALTERNATIVE_PROMOTER_USAGE("Alternative promoter usage"),
    ALTERNATIVE_SPLICING("Alternative splicing"),
    ALTERNATIVE_INITIATION("Alternative initiation"),
    RIBOSOMAL_FRAMESHIFTING("Ribosomal frameshifting");

    private final String name;

    APEventType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull APEventType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, APEventType.class);
    }
}
