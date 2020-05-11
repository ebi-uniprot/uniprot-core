package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.uniprotkb.comment.IsoformName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilder;

import java.util.List;

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

    public static @Nonnull IsoformNameBuilder from(@Nonnull IsoformName instance) {
        return new IsoformNameBuilder(instance.getValue(), instance.getEvidences());
    }
}
