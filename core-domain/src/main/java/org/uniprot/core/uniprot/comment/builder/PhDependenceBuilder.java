package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.uniprot.comment.PhDependence;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class PhDependenceBuilder extends AbstractFreeTextBuilder<PhDependenceBuilder, PhDependence> {
    public PhDependenceBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
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
