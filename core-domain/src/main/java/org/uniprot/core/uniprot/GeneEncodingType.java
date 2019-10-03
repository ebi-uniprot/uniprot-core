package org.uniprot.core.uniprot;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum GeneEncodingType implements EnumDisplay<GeneEncodingType> {
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

    public @Nonnull static GeneEncodingType typeOf(@Nullable String name) {
        for (GeneEncodingType geneEncodingType : GeneEncodingType.values()) {
            if (geneEncodingType.getName().equalsIgnoreCase(name)) {
                return geneEncodingType;
            }
        }
        return GeneEncodingType.UNKNOWN;
    }

    public @Nonnull String getName() {
        return this.name;
    }

    @Override
    public @Nonnull
    String toDisplayName() {
        return name;
    }
}
