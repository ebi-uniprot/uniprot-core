package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.gene.GeneName;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilder;

import java.util.List;

import javax.annotation.Nonnull;

public class GeneNameBuilder extends AbstractEvidencedValueBuilder<GeneNameBuilder, GeneName> {

    public GeneNameBuilder() {}

    public GeneNameBuilder(String name, List<Evidence> evidences) {
        this.value = name;
        this.evidences = modifiableList(evidences);
    }

    @Override
    protected @Nonnull GeneNameBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull GeneName build() {
        return new GeneImpl.GeneNameImpl(value, evidences);
    }

    public static @Nonnull GeneNameBuilder from(@Nonnull GeneName instance) {
        return new GeneNameBuilder(instance.getValue(), instance.getEvidences());
    }
}
