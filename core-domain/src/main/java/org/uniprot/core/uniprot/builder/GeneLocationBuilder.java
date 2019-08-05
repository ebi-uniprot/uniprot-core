package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.nonNullList;

import java.util.List;

import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.GeneLocationImpl;

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
