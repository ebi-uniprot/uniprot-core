package org.uniprot.core.unirule.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.impl.AnnotationImpl;
import org.uniprot.core.util.Utils;

public class AnnotationBuilder implements Builder<Annotation> {

    private Comment comment;

    private Keyword keyword;

    private Gene gene;

    private UniProtKBCrossReference dbReference;

    private ProteinDescription proteinDescription;

    public AnnotationBuilder comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public AnnotationBuilder keyword(Keyword keyword) {
        this.keyword = keyword;
        return this;
    }

    public AnnotationBuilder gene(Gene gene) {
        this.gene = gene;
        return this;
    }

    public AnnotationBuilder dbReference(UniProtKBCrossReference dbReference) {
        this.dbReference = dbReference;
        return this;
    }

    public AnnotationBuilder proteinDescription(ProteinDescription proteinDescription) {
        this.proteinDescription = proteinDescription;
        return this;
    }

    @Nonnull
    @Override
    public Annotation build() {
        return new AnnotationImpl(comment, keyword, gene, dbReference, proteinDescription);
    }

    public static @Nonnull AnnotationBuilder from(@Nonnull Annotation instance) {
        Utils.nullThrowIllegalArgument(instance);
        AnnotationBuilder builder = new AnnotationBuilder();
        builder.comment(instance.getComment())
                .keyword(instance.getKeyword())
                .gene(instance.getGene())
                .dbReference(instance.getDbReference())
                .proteinDescription(instance.getProteinDescription());
        return builder;
    }
}
