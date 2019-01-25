package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIdImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtIdBuilder extends AbstractValueBuilder<UniProtIdBuilder, UniProtId> {
    public UniProtIdBuilder(String value) {
        super(value);
    }

    @Override
    public UniProtId build() {
        return new UniProtIdImpl(value);
    }

    @Override
    protected UniProtIdBuilder getThis() {
        return this;
    }
}
