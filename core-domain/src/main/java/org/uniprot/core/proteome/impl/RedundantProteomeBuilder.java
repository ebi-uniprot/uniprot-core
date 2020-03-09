package org.uniprot.core.proteome.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RedundantProteome;

public class RedundantProteomeBuilder implements Builder<RedundantProteome> {
    private ProteomeId id;
    private Float similarity;

    public @Nonnull RedundantProteomeBuilder proteomeId(ProteomeId id) {
        this.id = id;
        return this;
    }

    public @Nonnull RedundantProteomeBuilder proteomeId(String id) {
        this.id = new ProteomeIdBuilder(id).build();
        return this;
    }

    public @Nonnull RedundantProteomeBuilder similarity(Float similarity) {
        this.similarity = similarity;
        return this;
    }

    @Override
    public @Nonnull RedundantProteome build() {
        return new RedundantProteomeImpl(id, similarity);
    }

    public static @Nonnull RedundantProteomeBuilder from(@Nonnull RedundantProteome instance) {
        return new RedundantProteomeBuilder()
                .proteomeId(instance.getId())
                .similarity(instance.getSimilarity());
    }
}
