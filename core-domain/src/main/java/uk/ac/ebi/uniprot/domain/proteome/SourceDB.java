package uk.ac.ebi.uniprot.domain.proteome;

public enum SourceDB {
	ENA ("ENA/EMBL"),
	ENSEMBL( "Ensembl"),
	ENSEMBL_METAZOA( "EnsemblMetazoa"),
	ENSEMBL_PROTISTS( "EnsemblProtists"),
	ENSEMBL_BACTERIA( "EnsemblBacteria"),
	ENSEMBL_FUNGI( "EnsemblFungi"),
	ENSEMBL_PLANTS( "EnsemblPlants"),
	REFSEQ( "Refseq"),
	WORMBASE( "WormBase"),
	VECTORBASE( "VectorBase");
	
	private final String name;
	SourceDB(String name){
		this.name =name;
	}
	public String getName() {
		return name;
	}
	
}
