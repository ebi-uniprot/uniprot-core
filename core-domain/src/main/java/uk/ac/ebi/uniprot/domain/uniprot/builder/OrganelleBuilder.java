package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.OrganelleImpl;

import java.util.List;

/**
 * Created 23/01/19
 *
 * @author Edd
 */
public class OrganelleBuilder extends AbstractEvidencedValueBuilder<OrganelleBuilder, Organelle> {
    private GeneEncodingType geneEncodingType;

    public OrganelleBuilder(GeneEncodingType geneEncodingType, String value, List<Evidence> evidences) {
        this.geneEncodingType = geneEncodingType;
        this.value = value;
        this.evidences = evidences;
    }

    @Override
    public Organelle build() {
        return new OrganelleImpl(geneEncodingType, value, evidences);
    }

    @Override
    protected OrganelleBuilder getThis() {
        return this;
    }
}
