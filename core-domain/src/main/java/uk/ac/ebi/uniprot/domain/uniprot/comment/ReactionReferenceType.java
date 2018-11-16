package uk.ac.ebi.uniprot.domain.uniprot.comment;

public enum ReactionReferenceType {
	CHEBI("ChEBI"),
	RHEA("Rhea"),
	UNKNOWN("Unknown");
	private String displayName;

	private ReactionReferenceType(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * String representation of the external source
	 *
	 * @return the name of the source
	 */
	public String toDisplayName() {
		return displayName;
	}

	/**
	 * Provided a string representation of a source, return the enum type.
	 *
	 * @param value string representation of a source.
	 * @return the enum type of the provided source.
	 * @throws IllegalArgumentException is thrown when the provided value has no corresponding enum type.
	 */
	public static ReactionReferenceType typeOf(String value) {
		for (ReactionReferenceType referenceType : ReactionReferenceType.values()) {
			if (referenceType.toDisplayName().equalsIgnoreCase(value.trim())) {
				return referenceType;
			}
		}
		return ReactionReferenceType.UNKNOWN;
	}
}
