package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.uniprotkb.comment.RedoxPotential;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.List;

import javax.annotation.Nonnull;

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
