package uk.ac.ebi.uniprot.domain.proteome;

import uk.ac.ebi.uniprot.common.EnumDisplay;

public enum ProteomeType implements EnumDisplay<ProteomeType> {
	NORMAL ("Complete proteome"),
	REFERENCE ("Reference proteome"),
	REPRESENTATIVE ("Representative proteome"),
	REDUNDANT ("Redundant proteome"),
	IN_COMPLETE ("Incomplete proteome");

	private final String name;
	ProteomeType(String name) {
		this.name= name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toDisplayName() {
		return getName();
	}
	
}
