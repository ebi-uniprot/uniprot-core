package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.RedoxPotential;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class RedoxPotentialBuilder
        extends AbstractFreeTextBuilder<RedoxPotentialBuilder, RedoxPotential> {
    public RedoxPotentialBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
    }

    @Override
    protected @Nonnull RedoxPotentialBuilder getThis() {
        return this;
    }

    @Override
    protected @Nonnull RedoxPotential createConcreteInstance() {
        return new BPCPCommentImpl.RedoxPotentialImpl(evidencedValues);
    }
}
