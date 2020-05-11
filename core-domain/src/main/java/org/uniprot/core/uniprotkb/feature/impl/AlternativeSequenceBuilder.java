package org.uniprot.core.uniprotkb.feature.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class AlternativeSequenceBuilder implements Builder<AlternativeSequence> {
    private String originalSequence;
    private List<String> alternativeSequences = new ArrayList<>();

    @Override
    public @Nonnull AlternativeSequence build() {
        return new AlternativeSequenceImpl(originalSequence, alternativeSequences);
    }

    public static @Nonnull AlternativeSequenceBuilder from(@Nonnull AlternativeSequence instance) {
        return new AlternativeSequenceBuilder()
                .original(instance.getOriginalSequence())
                .alternativeSequencesSet(instance.getAlternativeSequences());
    }

    public @Nonnull AlternativeSequenceBuilder original(String originalSequence) {
        this.originalSequence = originalSequence;
        return this;
    }

    public @Nonnull AlternativeSequenceBuilder alternativeSequencesSet(
            List<String> alternativeSequences) {
        this.alternativeSequences = modifiableList(alternativeSequences);
        return this;
    }

    public @Nonnull AlternativeSequenceBuilder alternativeSequencesAdd(String alternative) {
        addOrIgnoreNull(alternative, this.alternativeSequences);
        return this;
    }
}
