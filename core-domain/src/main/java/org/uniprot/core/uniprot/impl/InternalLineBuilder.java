package org.uniprot.core.uniprot.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class InternalLineBuilder extends AbstractValueBuilder<InternalLine> {
    private InternalLineType type;

    public InternalLineBuilder(InternalLineType type, String value) {
        super(value);
        this.type = type;
    }

    public static @Nonnull InternalLineBuilder from(@Nonnull InternalLine instance) {
        return new InternalLineBuilder(instance.getType(), instance.getValue());
    }

    @Override
    public @Nonnull InternalLine build() {
        return new InternalLineImpl(type, value);
    }
}
