package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.EnumDisplay;

/**
 * 
 * @author jieluo
 * @date   17 Jan 2017
 * @time   18:41:43
 *
 */
public enum UniProtEntryType implements EnumDisplay<UniProtEntryType> {

    SWISSPROT("Swiss-Prot"),
    TREMBL("TrEMBL"),
    UNKNOWN("UNKNOWN");

    private String value;

    UniProtEntryType(String type) {
        this.value = type;
    }

    public String getValue() {
        return value.toString();
    }

    public static UniProtEntryType typeOf(String value) {
        for (UniProtEntryType entryType : UniProtEntryType.values()) {
            if (entryType.getValue().equals(value)) {
                return entryType;
            }
        }
        throw new IllegalArgumentException("the entry type " + value + " doesn't exist");
    }

    @Override
    public String toDisplayName() {
        return value;
    }}
