package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.impl.SourceLineImpl;

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
    public SourceLine build() {
        return new SourceLineImpl(value);
    }

    @Override
    protected SourceLineBuilder getThis() {
        return this;
    }
}
