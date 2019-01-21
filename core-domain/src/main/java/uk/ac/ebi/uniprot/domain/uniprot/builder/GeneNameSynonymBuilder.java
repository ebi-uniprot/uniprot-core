package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

/**
 *
 * @author lgonzales
 */
public class GeneNameSynonymBuilder  extends AbstractEvidencedValueBuilder<GeneNameSynonymBuilder, GeneNameSynonym> {

    @Override
    protected GeneNameSynonymBuilder getThis() {
        return this;
    }

    @Override
    public GeneNameSynonym build() {
        return new GeneImpl.GeneNameSynonymImpl(value,evidences);
    }
}
