package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyLineage;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyLineageImpl;

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
        this.taxonId(instance.getTaxonId());
        this.scientificName(instance.getScientificName());
        this.rank(instance.getRank());
        this.hidden(instance.isHidden());
        return this;
    }

}
