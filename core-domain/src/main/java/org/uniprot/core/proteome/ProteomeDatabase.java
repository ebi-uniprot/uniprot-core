package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum ProteomeDatabase implements Database, EnumDisplay<ProteomeDatabase> {
    GENOME_ASSEMBLY("GenomeAssembly"),
    GENOME_ANNOTATION("GenomeAnnotation"),
    GENOME_ACCESSION("GenomeAccession"),
    ASSEMBLY_ID("AssemblyId"),
    BIOSAMPLE("Biosample"),
    UNKNOWN("Unknown");

    private final String name;

    ProteomeDatabase(String name) {
        this.name = name;
    }

    @Override
    public @Nonnull String getName() {
        return name;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return getName();
    }

    public static @Nonnull ProteomeDatabase fromValue(@Nonnull String type) {
        for (ProteomeDatabase gnType : ProteomeDatabase.values()) {
            if (gnType.getName().equalsIgnoreCase(type)) return gnType;
        }
        return ProteomeDatabase.UNKNOWN;
    }
}
