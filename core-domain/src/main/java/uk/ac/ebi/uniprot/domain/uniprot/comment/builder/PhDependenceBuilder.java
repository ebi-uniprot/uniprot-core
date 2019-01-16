package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.PhDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.List;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class PhDependenceBuilder extends AbstractFreeTextBuilder<PhDependenceBuilder, PhDependence> {
    public PhDependenceBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = evidencedValues;
    }

    @Override
    protected PhDependenceBuilder createBuilderInstance(List<EvidencedValue> evidencedValues) {
        return new PhDependenceBuilder(evidencedValues);
    }

    @Override
    protected PhDependence createConcreteInstance() {
        return new BPCPCommentImpl.PhDependenceImpl(evidencedValues);
    }
}
