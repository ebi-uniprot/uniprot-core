package org.uniprot.core.taxonomy.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;
import org.uniprot.core.uniprot.taxonomy.builder.AbstractOrganismNameBuilder;

public class TaxonomyLineageBuilder
        extends AbstractOrganismNameBuilder<TaxonomyLineageBuilder, TaxonomyLineage>
        implements Builder<TaxonomyLineage> {

    private long taxonId;

    private TaxonomyRank rank; // =TaxonomyRank.NO_RANK;

    private boolean hidden;

    public @Nonnull TaxonomyLineageBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public @Nonnull TaxonomyLineageBuilder rank(TaxonomyRank rank) {
        this.rank = rank;
        return this;
    }

    public @Nonnull TaxonomyLineageBuilder hidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    @Override
    public @Nonnull TaxonomyLineage build() {
        return new TaxonomyLineageImpl(taxonId, scientificName, commonName, synonyms, rank, hidden);
    }

    public static @Nonnull TaxonomyLineageBuilder from(@Nonnull TaxonomyLineage instance) {
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        AbstractOrganismNameBuilder.init(builder, instance);
        builder.taxonId(instance.getTaxonId());
        builder.scientificName(instance.getScientificName());
        builder.commonName(instance.getCommonName());
        builder.synonyms(instance.getSynonyms());
        builder.rank(instance.getRank());
        builder.hidden(instance.isHidden());
        return builder;
    }

    @Override
    protected @Nonnull TaxonomyLineageBuilder getThis() {
        return this;
    }
}
