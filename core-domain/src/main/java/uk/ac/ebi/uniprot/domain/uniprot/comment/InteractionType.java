package uk.ac.ebi.uniprot.domain.uniprot.comment;

public enum InteractionType {
    SELF,
    XENO,
    BINARY,
    UNKNOWN;

    public String getLink() {

        switch (this) {
            case SELF:
                return "https://www.ebi.ac.uk/intact/search/do/search?self=";
            case BINARY:
                return "https://www.ebi.ac.uk/intact/search/do/search?binary=";
            case XENO:
                return "https://www.ebi.ac.uk/intact/search/do/search?xeno=";
            case UNKNOWN:
                return "";
        }

        return "";
    }

}
