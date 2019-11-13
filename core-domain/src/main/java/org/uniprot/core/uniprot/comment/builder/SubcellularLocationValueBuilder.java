package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.comment.impl.SubcellularLocationImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

import javax.annotation.Nonnull;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class SubcellularLocationValueBuilder
        extends AbstractEvidencedValueBuilder<
                SubcellularLocationValueBuilder, SubcellularLocationValue> {
    private String id;

    @Override
    public @Nonnull SubcellularLocationValue build() {
        return new SubcellularLocationImpl.SubcellularLocationValueImpl(id, value, evidences);
    }

    @Override
    protected SubcellularLocationValueBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull SubcellularLocationValueBuilder from(@Nonnull SubcellularLocationValue instance) {
        return super.from(instance).id(instance.getId());
    }

    public @Nonnull SubcellularLocationValueBuilder id(String id) {
        this.id = id;
        return this;
    }
}
