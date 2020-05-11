package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.gene.ORFName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilder;

import java.util.List;

import javax.annotation.Nonnull;

/** @author lgonzales */
public class ORFNameBuilder extends AbstractEvidencedValueBuilder<ORFNameBuilder, ORFName> {

    public ORFNameBuilder() {}

    public ORFNameBuilder(String orf, List<Evidence> evidences) {
        this.value = orf;
        this.evidences = modifiableList(evidences);
    }

    @Override
    protected @Nonnull ORFNameBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull ORFName build() {
        return new GeneImpl.ORFNameImpl(value, evidences);
    }

    public static @Nonnull ORFNameBuilder from(@Nonnull ORFName instance) {
        return new ORFNameBuilder(instance.getValue(), instance.getEvidences());
    }
}
