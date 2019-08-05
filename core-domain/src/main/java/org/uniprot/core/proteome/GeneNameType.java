package org.uniprot.core.proteome;

import org.uniprot.core.util.EnumDisplay;


public enum GeneNameType implements EnumDisplay<GeneNameType> {
	MOD("MOD"),
	ENSEMBL("Ensembl"),
	OLN("OLN"),
	ORF("ORF"),
	GENE_NAME("Gene name"),
	MISSING ("Missing");
	
	private final String name;
	GeneNameType(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toDisplayName() {
		return getName();
	}
	public static GeneNameType fromValue(String type) {
		for(GeneNameType gnType: GeneNameType.values()) {
			if(gnType.getName().equals(type))
				return gnType;
		}
		return GeneNameType.MISSING;
	}
}
