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
public class RedoxPotentialBuilder extends AbstractFreeTextBuilder<RedoxPotential> {
    public RedoxPotentialBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
    }

    @Override
    protected @Nonnull RedoxPotential createConcreteInstance() {
        return new BPCPCommentImpl.RedoxPotentialImpl(evidencedValues);
    }

    public static @Nonnull RedoxPotentialBuilder from(@Nonnull RedoxPotential instance) {
        return new RedoxPotentialBuilder(instance.getTexts());
    }
}
