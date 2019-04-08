package uk.ac.ebi.uniprot.domain.proteome;

public enum Superkingdom {
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
	
}
