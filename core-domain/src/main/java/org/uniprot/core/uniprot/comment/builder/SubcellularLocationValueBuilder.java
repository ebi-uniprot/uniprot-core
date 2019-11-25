package org.uniprot.core.uniprot.comment.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.comment.impl.SubcellularLocationImpl;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class SubcellularLocationValueBuilder
        extends AbstractEvidencedValueBuilder<
                SubcellularLocationValueBuilder, SubcellularLocationValue> {
    private String id;

    public SubcellularLocationValueBuilder() {}

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
