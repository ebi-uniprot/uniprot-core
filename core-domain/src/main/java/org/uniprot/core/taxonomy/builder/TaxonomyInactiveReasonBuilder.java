package org.uniprot.core.taxonomy.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyInactiveReason;
import org.uniprot.core.taxonomy.TaxonomyInactiveReasonType;
import org.uniprot.core.taxonomy.impl.TaxonomyInactiveReasonImpl;

import javax.annotation.Nonnull;

/** @author lgonzales */
public class TaxonomyInactiveReasonBuilder
        implements Builder<TaxonomyInactiveReasonBuilder, TaxonomyInactiveReason> {

    private TaxonomyInactiveReasonType inactiveReasonType;

    private long mergedTo;

    public @Nonnull TaxonomyInactiveReasonBuilder inactiveReasonType(
            TaxonomyInactiveReasonType inactiveReasonType) {
        this.inactiveReasonType = inactiveReasonType;
        return this;
    }

    public @Nonnull TaxonomyInactiveReasonBuilder mergedTo(long mergedTo) {
        this.mergedTo = mergedTo;
        return this;
    }

    @Override
    public @Nonnull TaxonomyInactiveReason build() {
        return new TaxonomyInactiveReasonImpl(inactiveReasonType, mergedTo);
    }

    @Override
    public @Nonnull TaxonomyInactiveReasonBuilder from(@Nonnull TaxonomyInactiveReason instance) {
        this.inactiveReasonType = instance.getInactiveReasonType();
        this.mergedTo = instance.getMergedTo();
        return this;
    }
}
