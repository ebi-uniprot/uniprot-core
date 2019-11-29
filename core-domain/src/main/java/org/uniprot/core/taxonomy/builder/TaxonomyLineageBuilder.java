package org.uniprot.core.taxonomy.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;

import javax.annotation.Nonnull;

public class TaxonomyLineageBuilder implements Builder<TaxonomyLineageBuilder, TaxonomyLineage> {

    private long taxonId;

    private String scientificName;

    private TaxonomyRank rank; // =TaxonomyRank.NO_RANK;

    private boolean hidden;

    public @Nonnull TaxonomyLineageBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public @Nonnull TaxonomyLineageBuilder scientificName(String scientificName) {
        this.scientificName = scientificName;
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
        return new TaxonomyLineageImpl(taxonId, scientificName, rank, hidden);
    }

    @Override
    public @Nonnull TaxonomyLineageBuilder from(@Nonnull TaxonomyLineage instance) {
        if (instance != null) {
            this.taxonId(instance.getTaxonId());
            this.scientificName(instance.getScientificName());
            this.rank(instance.getRank());
            this.hidden(instance.isHidden());
        }
        return this;
    }
}
