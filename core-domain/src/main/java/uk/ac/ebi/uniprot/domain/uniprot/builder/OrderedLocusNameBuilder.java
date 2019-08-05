package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

/**
 * @author lgonzales
 */
public class OrderedLocusNameBuilder extends AbstractEvidencedValueBuilder<OrderedLocusNameBuilder, OrderedLocusName> {

    public OrderedLocusNameBuilder(){

    }

    public OrderedLocusNameBuilder(String oln, List<Evidence> evidences) {
        this.value = oln;
        this.evidences = nonNullList(evidences);
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
