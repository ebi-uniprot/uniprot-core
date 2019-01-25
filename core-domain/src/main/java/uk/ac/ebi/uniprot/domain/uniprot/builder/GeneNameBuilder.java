package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import java.util.List;

public class GeneNameBuilder extends AbstractEvidencedValueBuilder<GeneNameBuilder, GeneName> {

    public GeneNameBuilder(String name, List<Evidence> evidences) {
        this.value = name;
        this.evidences.addAll(evidences);
    }

    @Override
    protected GeneNameBuilder getThis() {
        return this;
    }

    @Override
    public GeneName build() {
        return new GeneImpl.GeneNameImpl(value, evidences);
    }
}
