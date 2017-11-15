package uk.ac.ebi.uniprot.domain.citation;


public enum CitationType {

	JOURNAL_ARTICLE("journal article"),
	UNPUBLISHED_JOURNAL_ARTICLE("unpublished journal article"),
	BOOK("book"),
	ELECTRONIC_ARTICLE("online journal article"),
	PATENT("patent"),
	SUBMISSION("submission", "Unpublished/no plans to publish"),
	THESIS("thesis"),
	UNPUBLISHED_OBSERVATIONS("unpublished observations"),
	UNKNOWN("default as unknown");

	private String value;
	private String displayName;

	CitationType(String value,
                     String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	private CitationType(String type) {
		this(type, type);
	}

	public String getValue() {
		return value;
	}

	public String getDisplayName() {
		return displayName;
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
		for (CitationType citationType : CitationType.values()) {

			if (citationType.getValue().equals(value)) {
				return citationType;
			}
		}
		throw new IllegalArgumentException("The citation with the description " + value + " doesn't exist");
	}

}

