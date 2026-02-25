package org.uniprot.core.proteome.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RelatedProteome;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

import javax.annotation.Nonnull;

public class RelatedProteomeBuilder implements Builder<RelatedProteome> {
    private ProteomeId proteomeId;
    private Float similarity;
    private Taxonomy taxonomy;

    public @Nonnull RelatedProteomeBuilder proteomeId(ProteomeId proteomeId) {
        this.proteomeId = proteomeId;
        return this;
    }

    public @Nonnull RelatedProteomeBuilder similarity(Float similarity) {
        this.similarity = similarity;
        return this;
    }
    public @Nonnull RelatedProteomeBuilder taxonomy(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
        return this;
    }


    @Nonnull
    @Override
    public RelatedProteome build() {
        return new RelatedProteomeImpl(proteomeId, similarity, taxonomy);
    }

    public static @Nonnull RelatedProteomeBuilder from(@Nonnull RelatedProteome instance) {
        RelatedProteomeBuilder builder = new RelatedProteomeBuilder();
        return builder.proteomeId(instance.getId()).similarity(instance.getSimilarity())
                .taxonomy(instance.getTaxId());
    }
}
