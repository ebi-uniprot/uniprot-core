package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MassSpectrometryCommentImpl extends CommentImpl implements MassSpectrometryComment {
    private MassSpectrometryMethod method;
    private Float molWeight;
    private Float molWeightError;
    private String note;
    private List<MassSpectrometryRange> ranges;
    private List<Evidence> evidences;

    private MassSpectrometryCommentImpl() {
        super(CommentType.MASS_SPECTROMETRY);
        this.ranges = Collections.emptyList();
        this.evidences = Collections.emptyList();
    }

    public MassSpectrometryCommentImpl(MassSpectrometryCommentBuilder builder) {
        super(CommentType.MASS_SPECTROMETRY);
        this.method = builder.getMethod();
        this.molWeight = builder.getMolWeight();

        this.molWeightError = builder.getMolWeightError();
        this.note = builder.getNote();
        if ((builder.getRanges() == null) || builder.getRanges().isEmpty()) {
            this.ranges = Collections.emptyList();
        } else {
            this.ranges = Collections.unmodifiableList(builder.getRanges());
        }
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
        }
    }

    @Override
    public Float getMolWeightError() {
        return molWeightError;
    }

    @Override
    public Float getMolWeight() {
        return molWeight;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public List<MassSpectrometryRange> getRanges() {
        return ranges;
    }

    @Override
    public MassSpectrometryMethod getMethod() {
        return method;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MassSpectrometryCommentImpl that = (MassSpectrometryCommentImpl) o;
        return method == that.method &&
                Objects.equals(molWeight, that.molWeight) &&
                Objects.equals(molWeightError, that.molWeightError) &&
                Objects.equals(note, that.note) &&
                Objects.equals(ranges, that.ranges) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), method, molWeight, molWeightError, note, ranges, evidences);
    }
}
