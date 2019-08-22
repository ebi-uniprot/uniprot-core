package org.uniprot.core.citation;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

public enum CitationXrefType implements DatabaseType, EnumDisplay<CitationXrefType> {

    PUBMED("PubMed"),
    DOI("DOI"),
    AGRICOLA("AGRICOLA");

    private String value;

    CitationXrefType(String type) {
        this.value = type;
    }

    public static CitationXrefType typeOf(String value) {
        for (CitationXrefType citXrefType : CitationXrefType
                .values()) {
            if (citXrefType.getName().equalsIgnoreCase(value)) {
                return citXrefType;
            }
        }
        throw new IllegalArgumentException("the citation cross-reference type " + value + " doesn't exist");
    }

    public String getName() {
        return value;
    }

    @Override
    public String toDisplayName() {
        return getName();
    }

}