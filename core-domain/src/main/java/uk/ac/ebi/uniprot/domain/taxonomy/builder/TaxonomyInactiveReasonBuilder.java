package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyInactiveReason;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyInactiveReasonType;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyInactiveReasonImpl;

/**
 *
 * @author lgonzales
 */
public class TaxonomyInactiveReasonBuilder implements Builder<TaxonomyInactiveReasonBuilder, TaxonomyInactiveReason> {

    private TaxonomyInactiveReasonType inactiveReasonType;

    private long mergedTo;

    public TaxonomyInactiveReasonBuilder inactiveReasonType(TaxonomyInactiveReasonType inactiveReasonType){
        this.inactiveReasonType = inactiveReasonType;
        return this;
    }

    public TaxonomyInactiveReasonBuilder mergedTo(long mergedTo){
        this.mergedTo = mergedTo;
        return this;
    }

    @Override
    public TaxonomyInactiveReason build() {
        return new TaxonomyInactiveReasonImpl(inactiveReasonType,mergedTo);
    }

    @Override
    public TaxonomyInactiveReasonBuilder from(TaxonomyInactiveReason instance) {
        this.inactiveReasonType = instance.getInactiveReasonType();
        this.mergedTo = instance.getMergedTo();
        return this;
    }
}
