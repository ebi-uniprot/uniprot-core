package org.uniprot.core.uniprot.comment.builder;

import javax.annotation.Nonnull;

import java.util.List;

import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.comment.impl.SubcellularLocationImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.util.Utils;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class SubcellularLocationValueBuilder
        extends AbstractEvidencedValueBuilder<
                SubcellularLocationValueBuilder, SubcellularLocationValue> {
    private String id;

    SubcellularLocationValueBuilder() {}

    public SubcellularLocationValueBuilder(String id, String value, List<Evidence> evidences) {
        this.id = id;
        this.value = value;
        this.evidences = Utils.modifiableList(evidences);
    }

    @Override
    public @Nonnull SubcellularLocationValue build() {
        return new SubcellularLocationImpl.SubcellularLocationValueImpl(id, value, evidences);
    }

    @Override
    protected SubcellularLocationValueBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull SubcellularLocationValueBuilder from(
            @Nonnull SubcellularLocationValue instance) {
        return super.from(instance).id(instance.getId());
    }

    public @Nonnull SubcellularLocationValueBuilder id(String id) {
        this.id = id;
        return this;
    }
}
