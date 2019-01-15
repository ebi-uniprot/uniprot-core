package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.MassSpectrometryRangeImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class MassSpectrometryRangeBuilder implements Builder2<MassSpectrometryRangeBuilder, MassSpectrometryRange> {
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
        return new MassSpectrometryRangeBuilder()
                .isoformId(instance.getIsoformId())
                .range(instance.getRange());
    }
}
