package uk.ac.ebi.uniprot.domain.uniprot.feature.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class AlternativeSequenceBuilder implements Builder2<AlternativeSequenceBuilder, AlternativeSequence> {
    private String originalSequence;
    private List<String> alternativeSequences = new ArrayList<>();

    @Override
    public AlternativeSequence build() {
        return new AlternativeSequenceImpl(originalSequence, alternativeSequences);
    }

    @Override
    public AlternativeSequenceBuilder from(AlternativeSequence instance) {
        return new AlternativeSequenceBuilder()
                .original(instance.getOriginalSequence())
                .alternatives(instance.getAlternativeSequences());
    }

    public AlternativeSequenceBuilder original(String originalSequence) {
        this.originalSequence = originalSequence;
        return this;
    }

    public AlternativeSequenceBuilder alternatives(List<String> alternativeSequences) {
        nonNullAddAll(alternativeSequences, this.alternativeSequences);
        return this;
    }

    public AlternativeSequenceBuilder alternative(String alternative) {
        nonNullAdd(alternative, this.alternativeSequences);
        return this;
    }
}
