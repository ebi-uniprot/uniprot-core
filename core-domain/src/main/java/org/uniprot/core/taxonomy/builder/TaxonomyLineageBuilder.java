package org.uniprot.core.taxonomy.builder;

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

    public TaxonomyLineageBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public TaxonomyLineageBuilder rank(TaxonomyRank rank) {
        this.rank = rank;
        return this;
    }

    public TaxonomyLineageBuilder hidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    @Override
    public TaxonomyLineage build() {
        return new TaxonomyLineageImpl(taxonId, scientificName, commonName, synonyms, rank, hidden);
    }

    @Override
    public TaxonomyLineageBuilder from(TaxonomyLineage instance) {
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
    protected TaxonomyLineageBuilder getThis() {
        return this;
    }
}
