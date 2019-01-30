package uk.ac.ebi.uniprot.domain.uniprot.feature.builder;

import uk.ac.ebi.uniprot.domain.builder.AbstractValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;

/**
 * Created 30/01/19
 *
 * @author Edd
 */
public class FeatureIdBuilder extends AbstractValueBuilder<FeatureIdBuilder, FeatureId> {
    @Override
    public FeatureId build() {
        return new FeatureIdImpl(value);
    }

    @Override
    protected FeatureIdBuilder getThis() {
        return this;
    }

    public FeatureIdBuilder(String value) {
        super(value);
    }
}
