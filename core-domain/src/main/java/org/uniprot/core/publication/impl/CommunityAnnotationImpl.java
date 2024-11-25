package org.uniprot.core.publication.impl;

import java.time.LocalDate;
import java.util.Objects;

import org.uniprot.core.publication.CommunityAnnotation;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class CommunityAnnotationImpl implements CommunityAnnotation {
    private static final long serialVersionUID = 8368111594497409648L;

    private final String proteinOrGene;
    private final String function;
    private final String disease;
    private final String comment;
    private final LocalDate submissionDate;

    public CommunityAnnotationImpl() {
        this(null, null, null, null, null);
    }

    public CommunityAnnotationImpl(
            String proteinOrGene, String function, String disease, String comment, LocalDate submissionDate) {
        this.proteinOrGene = proteinOrGene;
        this.function = function;
        this.disease = disease;
        this.comment = comment;
        this.submissionDate = submissionDate;
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
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityAnnotationImpl that = (CommunityAnnotationImpl) o;
        return Objects.equals(proteinOrGene, that.proteinOrGene)
                && Objects.equals(function, that.function)
                && Objects.equals(disease, that.disease)
                && Objects.equals(comment, that.comment)
                && Objects.equals(submissionDate, that.submissionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proteinOrGene, function, disease, comment, submissionDate);
    }

    @Override
    public String toString() {
        return "CommunityAnnotationImpl{"
                + "proteinOrGene='"
                + proteinOrGene
                + '\''
                + ", function='"
                + function
                + '\''
                + ", disease='"
                + disease
                + '\''
                + ", comment='"
                + comment
                + '\''
                + ", submissionDate='"
                + submissionDate
                + '\''
                + '}';
    }
}
