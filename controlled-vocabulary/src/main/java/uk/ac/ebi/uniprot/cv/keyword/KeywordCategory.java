package uk.ac.ebi.uniprot.cv.keyword;

public enum KeywordCategory {
	BIOLOGICAL_PROCESS("Biological process"),
	CELLULAR_COMPONENT("Cellular component"),
	CODING_SEQUENCE_DIVERSITY("Coding sequence diversity"),
	DEVELOPMENTAL_STAGE("Developmental stage"),
	DISEASE("Disease"),
	DOMAIN("Domain"),
	LIGAND("Ligand"),
	MOLECULAR_FUNCTION("Molecular function"),
	PTM("PTM"),
	
	TECHNICAL_TERM("Technical term");
	
	private final String name;
	KeywordCategory(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public static KeywordCategory typeOf(String name) {
		for (KeywordCategory cat: KeywordCategory.values()) {
			if(name.equals(cat.getName()))
				return cat;
		}
		throw new IllegalArgumentException (name + " is not valid keyword category");
	}
}
