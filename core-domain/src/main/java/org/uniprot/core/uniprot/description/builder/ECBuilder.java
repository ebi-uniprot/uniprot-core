package org.uniprot.core.uniprot.description.builder;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.impl.ECImpl;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;

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