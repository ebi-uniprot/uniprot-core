package org.uniprot.core.taxonomy.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;

public class TaxonomyLineageBuilder implements Builder<TaxonomyLineageBuilder, TaxonomyLineage> {

    private long taxonId;

    private String scientificName;

    private TaxonomyRank rank; //=TaxonomyRank.NO_RANK;

    private boolean hidden;

    public TaxonomyLineageBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public TaxonomyLineageBuilder scientificName(String scientificName) {
        this.scientificName = scientificName;
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
        return new TaxonomyLineageImpl(taxonId,scientificName,rank,hidden);
    }

    @Override
    public TaxonomyLineageBuilder from(TaxonomyLineage instance) {
        if(instance != null) {
            this.taxonId(instance.getTaxonId());
            this.scientificName(instance.getScientificName());
            this.rank(instance.getRank());
            this.hidden(instance.isHidden());
        }
        return this;
    }

}
