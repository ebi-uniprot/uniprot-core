package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.TemperatureDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class TemperatureDependenceBuilder extends AbstractFreeTextBuilder<TemperatureDependenceBuilder, TemperatureDependence> {
    public TemperatureDependenceBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = nonNullList(evidencedValues);
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
