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
    private Integer molWeight;

    public SequenceBuilder(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public Sequence build() {
        SequenceImpl sequenceImpl = new SequenceImpl(sequence);
        if(molWeight != null){
            sequenceImpl.setMolWeight(this.molWeight);
        }
        return sequenceImpl;
    }

    public SequenceBuilder molWeight(int molWeight) {
        this.molWeight = molWeight;
        return this;
    }

    @Override
    public SequenceBuilder from(Sequence instance) {
        this.sequence = instance.getValue();
        return this;
    }
}
