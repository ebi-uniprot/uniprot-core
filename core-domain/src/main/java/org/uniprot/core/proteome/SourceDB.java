package org.uniprot.core.proteome;

import org.uniprot.core.util.EnumDisplay;

public enum SourceDB implements EnumDisplay<SourceDB> {
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
	@Override
	public String toDisplayName() {
		return getName();
	}
	
}
