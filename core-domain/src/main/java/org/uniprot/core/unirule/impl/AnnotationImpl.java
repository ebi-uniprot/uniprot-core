package org.uniprot.core.unirule.impl;

import java.util.Objects;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.unirule.Annotation;

public class AnnotationImpl implements Annotation {

    private static final long serialVersionUID = -6246517843536310659L;
    private Comment comment;

    private Keyword keyword;

    private Gene gene;

    private UniProtKBCrossReference dbReference;

    private ProteinDescription proteinDescription;

    AnnotationImpl() {}

    AnnotationImpl(
            Comment comment,
            Keyword keyword,
            Gene gene,
            UniProtKBCrossReference dbReference,
            ProteinDescription proteinDescription) {
        this.comment = comment;
        this.keyword = keyword;
        this.gene = gene;
        this.dbReference = dbReference;
        this.proteinDescription = proteinDescription;
    }

    @Override
    public Comment getComment() {
        return comment;
    }

    @Override
    public Keyword getKeyword() {
        return keyword;
    }

    @Override
    public Gene getGene() {
        return gene;
    }

    public UniProtKBCrossReference getDbReference() {
        return dbReference;
    }

    @Override
    public ProteinDescription getProteinDescription() {
        return proteinDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnotationImpl that = (AnnotationImpl) o;
        return Objects.equals(comment, that.comment)
                && Objects.equals(keyword, that.keyword)
                && Objects.equals(gene, that.gene)
                && Objects.equals(dbReference, that.dbReference)
                && Objects.equals(proteinDescription, that.proteinDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, keyword, gene, dbReference, proteinDescription);
    }
}
