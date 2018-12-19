package uk.ac.ebi.uniprot.domain.uniprot.feature;

public enum FeatureCategory {
    MOLECULE_PROCESSING("Molecule processing"),
    REGIONS("Regions"),
    SITES("Sites"),
    AMINO_ACID_MODIFICATIONS("Amino acid modifications"),
    NATURAL_VARIATIONS("Natural variations"),
    EXPERIMENTAL_INFO("Experimental info"),
    SECONDARY_STRUCTURE("Secondary structure");
    private final String name;

    FeatureCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
