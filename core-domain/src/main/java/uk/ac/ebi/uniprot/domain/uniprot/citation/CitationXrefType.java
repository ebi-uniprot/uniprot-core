package uk.ac.ebi.uniprot.domain.uniprot.citation;

/**
 * Created by IntelliJ IDEA.
 * User: barrera
 * Date: 24/05/11
 * Time: 14:59
 */

public enum CitationXrefType {

    PUBMED("PUBMED"),
    DOI("DOI"),
    MEDLINE("MEDLINE"),
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
            if (citXrefType.getValue().equals(value)) {
                return citXrefType;
            }
        }
        throw new IllegalArgumentException("the citation cross-reference type " + value + " doesn't exist");
    }


}
