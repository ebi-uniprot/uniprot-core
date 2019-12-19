package org.uniprot.core.uniprot.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.GeneLocationImpl;

/**
 * Created 23/01/19
 *
 * @author Edd
 */
public class GeneLocationBuilder
        extends AbstractEvidencedValueBuilder<GeneLocationBuilder, GeneLocation> {
    private GeneEncodingType geneEncodingType;

    public GeneLocationBuilder() {}

    @Override
    public @Nonnull GeneLocation build() {
        return new GeneLocationImpl(geneEncodingType, value, evidences);
    }

    public @Nonnull GeneLocationBuilder geneEncodingType(GeneEncodingType geneEncodingType) {
        this.geneEncodingType = geneEncodingType;
        return this;
    }

    @Override
    public @Nonnull GeneLocationBuilder from(@Nonnull GeneLocation instance) {
        super.from(instance);
        this.geneEncodingType(instance.getGeneEncodingType());
        return this;
    }

    @Override
    protected @Nonnull GeneLocationBuilder getThis() {
        return this;
    }
}
