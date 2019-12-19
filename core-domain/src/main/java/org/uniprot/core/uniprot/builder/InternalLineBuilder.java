package org.uniprot.core.uniprot.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;
import org.uniprot.core.uniprot.impl.InternalLineImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class InternalLineBuilder extends AbstractValueBuilder<InternalLineBuilder, InternalLine> {
    private InternalLineType type;

    public InternalLineBuilder(InternalLineType type, String value) {
        super(value);
        this.type = type;
    }

    @Override
    public @Nonnull InternalLineBuilder from(@Nonnull InternalLine instance) {
        super.from(instance);
        this.type = instance.getType();
        return this;
    }

    @Override
    public @Nonnull InternalLine build() {
        return new InternalLineImpl(type, value);
    }

    @Override
    protected @Nonnull InternalLineBuilder getThis() {
        return this;
    }
}
