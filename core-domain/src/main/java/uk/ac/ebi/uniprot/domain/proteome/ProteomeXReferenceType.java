package uk.ac.ebi.uniprot.domain.proteome;

import uk.ac.ebi.uniprot.common.EnumDisplay;
import uk.ac.ebi.uniprot.domain.DatabaseType;

public enum ProteomeXReferenceType implements DatabaseType ,  EnumDisplay<ProteomeXReferenceType> {
	GENOME_ASSEMBLY("GenomeAssembly"),
	GENOME_ANNOTATION("GenomeAnnotation"),
	GENOME_ACCESSION("GenomeAccession"),
	ASSEMBLY_ID("AssemblyId"),
	BIOSAMPLE("Biosample"),
	UNKNOWN("Unknown");
	
	
	private final String name;
	ProteomeXReferenceType(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String toDisplayName() {
		return getName();
	}
	public static ProteomeXReferenceType fromValue(String type) {
		for(ProteomeXReferenceType gnType: ProteomeXReferenceType.values()) {
			if(gnType.getName().equalsIgnoreCase(type))
				return gnType;
		}
		return ProteomeXReferenceType.UNKNOWN;
	}
}
