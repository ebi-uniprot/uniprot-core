package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.EnumDisplay;

public enum InactiveReasonType implements EnumDisplay<InactiveReasonType> {
	DELETED,
	MERGED,
	DEMERGED;

	@Override
	public String toDisplayName() {
		return name();
	}
}
