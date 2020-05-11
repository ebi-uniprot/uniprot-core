package org.uniprot.core.taxonomy.impl;

import org.uniprot.core.taxonomy.TaxonomyInactiveReason;
import org.uniprot.core.taxonomy.TaxonomyInactiveReasonType;

import java.util.Objects;

/** @author lgonzales */
public class TaxonomyInactiveReasonImpl implements TaxonomyInactiveReason {

    private TaxonomyInactiveReasonType inactiveReasonType;

    private long mergedTo;

    TaxonomyInactiveReasonImpl() {
        this(null, 0);
    }

    TaxonomyInactiveReasonImpl(TaxonomyInactiveReasonType inactiveReasonType, long mergedTo) {
        this.inactiveReasonType = inactiveReasonType;
        this.mergedTo = mergedTo;
    }

    @Override
    public TaxonomyInactiveReasonType getInactiveReasonType() {
        return inactiveReasonType;
    }

    @Override
    public long getMergedTo() {
        return mergedTo;
    }

    @Override
    public boolean hasInactiveReasonType() {
        return inactiveReasonType != null;
    }

    @Override
    public boolean hasMergedTo() {
        return mergedTo > 0;
    }

    @Override
    public String toString() {
        return "TaxonomyInactiveReasonImpl{"
                + "inactiveReasonType="
                + inactiveReasonType
                + ", mergedTo="
                + mergedTo
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxonomyInactiveReasonImpl that = (TaxonomyInactiveReasonImpl) o;
        return getMergedTo() == that.getMergedTo()
                && getInactiveReasonType() == that.getInactiveReasonType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInactiveReasonType(), getMergedTo());
    }
}
