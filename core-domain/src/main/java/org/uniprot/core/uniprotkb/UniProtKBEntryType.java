package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum UniProtKBEntryType implements EnumDisplay<UniProtKBEntryType> {
    SWISSPROT("UniProtKB reviewed (Swiss-Prot)"),
    TREMBL("UniProtKB unreviewed (TrEMBL)"),
    INACTIVE("Inactive"),
    UNKNOWN("UNKNOWN");

    private String value;

    UniProtKBEntryType(String type) {
        this.value = type;
    }

    public @Nonnull static UniProtKBEntryType typeOf(@Nonnull String value) {
        for (UniProtKBEntryType entryType : UniProtKBEntryType.values()) {
            if (entryType.getValue().equalsIgnoreCase(value)) {
                return entryType;
            }
        }
        return UniProtKBEntryType.UNKNOWN;
    }

    public @Nonnull String getValue() {
        return value;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return value;
    }
}
