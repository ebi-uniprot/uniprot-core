package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public class UniProtAccessionBuilder extends AbstractValueBuilder<UniProtAccessionBuilder, UniProtAccession> {
    public UniProtAccessionBuilder(String value) {
        super(value);
    }

    @Override
    public UniProtAccession build() {
        return new UniProtAccessionImpl(value);
    }

    @Override
    protected UniProtAccessionBuilder getThis() {
        return this;
    }
}
