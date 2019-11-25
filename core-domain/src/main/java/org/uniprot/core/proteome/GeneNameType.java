package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum GeneNameType implements EnumDisplay<GeneNameType> {
    MOD("MOD"),
    ENSEMBL("Ensembl"),
    OLN("OLN"),
    ORF("ORF"),
    GENE_NAME("Gene name"),
    MISSING("Missing");

    private final String name;

    GeneNameType(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return getName();
    }

    public static @Nonnull GeneNameType fromValue(String type) {
        for (GeneNameType gnType : GeneNameType.values()) {
            if (gnType.getName().equalsIgnoreCase(type)) return gnType;
        }
        return GeneNameType.MISSING;
    }
}
