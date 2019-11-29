package org.uniprot.core.uniprot.builder;

import org.uniprot.core.builder.AbstractValueBuilder;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.impl.SourceLineImpl;

import javax.annotation.Nonnull;

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
