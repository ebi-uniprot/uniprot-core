package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * @author lgonzales
 */
public class OrderedLocusNameBuilder extends AbstractEvidencedValueBuilder<OrderedLocusNameBuilder, OrderedLocusName> {

    public OrderedLocusNameBuilder(){

    }

    public OrderedLocusNameBuilder(String oln, List<Evidence> evidences) {
        this.value = oln;
        nonNullAddAll(evidences, this.evidences);
    }

    @Override
    protected OrderedLocusNameBuilder getThis() {
        return this;
    }

    @Override
    public OrderedLocusName build() {
        return new GeneImpl.OrderedLocusNameImpl(value, evidences);
    }
}
