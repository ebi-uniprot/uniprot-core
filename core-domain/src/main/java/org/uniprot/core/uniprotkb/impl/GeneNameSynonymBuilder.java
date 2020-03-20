package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.gene.GeneNameSynonym;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.AbstractEvidencedValueBuilder;

/** @author lgonzales */
public class GeneNameSynonymBuilder
        extends AbstractEvidencedValueBuilder<GeneNameSynonymBuilder, GeneNameSynonym> {

    public GeneNameSynonymBuilder() {}

    public GeneNameSynonymBuilder(String syn, List<Evidence> evidences) {
        this.value = syn;
        this.evidences = modifiableList(evidences);
    }

    @Override
    protected @Nonnull GeneNameSynonymBuilder getThis() {
        return this;
    }

    @Override
    public @Nonnull GeneNameSynonym build() {
        return new GeneImpl.GeneNameSynonymImpl(value, evidences);
    }

    public static @Nonnull GeneNameSynonymBuilder from(@Nonnull GeneNameSynonym instance) {
        return new GeneNameSynonymBuilder(instance.getValue(), instance.getEvidences());
    }
}
