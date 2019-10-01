package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.gene.GeneNameSynonym;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.GeneImpl;

/** @author lgonzales */
public class GeneNameSynonymBuilder
        extends AbstractEvidencedValueBuilder<GeneNameSynonymBuilder, GeneNameSynonym> {

    public GeneNameSynonymBuilder() {}

    public GeneNameSynonymBuilder(String syn, List<Evidence> evidences) {
        this.value = syn;
        this.evidences = modifiableList(evidences);
    }

    @Override
    protected GeneNameSynonymBuilder getThis() {
        return this;
    }

    @Override
    public GeneNameSynonym build() {
        return new GeneImpl.GeneNameSynonymImpl(value, evidences);
    }
}
