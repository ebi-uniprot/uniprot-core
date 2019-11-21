package org.uniprot.core.uniprot.comment;

import org.uniprot.core.util.EnumDisplay;

public enum InteractionType implements EnumDisplay<InteractionType> {
    SELF,
    XENO,
    BINARY,
    UNKNOWN;

    private final String baseUrl = "https://www.ebi.ac.uk/intact/search/do/search?";

    public String getLink() {
        switch (this) {
            case SELF:
                return baseUrl + "self=";
            case BINARY:
                return baseUrl + "binary=";
            case XENO:
                return baseUrl + "xeno=";
            default:
                return "";
        }
    }

    @Override
    public String toDisplayName() {
        return name();
    }
}
