package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.gene.OrderedLocusName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilder;

/** @author lgonzales */
public class OrderedLocusNameBuilder
        extends AbstractEvidencedValueBuilder<OrderedLocusNameBuilder, OrderedLocusName> {

    public OrderedLocusNameBuilder() {}

    public OrderedLocusNameBuilder(String oln, List<Evidence> evidences) {
        this.value = oln;
        this.evidences = modifiableList(evidences);
    }

    @Override
    protected @Nonnull OrderedLocusNameBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull OrderedLocusName build() {
        return new GeneImpl.OrderedLocusNameImpl(value, evidences);
    }

    public static @Nonnull OrderedLocusNameBuilder from(@Nonnull OrderedLocusName instance) {
        return new OrderedLocusNameBuilder(instance.getValue(), instance.getEvidences());
    }
}
