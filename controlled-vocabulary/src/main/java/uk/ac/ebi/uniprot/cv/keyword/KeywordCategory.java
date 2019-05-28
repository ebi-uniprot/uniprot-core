package uk.ac.ebi.uniprot.cv.keyword;

import uk.ac.ebi.uniprot.common.EnumDisplay;

public enum KeywordCategory implements EnumDisplay<KeywordCategory> {
	BIOLOGICAL_PROCESS("Biological process", "KW-9999"),
	CELLULAR_COMPONENT("Cellular component", "KW-9998"),
	CODING_SEQUENCE_DIVERSITY("Coding sequence diversity", "KW-9997"),
	DEVELOPMENTAL_STAGE("Developmental stage", "KW-9996"),
	DISEASE("Disease", "KW-9995"),
	DOMAIN("Domain", "KW-9994"),
	LIGAND("Ligand", "KW-9993"),
	MOLECULAR_FUNCTION("Molecular function", "KW-9992"),
	PTM("PTM", "KW-9991"),	
	TECHNICAL_TERM("Technical term", "KW-9990"),
	UNKNOWN("Unknown", "KW-0000");
	
	private final String name;
	private final String accession;
	KeywordCategory(String name, String accession){
		this.name = name;
		this.accession =accession;
	}
	public String getName() {
		return name;
	}
	public String getAccession() {
		return accession;
	}
	public static KeywordCategory fromValue(String name) {
		for (KeywordCategory cat: KeywordCategory.values()) {
			if(name.equals(cat.getName()))
				return cat;
		}
		return KeywordCategory.UNKNOWN;
	}
	
	public static KeywordCategory fromAccession(String accession) {
		for (KeywordCategory cat: KeywordCategory.values()) {
			if(accession.equals(cat.getAccession()))
				return cat;
		}
		return KeywordCategory.UNKNOWN;
	}
	@Override
	public String toDisplayName() {
		return getName();
	}
}
