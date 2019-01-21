package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

/**
 *
 * @author lgonzales
 */
public class OrderedLocusNameBuilder extends AbstractEvidencedValueBuilder<OrderedLocusNameBuilder, OrderedLocusName> {
    @Override
    protected OrderedLocusNameBuilder getThis() {
        return this;
    }

    @Override
    public OrderedLocusName build() {
        return new GeneImpl.OrderedLocusNameImpl(value,evidences);
    }
}
