package org.uniprot.core.uniprot;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:41:43
 */
public enum UniProtEntryType implements EnumDisplay<UniProtEntryType> {
    SWISSPROT("Swiss-Prot"),
    TREMBL("TrEMBL"),
    INACTIVE("Inactive"),
    UNKNOWN("UNKNOWN");

    private String value;

    UniProtEntryType(String type) {
        this.value = type;
    }

    public static UniProtEntryType typeOf(String value) {
        for (UniProtEntryType entryType : UniProtEntryType.values()) {
            if (entryType.getValue().equals(value)) {
                return entryType;
            }
        }
        throw new IllegalArgumentException("the entry type " + value + " doesn't exist");
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toDisplayName() {
        return value;
    }
}
