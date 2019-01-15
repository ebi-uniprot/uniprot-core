package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.SubcellularLocationImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class SubcellularLocationValueBuilder extends AbstractEvidencedValueBuilder<SubcellularLocationValueBuilder, SubcellularLocationValue> {
    @Override
    protected SubcellularLocationValueBuilder createBuilderInstance() {
        return new SubcellularLocationValueBuilder();
    }

    @Override
    protected SubcellularLocationValue createConcreteInstance() {
        return new SubcellularLocationImpl.SubcellularLocationValueImpl(value, evidences);
    }
}