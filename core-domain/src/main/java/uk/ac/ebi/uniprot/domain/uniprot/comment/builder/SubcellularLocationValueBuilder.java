package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class SubcellularLocationValueBuilder extends AbstractEvidencedValueBuilder<SubcellularLocationValueBuilder, SubcellularLocationValue> {
    private SubcellularLocationValueBuilder() {}

    public SubcellularLocationValueBuilder(String value, List<Evidence> evidences) {
        this.value = value;
        this.evidences = nonNullList(evidences);
    }

    @Override
    public SubcellularLocationValue build() {
        return new SubcellularLocationImpl.SubcellularLocationValueImpl(value, evidences);
    }

    @Override
    protected SubcellularLocationValueBuilder getThis() {
        return this;
    }
}