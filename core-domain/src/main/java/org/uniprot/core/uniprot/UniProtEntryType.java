package org.uniprot.core.uniprot;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.util.EnumDisplay;

public enum UniProtEntryType implements EnumDisplay<UniProtEntryType> {
    SWISSPROT("Swiss-Prot"),
    TREMBL("TrEMBL"),
    INACTIVE("Inactive"),
    UNKNOWN("UNKNOWN");

    private String value;

    UniProtEntryType(String type) {
        this.value = type;
    }

    public @Nonnull static UniProtEntryType typeOf(@Nonnull String value) {
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
