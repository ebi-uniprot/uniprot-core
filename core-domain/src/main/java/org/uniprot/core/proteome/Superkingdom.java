package org.uniprot.core.proteome;

import org.uniprot.core.util.EnumDisplay;

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
	public static Superkingdom fromValue(String type) {
		for(Superkingdom gnType: Superkingdom.values()) {
			if(gnType.getName().equals(type))
				return gnType;
		}
		  throw new IllegalArgumentException(type);
	}
}
