package uk.ac.ebi.uniprot.domain.proteome;

import uk.ac.ebi.uniprot.domain.DatabaseType;

public enum ProteomeXReferenceType implements DatabaseType {
	GENOME_ASSEMBLY("GenomeAssembly"),
	GENOME_ANNOTATION("GenomeAnnotation"),
	GENOME_ACCESSION("GenomeAccession"),
	BIOSAMPLE("Biosample");
	
	
	private final String name;
	ProteomeXReferenceType(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}

}
