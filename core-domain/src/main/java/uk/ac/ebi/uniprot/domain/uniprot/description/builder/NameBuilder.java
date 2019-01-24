package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.NameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

public class NameBuilder extends AbstractEvidencedValueBuilder<NameBuilder, Name> {

    @Override
    protected NameBuilder getThis() {
        return this;
    }

    @Override
    public Name build() {
        return new NameImpl(value,evidences);
    }
}
