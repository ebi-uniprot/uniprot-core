package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.comment.impl.SubcellularLocationImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

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
        this.evidences = modifiableList(evidences);
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