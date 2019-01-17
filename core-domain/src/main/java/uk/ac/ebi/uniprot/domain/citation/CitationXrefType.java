package uk.ac.ebi.uniprot.domain.citation;

import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.EnumDisplay;

public enum CitationXrefType implements DatabaseType, EnumDisplay<CitationXrefType> {

    PUBMED("PubMed"),
    DOI("DOI"),
    AGRICOLA("AGRICOLA");

    private String value;

    private CitationXrefType(String type) {
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
