package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.APIsoformImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class IsoformNameBuilder  extends AbstractEvidencedValueBuilder<IsoformNameBuilder, IsoformName> {
    @Override
    protected IsoformNameBuilder createBuilderInstance() {
        return new IsoformNameBuilder();
    }

    @Override
    protected IsoformName createConcreteInstance() {
        return new APIsoformImpl.IsoformNameImpl(value, evidences);
    }
}