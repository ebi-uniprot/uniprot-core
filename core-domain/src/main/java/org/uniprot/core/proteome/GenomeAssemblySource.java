package org.uniprot.core.proteome;

import javax.annotation.Nonnull;

import org.uniprot.core.util.EnumDisplay;

/**
 * @author jluo
 * @date: 15 Apr 2020
 */
public enum GenomeAssemblySource implements EnumDisplay {
    ENA("ENA/EMBL"),
    ENSEMBLFUNGI("EnsemblFungi"),
    ENSEMBLPLANTS("EnsemblPlants"),
    ENSEMBLBACTERIA("EnsemblBacteria"),
    ENSEMBLPROTISTS("EnsemblProtists"),
    ENSEMBLMETAZOA("EnsemblMetazoa"),
    ENSEMBL("Ensembl"),
    REFSEQ("Refseq"),
    WORMBASE("WormBase"),
    VECTORBASE("VectorBase");

    private final String name;

    GenomeAssemblySource(String name) {
        this.name = name;
    }

    public @Nonnull String getName() {
        return name;
    }

    public static @Nonnull GenomeAssemblySource fromValue(@Nonnull String sourceValue) {
        for (GenomeAssemblySource source : GenomeAssemblySource.values()) {
            if (source.getDisplayName().equalsIgnoreCase(sourceValue)) return source;
        }
        throw new IllegalArgumentException(
                "The GenomeAssemblySource with " + sourceValue + " doesn't exist");
    }
}
