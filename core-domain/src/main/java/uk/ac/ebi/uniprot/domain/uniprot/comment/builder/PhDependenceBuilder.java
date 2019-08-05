package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.PhDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class PhDependenceBuilder extends AbstractFreeTextBuilder<PhDependenceBuilder, PhDependence> {
    public PhDependenceBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = nonNullList(evidencedValues);
    }

    @Override
    protected PhDependenceBuilder getThis() {
        return this;
    }

    @Override
    protected PhDependence createConcreteInstance() {
        return new BPCPCommentImpl.PhDependenceImpl(evidencedValues);
    }
}
