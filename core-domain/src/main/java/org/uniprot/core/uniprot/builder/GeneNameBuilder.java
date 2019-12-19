package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.gene.GeneName;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.GeneImpl;

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
}
