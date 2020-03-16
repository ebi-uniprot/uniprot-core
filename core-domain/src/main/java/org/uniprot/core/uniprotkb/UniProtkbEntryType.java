package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum UniProtkbEntryType implements EnumDisplay<UniProtkbEntryType> {
    SWISSPROT("Swiss-Prot"),
    TREMBL("TrEMBL"),
    INACTIVE("Inactive"),
    UNKNOWN("UNKNOWN");

    private String value;

    UniProtkbEntryType(String type) {
        this.value = type;
    }

    public @Nonnull static UniProtkbEntryType typeOf(@Nonnull String value) {
        for (UniProtkbEntryType entryType : UniProtkbEntryType.values()) {
            if (entryType.getValue().equalsIgnoreCase(value)) {
                return entryType;
            }
        }
        return UniProtkbEntryType.UNKNOWN;
    }

    public @Nonnull String getValue() {
        return value;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return value;
    }
}
