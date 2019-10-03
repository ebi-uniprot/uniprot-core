package org.uniprot.core.uniprot;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum UniProtEntryType implements EnumDisplay<UniProtEntryType> {
    SWISSPROT("Swiss-Prot"),
    TREMBL("TrEMBL"),
    INACTIVE("Inactive"),
    UNKNOWN("UNKNOWN");

    private String value;

    UniProtEntryType(String type) {
        this.value = type;
    }

    public @Nonnull static UniProtEntryType typeOf(@Nullable String value) {
        for (UniProtEntryType entryType : UniProtEntryType.values()) {
            if (entryType.getValue().equalsIgnoreCase(value)) {
                return entryType;
            }
        }
        return UniProtEntryType.UNKNOWN;
    }

    public @Nonnull String getValue() {
        return value;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return value;
    }
}
