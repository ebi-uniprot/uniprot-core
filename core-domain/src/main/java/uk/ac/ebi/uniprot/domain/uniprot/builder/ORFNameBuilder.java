package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

/**
 *
 * @author lgonzales
 */
public class ORFNameBuilder extends AbstractEvidencedValueBuilder<ORFNameBuilder, ORFName> {
    @Override
    protected ORFNameBuilder getThis() {
        return this;
    }

    @Override
    public ORFName build() {
        return new GeneImpl.ORFNameImpl(value,evidences);
    }
}
