package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.CommunityAnnotation;

import java.util.Objects;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class CommunityAnnotationImpl implements CommunityAnnotation {
    private final String proteinOrGene;
    private final String function;
    private final String disease;
    private final String comment;

    public CommunityAnnotationImpl(
            String proteinOrGene, String function, String disease, String comment) {
        this.proteinOrGene = proteinOrGene;
        this.function = function;
        this.disease = disease;
        this.comment = comment;
    }

    @Override
    public String getProteinOrGene() {
        return proteinOrGene;
    }

    @Override
    public String getFunction() {
        return function;
    }

    @Override
    public String getDisease() {
        return disease;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityAnnotationImpl that = (CommunityAnnotationImpl) o;
        return Objects.equals(proteinOrGene, that.proteinOrGene)
                && Objects.equals(function, that.function)
                && Objects.equals(disease, that.disease)
                && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proteinOrGene, function, disease, comment);
    }

    @Override
    public String toString() {
        return "CommunityAnnotationImpl{" +
                "proteinOrGene='" + proteinOrGene + '\'' +
                ", function='" + function + '\'' +
                ", disease='" + disease + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
