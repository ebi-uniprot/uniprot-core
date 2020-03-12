package org.uniprot.core.uniprotkb.evidence.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

/**
 * Created 14/01/19
 *
 * @author Edd
 */
public abstract class AbstractEvidencedValueBuilder<
                B extends AbstractEvidencedValueBuilder<B, E>, E extends EvidencedValue>
        extends AbstractHasEvidencesBuilder<B, E> {
    protected String value;

    protected static <B extends AbstractEvidencedValueBuilder<B, E>, E extends EvidencedValue>
            void init(@Nonnull B builder, @Nonnull E instance) {
        AbstractHasEvidencesBuilder.init(builder, instance);
        builder.value(instance.getValue());
    }

    public @Nonnull B value(String value) {
        this.value = value;
        return getThis();
    }
}
