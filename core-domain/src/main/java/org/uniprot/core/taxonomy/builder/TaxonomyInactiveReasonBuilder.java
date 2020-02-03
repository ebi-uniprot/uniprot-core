package org.uniprot.core.taxonomy.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyInactiveReason;
import org.uniprot.core.taxonomy.TaxonomyInactiveReasonType;
import org.uniprot.core.taxonomy.impl.TaxonomyInactiveReasonImpl;

/** @author lgonzales */
public class TaxonomyInactiveReasonBuilder implements Builder<TaxonomyInactiveReason> {

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

    public static @Nonnull TaxonomyInactiveReasonBuilder from(
            @Nonnull TaxonomyInactiveReason instance) {
        return new TaxonomyInactiveReasonBuilder()
                .inactiveReasonType(instance.getInactiveReasonType())
                .mergedTo(instance.getMergedTo());
    }
}
