package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum UniProtKBEntryType implements EnumDisplay {
    SWISSPROT("Swiss-Prot"),
    TREMBL("TrEMBL"),
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
        return EnumDisplay.typeOf(name, UniProtKBEntryType.UNKNOWN);
    }
}
