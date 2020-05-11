package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum ProteomeDatabase implements Database, EnumDisplay {
    GENOME_ASSEMBLY("GenomeAssembly"),
    GENOME_ANNOTATION("GenomeAnnotation"),
    GENOME_ACCESSION("GenomeAccession"),
    ASSEMBLY_ID("AssemblyId"),
    BIOSAMPLE("Biosample");

    private final String name;

    ProteomeDatabase(String name) {
        this.name = name;
    }

    @Override
    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull ProteomeDatabase typeOf(@Nonnull String name) {
        return EnumDisplay.typeOf(name, ProteomeDatabase.class);
    }
}
