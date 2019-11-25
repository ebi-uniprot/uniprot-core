package org.uniprot.core.proteome;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum ProteomeXReferenceType implements DatabaseType, EnumDisplay<ProteomeXReferenceType> {
    GENOME_ASSEMBLY("GenomeAssembly"),
    GENOME_ANNOTATION("GenomeAnnotation"),
    GENOME_ACCESSION("GenomeAccession"),
    ASSEMBLY_ID("AssemblyId"),
    BIOSAMPLE("Biosample"),
    UNKNOWN("Unknown");

    private final String name;

    ProteomeXReferenceType(String name) {
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

    public static @Nonnull ProteomeXReferenceType fromValue(String type) {
        for (ProteomeXReferenceType gnType : ProteomeXReferenceType.values()) {
            if (gnType.getName().equalsIgnoreCase(type)) return gnType;
        }
        return ProteomeXReferenceType.UNKNOWN;
    }
}
