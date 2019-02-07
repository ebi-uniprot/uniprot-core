package uk.ac.ebi.uniprot.domain.uniprot.feature.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class AlternativeSequenceBuilder implements Builder<AlternativeSequenceBuilder, AlternativeSequence> {
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
        this.alternativeSequences = nonNullList(alternativeSequences);
        return this;
    }

    public AlternativeSequenceBuilder alternative(String alternative) {
        nonNullAdd(alternative, this.alternativeSequences);
        return this;
    }
}
