package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.SequenceFeatureLocation;

import javax.annotation.Nonnull;

public class SequenceFeatureLocationBuilder implements Builder<SequenceFeatureLocation> {

    private int start;
    private int end;
    private String alignment;

    @Nonnull
    @Override
    public SequenceFeatureLocation build() {
        return new SequenceFeatureLocationImpl(start,end,alignment);
    }

    public @Nonnull SequenceFeatureLocationBuilder range(int start, int end) {
        this.start = start;
        this.end = end;
        return this;
    }

    public @Nonnull SequenceFeatureLocationBuilder start(int start) {
        this.start = start;
        return this;
    }

    public @Nonnull SequenceFeatureLocationBuilder end(int end) {
        this.end = end;
        return this;
    }

    public @Nonnull SequenceFeatureLocationBuilder alignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    public static @Nonnull SequenceFeatureLocationBuilder from(@Nonnull SequenceFeatureLocation instance) {
        return new SequenceFeatureLocationBuilder()
                .start(instance.getStart())
                .end(instance.getEnd())
                .alignment(instance.getAlignment());
    }
}
