package org.uniprot.core.uniprotkb;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum GeneEncodingType implements EnumDisplay {
    UNKNOWN("unknown"),
    HYDROGENOSOME("Hydrogenosome"),
    MITOCHONDRION("Mitochondrion"),
    NUCLEOMORPH("Nucleomorph"),
    PLASMID("Plasmid"),
    PLASTID("Plastid"),
    APICOPLAST("Apicoplast"),
    CHLOROPLAST("Chloroplast"),
    CYANELLE("Cyanelle"),
    NON_PHOTOSYNTHETIC_PLASTID("Non-photosynthetic plastid"),
    ORGANELLAR_CHROMATOPHORE("Organellar chromatophore");

    private String name;

    GeneEncodingType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return this.name;
    }

    public static @Nonnull GeneEncodingType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, GeneEncodingType.UNKNOWN);
    }
}
