package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum FeatureCategory implements EnumDisplay {
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

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull FeatureCategory typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, FeatureCategory.class);
    }
}
