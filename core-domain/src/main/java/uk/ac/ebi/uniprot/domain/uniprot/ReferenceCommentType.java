package uk.ac.ebi.uniprot.domain.uniprot;

import org.uniprot.core.common.EnumDisplay;

/**
 * @author jluo
 */
public enum ReferenceCommentType implements EnumDisplay<ReferenceCommentType> {
    STRAIN("STRAIN"),
    PLASMID("PLASMID"),
    TRANSPOSON("TRANSPOSON"),
    TISSUE("TISSUE");

    private String value;

    ReferenceCommentType(String type) {
        this.value = type;

    }

    public static ReferenceCommentType typeOf(String value) {
        for (ReferenceCommentType type : ReferenceCommentType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("The ReferenceComment type " + value + " doesn't exist");
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toDisplayName() {
        return value;
    }
}
