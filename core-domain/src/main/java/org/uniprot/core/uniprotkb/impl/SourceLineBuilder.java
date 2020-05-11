package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.impl.AbstractValueBuilder;
import org.uniprot.core.uniprotkb.SourceLine;

import javax.annotation.Nonnull;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class SourceLineBuilder extends AbstractValueBuilder<SourceLine> {
    public SourceLineBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull SourceLine build() {
        return new SourceLineImpl(value);
    }

    public static @Nonnull SourceLineBuilder from(@Nonnull SourceLine instance) {
        return new SourceLineBuilder(instance.getValue());
    }
}
