package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.comment.PhDependence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class PhDependenceBuilder extends AbstractFreeTextBuilder<PhDependence> {
    public PhDependenceBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
    }

    @Override
    protected @Nonnull PhDependence createConcreteInstance() {
        return new BPCPCommentImpl.PhDependenceImpl(evidencedValues);
    }

    public static @Nonnull PhDependenceBuilder from(@Nonnull PhDependence instance) {
        return new PhDependenceBuilder(instance.getTexts());
    }
}
