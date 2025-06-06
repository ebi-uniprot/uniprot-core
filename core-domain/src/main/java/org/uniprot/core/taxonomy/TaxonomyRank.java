package org.uniprot.core.taxonomy;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum TaxonomyRank implements EnumDisplay {

    STRAIN("strain"),
    SEROTYPE("serotype"),
    SERIES("series"),
    MORPH("morph"),
    BIOTYPE("biotype"),
    CLADE("clade"),
    GENOTYPE("genotype"),
    ISOLATE("isolate"),
    FORMA_SPECIALIS("forma specialis"),
    PATHOGROUP("pathogroup"),
    SEROGROUP("serogroup"),
    SECTION("section"),
    SUBSECTION("subsection"),
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
    REALM("realm"),
    DOMAIN("domain"),
    SUBVARIETY("subvariety"),
    NO_RANK("no rank");

    private final String name;

    TaxonomyRank(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull TaxonomyRank typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, TaxonomyRank.class);
    }
}
