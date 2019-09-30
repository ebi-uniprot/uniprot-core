package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.comment.impl.MassSpectrometryRangeImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class MassSpectrometryRangeBuilder
        implements Builder<MassSpectrometryRangeBuilder, MassSpectrometryRange> {
    private Range range;
    private String isoformId;

    public MassSpectrometryRangeBuilder range(Range range) {
        this.range = range;
        return this;
    }

    public MassSpectrometryRangeBuilder isoformId(String isoformId) {
        this.isoformId = isoformId;
        return this;
    }

    public MassSpectrometryRangeImpl build() {
        return new MassSpectrometryRangeImpl(range, isoformId);
    }

    @Override
    public MassSpectrometryRangeBuilder from(MassSpectrometryRange instance) {
        return this.isoformId(instance.getIsoformId()).range(instance.getRange());
    }
}
