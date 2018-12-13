package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.EnumDisplay;

/**
 * User: mkleen@ebi.ac.uk
 * Date: 28-Nov-2005
 * Time: 11:24:21
 */
public enum IsoformSequenceStatus implements EnumDisplay<IsoformSequenceStatus> {

	DISPLAYED ("displayed"),
	EXTERNAL ("external"),
	NOT_DESCRIBED ("not described"),
	DESCRIBED ("described");

	private String value;

	private IsoformSequenceStatus(String type) {
		this.value = type;
	}

	public String getValue() {
		return this.value;
	}

		public static IsoformSequenceStatus typeOf(String value) {
		for (IsoformSequenceStatus status : IsoformSequenceStatus.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("the IsoformSequenceStatus with the description " + value + " doesn't exist");
	}

	@Override
	public String toDisplayName() {
		return value;
	}}
