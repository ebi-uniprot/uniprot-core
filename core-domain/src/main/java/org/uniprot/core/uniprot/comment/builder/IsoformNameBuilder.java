package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.uniprot.comment.IsoformName;
import org.uniprot.core.uniprot.comment.impl.APIsoformImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

import javax.annotation.Nonnull;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class IsoformNameBuilder
        extends AbstractEvidencedValueBuilder<IsoformNameBuilder, IsoformName> {
    public IsoformNameBuilder() {}

    public IsoformNameBuilder(String value, List<Evidence> evidences) {
        this.value = value;
        this.evidences = modifiableList(evidences);
    }

    @Override
    public @Nonnull IsoformName build() {
        return new APIsoformImpl.IsoformNameImpl(value, evidences);
    }

    @Override
    protected @Nonnull IsoformNameBuilder getThis() {
        return this;
    }
}
