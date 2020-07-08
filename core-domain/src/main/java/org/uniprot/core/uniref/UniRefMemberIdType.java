package org.uniprot.core.uniref;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum UniRefMemberIdType implements EnumDisplay {
    UNIPROTKB("UniProtKB ID", 3),

    UNIPROTKB_SWISSPROT("Reviewed (Swiss-Prot)", 0),
    UNIPROTKB_TREMBL("Unreviewed (TrEMBL)", 1),
    UNIPARC("UniParc", 2);

    private String displayName;
    private final int displayOrder;

    UniRefMemberIdType(String displayName, int displayOrder) {
        this.displayName = displayName;
        this.displayOrder = displayOrder;
    }

    public @Nonnull String getName() {
        return displayName;
    }

    public @Nonnull int getDisplayOrder() {
        return displayOrder;
    }

    public static @Nonnull UniRefMemberIdType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, UniRefMemberIdType.class);
    }
}
