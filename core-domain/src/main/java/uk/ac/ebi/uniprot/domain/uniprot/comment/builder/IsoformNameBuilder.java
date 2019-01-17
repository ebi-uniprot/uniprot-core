package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class IsoformNameBuilder extends AbstractEvidencedValueBuilder<IsoformNameBuilder, IsoformName> {
    private IsoformNameBuilder() {}

    public IsoformNameBuilder(String value, List<Evidence> evidences) {
        this.value = value;
        this.evidences = evidences;
    }

    @Override
    protected IsoformNameBuilder createBuilderInstance() {
        return new IsoformNameBuilder();
    }

    @Override
    protected IsoformName createConcreteInstance() {
        return new APIsoformImpl.IsoformNameImpl(value, evidences);
    }
}