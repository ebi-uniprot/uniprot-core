package uk.ac.ebi.uniprot.domain.taxonomy;

import uk.ac.ebi.uniprot.cv.common.EnumDisplay;

public enum TaxonomyRank implements EnumDisplay<TaxonomyRank> {
    FORMA("forma"),
    VARIETAS("varietas"),
    SUBSPECIES("subspecies"),
    SPECIES("species"),
    SPECIES_SUBGROUP("species subgroup"),
    SPECIES_GROUP("species group"),
    SUBGENUS("subgenus"),
    GENUS("genus"),
    SUBTRIBE("subtribe"),
    TRIBE("tribe"),
    SUBFAMILY("subfamily"),
    FAMILY("family"),
    SUPERFAMILY("superfamily"),
    PARVORDER("parvorder"),
    INFRAORDER("infraorder"),
    SUBORDER("suborder"),
    ORDER("order"),
    SUPERORDER("superorder"),
    SUBCOHORT("subcohort"),
    COHORT("cohort"),
    INFRACLASS("infraclass"),
    SUBCLASS("subclass"),
    CLASS("class"),
    SUPERCLASS("superclass"),
    SUBPHYLUM("subphylum"),
    PHYLUM("phylum"),
    SUPERPHYLUM("superphylum"),
    SUBKINGDOM("subkingdom"),
    KINGDOM("kingdom"),
    SUPERKINGDOM("superkingdom"),
    NO_RANK("no rank");

    private final String name;

    TaxonomyRank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toDisplayName() {
        return name;
    }
}
