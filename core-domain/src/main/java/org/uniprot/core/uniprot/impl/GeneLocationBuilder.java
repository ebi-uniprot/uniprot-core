package org.uniprot.core.uniprot.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.evidence.impl.AbstractEvidencedValueBuilder;

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

    public static @Nonnull GeneLocationBuilder from(@Nonnull GeneLocation instance) {
        GeneLocationBuilder builder = new GeneLocationBuilder();
        AbstractEvidencedValueBuilder.init(builder, instance);
        builder.geneEncodingType(instance.getGeneEncodingType());
        return builder;
    }

    @Override
    protected @Nonnull GeneLocationBuilder getThis() {
        return this;
    }
}
