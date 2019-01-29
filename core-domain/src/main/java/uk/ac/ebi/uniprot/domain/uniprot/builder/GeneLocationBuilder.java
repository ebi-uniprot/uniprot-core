package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.GeneLocation;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneLocationImpl;

import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

/**
 * Created 23/01/19
 *
 * @author Edd
 */
public class GeneLocationBuilder extends AbstractEvidencedValueBuilder<GeneLocationBuilder, GeneLocation> {
    private GeneEncodingType geneEncodingType;

    public GeneLocationBuilder(){

    }

    public GeneLocationBuilder(GeneEncodingType geneEncodingType, String value, List<Evidence> evidences) {
        this.geneEncodingType = geneEncodingType;
        this.value = value;
        this.evidences = nonNullList(evidences);
    }

    @Override
    public GeneLocation build() {
        return new GeneLocationImpl(geneEncodingType, value, evidences);
    }

    public GeneLocationBuilder geneEncodingType(GeneEncodingType geneEncodingType) {
        this.geneEncodingType = geneEncodingType;
        return this;
    }

    @Override
    protected GeneLocationBuilder getThis() {
        return this;
    }
}
