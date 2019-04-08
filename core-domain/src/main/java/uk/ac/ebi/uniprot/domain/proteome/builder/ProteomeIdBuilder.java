package uk.ac.ebi.uniprot.domain.proteome.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;
import uk.ac.ebi.uniprot.domain.proteome.impl.ProteomeIdImpl;

public class ProteomeIdBuilder extends AbstractValueBuilder<ProteomeIdBuilder, ProteomeId> {
    public ProteomeIdBuilder(String value) {
        super(value);
    }

    @Override
    public ProteomeId build() {
        return new ProteomeIdImpl(value);
    }

    @Override
    protected ProteomeIdBuilder getThis() {
        return this;
    }

}
