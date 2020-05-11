package org.uniprot.core.uniprotkb;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum UniProtKBEntryType implements EnumDisplay {
    SWISSPROT("UniProtKB reviewed (Swiss-Prot)"),
    TREMBL("UniProtKB unreviewed (TrEMBL)"),
    INACTIVE("Inactive"),
    UNKNOWN("UNKNOWN");

    private String name;

    UniProtKBEntryType(String type) {
        this.name = type;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull UniProtKBEntryType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, UniProtKBEntryType.class, UniProtKBEntryType.UNKNOWN);
    }
}
