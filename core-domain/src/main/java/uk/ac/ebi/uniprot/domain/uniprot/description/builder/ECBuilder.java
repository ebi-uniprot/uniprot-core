package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ECImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

/**
 *
 * @author lgonzales
 */
public class ECBuilder extends AbstractEvidencedValueBuilder<ECBuilder, EC> {

    @Override
    protected ECBuilder getThis() {
        return this;
    }

    @Override
    public EC build() {
        return new ECImpl(value,evidences);
    }
}