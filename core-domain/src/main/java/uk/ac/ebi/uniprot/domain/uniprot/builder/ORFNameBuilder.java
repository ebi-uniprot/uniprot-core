package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import java.util.List;

/**
 *
 * @author lgonzales
 */
public class ORFNameBuilder extends AbstractEvidencedValueBuilder<ORFNameBuilder, ORFName> {

    public ORFNameBuilder(){

    }

    public ORFNameBuilder(String orf, List<Evidence> evidences) {
        this.value = orf;
        this.evidences.addAll(evidences);
    }

    @Override
    protected ORFNameBuilder getThis() {
        return this;
    }

    @Override
    public ORFName build() {
        return new GeneImpl.ORFNameImpl(value,evidences);
    }
}
