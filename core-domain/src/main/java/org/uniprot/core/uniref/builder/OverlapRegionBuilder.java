package org.uniprot.core.uniref.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.impl.OverlapRegionImpl;

import javax.annotation.Nonnull;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class OverlapRegionBuilder implements Builder<OverlapRegionBuilder, OverlapRegion> {
    private int start;
    private int end;

    @Override
    public @Nonnull OverlapRegion build() {
        return new OverlapRegionImpl(start, end);
    }

    @Override
    public @Nonnull OverlapRegionBuilder from(@Nonnull OverlapRegion instance) {
        return start(instance.getStart()).end(instance.getEnd());
    }

    public @Nonnull OverlapRegionBuilder start(int start) {
        this.start = start;
        return this;
    }

    public @Nonnull OverlapRegionBuilder end(int end) {
        this.end = end;
        return this;
    }
}
