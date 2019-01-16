package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;

import java.util.Objects;


public class MassSpectrometryRangeImpl implements MassSpectrometryRange {
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
        } else
            this.isoformId = isoformId;
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
        return Objects.equals(range, that.range) &&
                Objects.equals(isoformId, that.isoformId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, isoformId);
    }
}
