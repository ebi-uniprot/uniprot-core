package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryRangeImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class MassSpectrometryRangeBuilder implements Builder<MassSpectrometryRangeBuilder, MassSpectrometryRange> {
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
        return this
                .isoformId(instance.getIsoformId())
                .range(instance.getRange());
    }
}
