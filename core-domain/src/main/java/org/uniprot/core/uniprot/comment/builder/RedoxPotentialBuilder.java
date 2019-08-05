package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullList;

import java.util.List;

import org.uniprot.core.uniprot.comment.RedoxPotential;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class RedoxPotentialBuilder extends AbstractFreeTextBuilder<RedoxPotentialBuilder, RedoxPotential> {
    public RedoxPotentialBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = nonNullList(evidencedValues);
    }

    @Override
    protected RedoxPotentialBuilder getThis() {
        return this;
    }

    @Override
    protected RedoxPotential createConcreteInstance() {
        return new BPCPCommentImpl.RedoxPotentialImpl(evidencedValues);
    }
}
