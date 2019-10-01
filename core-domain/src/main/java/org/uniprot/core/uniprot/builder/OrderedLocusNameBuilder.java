package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.gene.OrderedLocusName;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.GeneImpl;

/** @author lgonzales */
public class OrderedLocusNameBuilder
        extends AbstractEvidencedValueBuilder<OrderedLocusNameBuilder, OrderedLocusName> {

    public OrderedLocusNameBuilder() {}

    public OrderedLocusNameBuilder(String oln, List<Evidence> evidences) {
        this.value = oln;
        this.evidences = modifiableList(evidences);
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
