package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import static org.uniprot.core.common.Utils.nonNullList;

import java.util.List;

/**
 * @author lgonzales
 */
public class GeneNameSynonymBuilder extends AbstractEvidencedValueBuilder<GeneNameSynonymBuilder, GeneNameSynonym> {

    public GeneNameSynonymBuilder(){

    }

    public GeneNameSynonymBuilder(String syn, List<Evidence> evidences) {
        this.value = syn;
        this.evidences = nonNullList(evidences);
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
