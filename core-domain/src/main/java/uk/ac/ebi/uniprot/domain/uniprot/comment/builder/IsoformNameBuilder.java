package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class IsoformNameBuilder extends AbstractEvidencedValueBuilder<IsoformNameBuilder, IsoformName> {
    @Override
    protected IsoformNameBuilder createBuilderInstance() {
        return new IsoformNameBuilder();
    }

    @Override
    protected IsoformName createConcreteInstance() {
        return new APIsoformImpl.IsoformNameImpl(value, evidences);
    }
}