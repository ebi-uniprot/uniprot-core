package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

public class GeneNameBuilder extends AbstractEvidencedValueBuilder<GeneNameBuilder, GeneName> {

    public GeneNameBuilder(){

    }

    public GeneNameBuilder(String name, List<Evidence> evidences) {
        this.value = name;
        this.evidences = nonNullList(evidences);
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
