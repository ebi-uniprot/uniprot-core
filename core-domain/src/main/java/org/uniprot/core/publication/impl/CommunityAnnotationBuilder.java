package org.uniprot.core.publication.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.publication.CommunityAnnotation;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class CommunityAnnotationBuilder implements Builder<CommunityAnnotation> {
    private String proteinOrGene;
    private String function;
    private String disease;
    private String comment;

    public CommunityAnnotationBuilder proteinOrGene(String proteinOrGene) {
        this.proteinOrGene = proteinOrGene;
        return this;
    }

    public CommunityAnnotationBuilder function(String function) {
        this.function = function;
        return this;
    }

    public CommunityAnnotationBuilder disease(String disease) {
        this.disease = disease;
        return this;
    }

    public CommunityAnnotationBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    @Nonnull
    @Override
    public CommunityAnnotation build() {
        return new CommunityAnnotationImpl(proteinOrGene, function, disease, comment);
    }

    public static CommunityAnnotationBuilder from(@Nonnull CommunityAnnotation instance) {
        return new CommunityAnnotationBuilder()
                .function(instance.getFunction())
                .disease(instance.getDisease())
                .comment(instance.getComment())
                .proteinOrGene(instance.getProteinOrGene());
    }
}
