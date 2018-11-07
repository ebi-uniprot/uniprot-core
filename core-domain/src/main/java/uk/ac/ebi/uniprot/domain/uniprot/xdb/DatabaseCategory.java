package uk.ac.ebi.uniprot.domain.uniprot.xdb;



public enum DatabaseCategory {
	SEQUENCE_DATABASES("SEQ", "Sequence databases"),
	D3_STRUCTURE_DATABASES("3DS", "3D structure databases"),
	PROTEIN_PROTEIN_INTERACTION_DATABASES("PPI", "Protein-protein interaction databases"),
	CHEMISTRY("CHEMISTRY","Chemistry"),
	PROTEIN_FAMILY_GROUP_DATABASES("PFAM", "Protein family/group databases"),
	PTM_DATABASES("PTM", "PTM databases"),
	POLYMORPHISM_AND_MUTATION_DATABASES("PMD", "Polymorphism and mutation databases"),
	D2_GEL_DATABASES("2DG", "2D gel databases"),
	PROTEOMIC_DATABASES("PROTEOMIC", "Proteomic databases"),
	PROTOCOLS_AND_MATERIALS_DATABASES("PAM","Protocols and materials databases"),
	GENOME_ANNOTATION_DATABASES("GMA", "Genome annotation databases"),
	ORGANISM_SPECIFIC_DATABASES("ORG","Organism-specific databases"),
	PHYLOGENOMIC_DATABASES("PLG", "Phylogenomic databases"),
	ENZYME_AND_PATHWAY_DATABASES("EAP", "Enzyme and pathway databases"),
	OTHER("OTHER", "Other"),
	GENE_EXPRESSION_DATABASES("GEP", "Gene expression databases"),
	FAMILY_AND_DOMAIN_DATABASES("FMD", "Family and domain databases"),
	GENE_ONTOLOGY_DATABASES("OTG", "Gene ontology databases", false),
	
	PROTEOMES_DATABASES("PRM", "Proteomes databases", false),
	
	UNKNOWN("UNK", "Unknown", false)
	;
	
	
	
	private final String name;
	private final String displayName;
	private final boolean searchable;
	DatabaseCategory(String name){
		this(name, name);
	}
	DatabaseCategory(String name, String displayName){
		this(name, displayName, true);
	}
	
	DatabaseCategory(String name, String displayName, boolean searchable){
		this.name = name;
		this.displayName = displayName;
		this.searchable = searchable;
	}
	  public String getName() {
	        return name;
	    }
	public String getDisplayName() {
		return this.displayName;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public static DatabaseCategory typeOf(String value) {
		for (DatabaseCategory type : DatabaseCategory.values()) {
			if (type.getName().equals(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("the database category " + value + " doesn't exist");	
	}
}
