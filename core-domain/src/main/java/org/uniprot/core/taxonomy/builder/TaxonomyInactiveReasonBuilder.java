package org.uniprot.core.taxonomy.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyInactiveReason;
import org.uniprot.core.taxonomy.TaxonomyInactiveReasonType;
import org.uniprot.core.taxonomy.impl.TaxonomyInactiveReasonImpl;

/** @author lgonzales */
public class TaxonomyInactiveReasonBuilder
        implements Builder<TaxonomyInactiveReasonBuilder, TaxonomyInactiveReason> {

    private TaxonomyInactiveReasonType inactiveReasonType;

    private long mergedTo;

    public TaxonomyInactiveReasonBuilder inactiveReasonType(
            TaxonomyInactiveReasonType inactiveReasonType) {
        this.inactiveReasonType = inactiveReasonType;
        return this;
    }

    public TaxonomyInactiveReasonBuilder mergedTo(long mergedTo) {
        this.mergedTo = mergedTo;
        return this;
    }

    @Override
    public TaxonomyInactiveReason build() {
        return new TaxonomyInactiveReasonImpl(inactiveReasonType, mergedTo);
    }

    @Override
    public TaxonomyInactiveReasonBuilder from(TaxonomyInactiveReason instance) {
        this.inactiveReasonType = instance.getInactiveReasonType();
        this.mergedTo = instance.getMergedTo();
        return this;
    }
}
