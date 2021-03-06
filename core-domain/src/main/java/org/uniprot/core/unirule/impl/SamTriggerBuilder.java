package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.nullThrowIllegalArgument;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Range;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.SamTriggerType;

public class SamTriggerBuilder implements Builder<SamTrigger> {
    private SamTriggerType samTriggerType;
    private Range expectedHits;

    public @Nonnull SamTriggerBuilder samTriggerType(SamTriggerType samTriggerType) {
        this.samTriggerType = samTriggerType;
        return this;
    }

    public @Nonnull SamTriggerBuilder expectedHits(Range expectedHits) {
        this.expectedHits = expectedHits;
        return this;
    }

    @Nonnull
    @Override
    public SamTrigger build() {
        return new SamTriggerImpl(samTriggerType, expectedHits);
    }

    public static @Nonnull SamTriggerBuilder from(@Nonnull SamTrigger instance) {
        nullThrowIllegalArgument(instance);
        SamTriggerBuilder builder = new SamTriggerBuilder();
        builder.samTriggerType(instance.getSamTriggerType())
                .expectedHits(instance.getExpectedHits());
        return builder;
    }
}
