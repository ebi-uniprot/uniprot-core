package org.uniprot.core.taxonomy.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;

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

    public static @Nonnull TaxonomyLineageBuilder from(@Nonnull TaxonomyLineage instance) {
        return new TaxonomyLineageBuilder()
            .taxonId(instance.getTaxonId())
            .scientificName(instance.getScientificName())
            .rank(instance.getRank())
            .hidden(instance.isHidden());
    }
}
