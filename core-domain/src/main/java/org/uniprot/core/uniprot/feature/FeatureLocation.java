package org.uniprot.core.uniprot.feature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;

/**
 * @author jluo
 * @date: 15 Nov 2019
 */
public class FeatureLocation extends Range {
    /** */
    private static final long serialVersionUID = 5226774801010994052L;

    private final String sequence;

    FeatureLocation() {
        super();
        sequence = null;
    }

    public FeatureLocation(@Nullable Integer start, @Nullable Integer end) {
        super(start, end);
        sequence = null;
    }

    public FeatureLocation(
            @Nullable Integer start,
            @Nullable Integer end,
            @Nonnull PositionModifier sModifier,
            @Nonnull PositionModifier eModifier) {
        super(start, end, sModifier, eModifier);
        this.sequence = null;
    }

    public FeatureLocation(
            @Nullable String sequence,
            @Nullable Integer start,
            @Nullable Integer end,
            @Nonnull PositionModifier sModifier,
            @Nonnull PositionModifier eModifier) {
        super(start, end, sModifier, eModifier);
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }
}
