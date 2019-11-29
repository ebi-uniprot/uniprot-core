package org.uniprot.core.uniprot.feature.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.impl.AlternativeSequenceImpl;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class AlternativeSequenceBuilder
        implements Builder<AlternativeSequenceBuilder, AlternativeSequence> {
    private String originalSequence;
    private List<String> alternativeSequences = new ArrayList<>();

    @Override
    public @Nonnull AlternativeSequence build() {
        return new AlternativeSequenceImpl(originalSequence, alternativeSequences);
    }

    @Override
    public @Nonnull AlternativeSequenceBuilder from(@Nonnull AlternativeSequence instance) {
        return new AlternativeSequenceBuilder()
                .original(instance.getOriginalSequence())
                .alternatives(instance.getAlternativeSequences());
    }

    public @Nonnull AlternativeSequenceBuilder original(String originalSequence) {
        this.originalSequence = originalSequence;
        return this;
    }

    public @Nonnull AlternativeSequenceBuilder alternatives(List<String> alternativeSequences) {
        this.alternativeSequences = modifiableList(alternativeSequences);
        return this;
    }

    public @Nonnull AlternativeSequenceBuilder alternative(String alternative) {
        addOrIgnoreNull(alternative, this.alternativeSequences);
        return this;
    }
}
