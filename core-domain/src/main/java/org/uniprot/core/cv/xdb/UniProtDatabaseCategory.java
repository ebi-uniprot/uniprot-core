package org.uniprot.core.cv.xdb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum UniProtDatabaseCategory implements EnumDisplay {
    SEQUENCE_DATABASES("SEQ", "Sequence databases"),
    D3_STRUCTURE_DATABASES("3DS", "3D structure databases"),
    PROTEIN_PROTEIN_INTERACTION_DATABASES("PPI", "Protein-protein interaction databases"),
    CHEMISTRY("CHEMISTRY", "Chemistry"),
    PROTEIN_FAMILY_GROUP_DATABASES("PFAM", "Protein family/group databases"),
    PTM_DATABASES("PTM", "PTM databases"),
    GENERIC_VARIATION_DATABASES("GVD", "Genetic variation databases"),
    D2_GEL_DATABASES("2DG", "2D gel databases"),
    PROTEOMIC_DATABASES("PROTEOMIC", "Proteomic databases"),
    PROTOCOLS_AND_MATERIALS_DATABASES("PAM", "Protocols and materials databases"),
    GENOME_ANNOTATION_DATABASES("GMA", "Genome annotation databases"),
    ORGANISM_SPECIFIC_DATABASES("ORG", "Organism-specific databases"),
    PHYLOGENOMIC_DATABASES("PLG", "Phylogenomic databases"),
    ENZYME_AND_PATHWAY_DATABASES("EAP", "Enzyme and pathway databases"),
    MISCELLANEOUS("MISC", "Miscellaneous"),
    GENE_EXPRESSION_DATABASES("GEP", "Gene expression databases"),
    FAMILY_AND_DOMAIN_DATABASES("FMD", "Family and domain databases"),

    GENE_ONTOLOGY_DATABASES("OTG", "Ontologies", false),
    PROTEOMES_DATABASES("PRM", "Proteomes databases", false),
    UNKNOWN("UNK", "Unknown", false);

    private final String name;
    private final String displayName;
    private final boolean searchable;

    UniProtDatabaseCategory(String name, String displayName) {
        this(name, displayName, true);
    }

    UniProtDatabaseCategory(String name, String displayName, boolean searchable) {
        this.name = name;
        this.displayName = displayName;
        this.searchable = searchable;
    }

    public @Nonnull String getName() {
        return name;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public @Nonnull String getDisplayName() {
        return this.displayName;
    }

    public static @Nonnull UniProtDatabaseCategory typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, UniProtDatabaseCategory.class);
    }
}
