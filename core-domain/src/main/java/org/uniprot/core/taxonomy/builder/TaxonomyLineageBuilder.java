package org.uniprot.core.taxonomy.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;
import org.uniprot.core.uniprot.taxonomy.builder.AbstractOrganismNameBuilder;

public class TaxonomyLineageBuilder
        extends AbstractOrganismNameBuilder<TaxonomyLineageBuilder, TaxonomyLineage>
        implements Builder<TaxonomyLineageBuilder, TaxonomyLineage> {

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

    @Override
    public @Nonnull TaxonomyLineageBuilder from(@Nonnull TaxonomyLineage instance) {
        if (instance != null) {
            this.taxonId(instance.getTaxonId());
            this.scientificName(instance.getScientificName());
            this.commonName(instance.getCommonName());
            this.synonyms(instance.getSynonyms());
            this.rank(instance.getRank());
            this.hidden(instance.isHidden());
        }
        return this;
    }

    @Override
    protected @Nonnull TaxonomyLineageBuilder getThis() {
        return this;
    }
}
