package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class SubcellularLocationValueBuilder extends AbstractEvidencedValueBuilder<SubcellularLocationValueBuilder, SubcellularLocationValue> {
    private String id;

    private SubcellularLocationValueBuilder() {
    }

    public SubcellularLocationValueBuilder(String id, String value, List<Evidence> evidences) {
        this.id = id;
        this.value = value;
        this.evidences = nonNullList(evidences);
    }

    @Override
    public SubcellularLocationValue build() {
        return new SubcellularLocationImpl.SubcellularLocationValueImpl(id, value, evidences);
    }

    @Override
    protected SubcellularLocationValueBuilder getThis() {
        return this;
    }

    @Override
    public SubcellularLocationValueBuilder from(SubcellularLocationValue instance) {
        return super.from(instance)
                .id(instance.getId());
    }

    public SubcellularLocationValueBuilder id(String id) {
        this.id = id;
        return this;
    }
}