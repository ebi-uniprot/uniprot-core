package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

public enum GeneNameType implements EnumDisplay {
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

    public static @Nonnull GeneNameType typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, GeneNameType.MISSING);
    }
}
