package uk.ac.ebi.uniprot.domain.citation;

import uk.ac.ebi.uniprot.domain.DatabaseType;

public enum CitationXrefType implements DatabaseType {

    PUBMED("PubMed"),
    DOI("DOI"),
    AGRICOLA("AGRICOLA");

    private String value;

    private CitationXrefType(String type){
        this.value = type;
    }

    public String getName() {
        return value;
    }

    public static CitationXrefType typeOf(String value) {
        for (CitationXrefType citXrefType : CitationXrefType.values()) {
            if (citXrefType.getName().equalsIgnoreCase(value)) {
                return citXrefType;
            }
        }
        throw new IllegalArgumentException("the citation cross-reference type " + value + " doesn't exist");
    }


}
