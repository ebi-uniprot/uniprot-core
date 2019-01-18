package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.EnumDisplay;

public enum APEventType implements EnumDisplay<APEventType> {
    ALTERNATIVE_PROMOTER_USAGE("Alternative promoter usage"),
    ALTERNATIVE_SPLICING("Alternative splicing"),
    ALTERNATIVE_INITIATION("Alternative initiation"),
    RIBOSOMAL_FRAMESHIFTING("Ribosomal frameshifting");

    private final String name;

    APEventType(String name) {
        this.name = name;
    }

    public static APEventType typeOf(String value) {
        for (APEventType type : APEventType
                .values()) {
            if (type.getName().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("The AP event type: " + value + " doesn't exist");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toDisplayName() {
        return name;
    }
}