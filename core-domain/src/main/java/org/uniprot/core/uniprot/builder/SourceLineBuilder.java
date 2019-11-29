package org.uniprot.core.uniprot.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.impl.SourceLineImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class SourceLineBuilder extends AbstractValueBuilder<SourceLineBuilder, SourceLine> {
    public SourceLineBuilder(String value) {
        super(value);
    }

    @Override
    public @Nonnull SourceLine build() {
        return new SourceLineImpl(value);
    }

    @Override
    protected @Nonnull SourceLineBuilder getThis() {
        return this;
    }
}
