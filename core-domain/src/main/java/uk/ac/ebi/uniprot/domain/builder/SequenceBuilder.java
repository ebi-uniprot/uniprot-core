package uk.ac.ebi.uniprot.domain.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.impl.SequenceImpl;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class SequenceBuilder implements Builder2<SequenceBuilder, Sequence> {
    private String sequence;

    public SequenceBuilder(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public Sequence build() {
        return new SequenceImpl(sequence);
    }

    @Override
    public SequenceBuilder from(Sequence instance) {
        this.sequence = instance.getValue();
        return this;
    }
}
