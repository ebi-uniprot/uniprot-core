package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.RedoxPotential;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

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
