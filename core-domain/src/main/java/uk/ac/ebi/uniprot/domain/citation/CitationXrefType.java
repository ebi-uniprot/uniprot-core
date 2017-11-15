package uk.ac.ebi.uniprot.domain.citation;

public enum CitationXrefType {

    PUBMED("PubMed"),
    DOI("DOI"),
    AGRICOLA("AGRICOLA");

    private String value;

    private CitationXrefType(String type){
        this.value = type;
    }

    public String getValue() {
        return value;
    }

    public static CitationXrefType typeOf(String value) {
        for (CitationXrefType citXrefType : CitationXrefType.values()) {
            if (citXrefType.getValue().equalsIgnoreCase(value)) {
                return citXrefType;
            }
        }
        throw new IllegalArgumentException("the citation cross-reference type " + value + " doesn't exist");
    }


}
