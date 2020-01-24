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
    protected @Nonnull SubcellularLocationValueBuilder getThis() {
        return this;
    }

    public static @Nonnull SubcellularLocationValueBuilder from(
            @Nonnull SubcellularLocationValue instance) {
        SubcellularLocationValueBuilder builder = new SubcellularLocationValueBuilder();
        AbstractEvidencedValueBuilder.init(builder, instance);
        return builder.id(instance.getId());
    }

    public @Nonnull SubcellularLocationValueBuilder id(String id) {
        this.id = id;
        return this;
    }
}
