package uk.ac.ebi.uniprot.cv.keyword;

import uk.ac.ebi.uniprot.common.EnumDisplay;

public enum KeywordCategory implements EnumDisplay<KeywordCategory> {
	BIOLOGICAL_PROCESS("Biological process"),
	CELLULAR_COMPONENT("Cellular component"),
	CODING_SEQUENCE_DIVERSITY("Coding sequence diversity"),
	DEVELOPMENTAL_STAGE("Developmental stage"),
	DISEASE("Disease"),
	DOMAIN("Domain"),
	LIGAND("Ligand"),
	MOLECULAR_FUNCTION("Molecular function"),
	PTM("PTM"),	
	TECHNICAL_TERM("Technical term"),
	UNKNOWN("Unknown");
	
	private final String name;
	KeywordCategory(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public static KeywordCategory fromValue(String name) {
		for (KeywordCategory cat: KeywordCategory.values()) {
			if(name.equals(cat.getName()))
				return cat;
		}
		return KeywordCategory.UNKNOWN;
	}
	@Override
	public String toDisplayName() {
		return getName();
	}
}
