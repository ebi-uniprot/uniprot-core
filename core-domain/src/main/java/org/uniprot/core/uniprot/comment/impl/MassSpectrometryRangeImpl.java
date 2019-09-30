package org.uniprot.core.uniprot.comment.impl;

import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;

public class MassSpectrometryRangeImpl implements MassSpectrometryRange {
    private static final long serialVersionUID = -8822968683072940404L;
    private Range range;
    private String isoformId;

    private MassSpectrometryRangeImpl() {
        this.isoformId = "";
    }

    public MassSpectrometryRangeImpl(Integer start, Integer end, String isoformId) {
        this(new Range(start, end), isoformId);
    }

    public MassSpectrometryRangeImpl(Range range, String isoformId) {
        this.range = range;
        if (isoformId == null || isoformId.isEmpty()) {
            this.isoformId = "";
        } else this.isoformId = isoformId;
    }

    @Override
    public Range getRange() {
        return range;
    }

    @Override
    public boolean hasIsoformId() {
        return ((isoformId != null) && !isoformId.isEmpty());
    }

    @Override
    public String getIsoformId() {
        return isoformId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MassSpectrometryRangeImpl that = (MassSpectrometryRangeImpl) o;
        return Objects.equals(range, that.range) && Objects.equals(isoformId, that.isoformId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, isoformId);
    }
}
