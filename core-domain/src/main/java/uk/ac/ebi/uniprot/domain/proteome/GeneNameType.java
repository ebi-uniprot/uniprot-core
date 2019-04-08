package uk.ac.ebi.uniprot.domain.proteome;

public enum GeneNameType {
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
}
