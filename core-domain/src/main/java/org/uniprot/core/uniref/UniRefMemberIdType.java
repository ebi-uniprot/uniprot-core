package org.uniprot.core.uniref;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum UniRefMemberIdType implements EnumDisplay {
    UNIPROTKB("UniProtKB ID"),
    UNIPARC("UniParc ID");

    private String displayName;

    UniRefMemberIdType(String displayName) {
        this.displayName = displayName;
    }

    public @Nonnull String getName() {
        return displayName;
    }

    public static @Nonnull UniRefMemberIdType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, UniRefMemberIdType.class);
    }
}
