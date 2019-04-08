package uk.ac.ebi.uniprot.domain.proteome;

import uk.ac.ebi.uniprot.common.EnumDisplay;

public enum Superkingdom implements EnumDisplay<Superkingdom> {
	ARCHAEA("archaea"),
	VIRUSES("viruses"),
	BACTERIA("bacteria"),
	EUKARYOTA("eukaryota");
	
	private final String name;
	
	Superkingdom(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toDisplayName() {
		return getName();
	}
	
}
