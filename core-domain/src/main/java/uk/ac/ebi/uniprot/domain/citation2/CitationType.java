package uk.ac.ebi.uniprot.domain.citation2;


import uk.ac.ebi.uniprot.domain.EnumDisplay;

public enum CitationType implements EnumDisplay<CitationType> {

    JOURNAL_ARTICLE("journal article"),
    BOOK("book"),
    ELECTRONIC_ARTICLE("online journal article"),
    PATENT("patent"),
    SUBMISSION("submission", "Unpublished/no plans to publish"),
    THESIS("thesis"),
    UNPUBLISHED("unpublished"),
    UNKNOWN("default as unknown");

    private String value;
    private String displayName;

    CitationType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    private CitationType(String type) {
        this(type, type);
    }

    public static CitationType citationTypeOf(String value) {
        for (CitationType type : CitationType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("The citation with the description " + value + " doesn't exist");
    }

    public static CitationType typeOf(String value) {
        for (CitationType citationType : CitationType
                .values()) {

            if (citationType.getValue().equals(value)) {
                return citationType;
            }
        }
        throw new IllegalArgumentException("The citation with the description " + value + " doesn't exist");
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toDisplayName() {
        return value;
    }
}

