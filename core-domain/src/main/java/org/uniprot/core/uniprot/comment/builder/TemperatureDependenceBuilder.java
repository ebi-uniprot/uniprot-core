package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.uniprot.comment.TemperatureDependence;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class TemperatureDependenceBuilder
        extends AbstractFreeTextBuilder<TemperatureDependenceBuilder, TemperatureDependence> {
    public TemperatureDependenceBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
    }

    @Override
    protected TemperatureDependenceBuilder getThis() {
        return this;
    }

    @Override
    protected TemperatureDependence createConcreteInstance() {
        return new BPCPCommentImpl.TemperatureDependenceImpl(evidencedValues);
    }
}
