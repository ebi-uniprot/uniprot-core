package org.uniprot.core.proteome.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.GenomeAssembly;
import org.uniprot.core.proteome.GenomeAssemblyLevel;
import org.uniprot.core.proteome.GenomeAssemblySource;

import javax.annotation.Nonnull;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
public class GenomeAssemblyBuilder implements Builder<GenomeAssembly> {

    private String assemblyId;
    private String genomeAssemblyUrl;
    private GenomeAssemblyLevel level;
    private GenomeAssemblySource source;

    public @Nonnull GenomeAssemblyBuilder assemblyId(String assemblyId) {
        this.assemblyId = assemblyId;
        return this;
    }

    public @Nonnull GenomeAssemblyBuilder genomeAssemblyUrl(String genomeAssemblyUrl) {
        this.genomeAssemblyUrl = genomeAssemblyUrl;
        return this;
    }

    public @Nonnull GenomeAssemblyBuilder level(GenomeAssemblyLevel level) {
        this.level = level;
        return this;
    }

    public @Nonnull GenomeAssemblyBuilder source(GenomeAssemblySource source) {
        this.source = source;
        return this;
    }

    @Nonnull
    @Override
    public GenomeAssembly build() {
        return new GenomeAssemblyImpl(assemblyId, genomeAssemblyUrl, level, source);
    }

    public static @Nonnull GenomeAssemblyBuilder from(@Nonnull GenomeAssembly instance) {
        return new GenomeAssemblyBuilder()
                .assemblyId(instance.getAssemblyId())
                .genomeAssemblyUrl(instance.getGenomeAssemblyUrl())
                .level(instance.getLevel())
                .source(instance.getSource());
    }
}
