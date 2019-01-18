package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

public final class MassSpectrometryCommentBuilder implements CommentBuilder<MassSpectrometryCommentBuilder, MassSpectrometryComment> {
    private MassSpectrometryMethod method;
    private Double molWeight;
    private Double molWeightError;
    private String note;
    private List<MassSpectrometryRange> ranges = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    public MassSpectrometryComment build() {
        return new MassSpectrometryCommentImpl(this);
    }

    @Override
    public MassSpectrometryCommentBuilder from(MassSpectrometryComment instance) {
        ranges.clear();
        evidences.clear();
        return this
                .molWeight(instance.getMolWeight())
                .molWeightError(instance.getMolWeightError())
                .evidences(instance.getEvidences())
                .ranges(instance.getRanges())
                .note(instance.getNote())
                .method(instance.getMethod());
    }

    public MassSpectrometryCommentBuilder method(MassSpectrometryMethod method) {
        this.method = method;
        return this;
    }

    public MassSpectrometryCommentBuilder molWeight(Double molWeight) {
        this.molWeight = molWeight;
        return this;
    }

    public MassSpectrometryCommentBuilder molWeightError(Double molWeightError) {
        if ((molWeightError != null) && (Math.abs(molWeightError - 0.0) <= Double.MIN_VALUE)) {
            this.molWeightError = null;
        }
        this.molWeightError = molWeightError;
        return this;
    }

    public MassSpectrometryCommentBuilder note(String note) {
        this.note = note;
        return this;
    }

    public MassSpectrometryCommentBuilder ranges(List<MassSpectrometryRange> ranges) {
        this.ranges.addAll(ranges);
        return this;
    }

    public MassSpectrometryCommentBuilder addRange(MassSpectrometryRange range) {
        this.ranges.add(range);
        return this;
    }

    public MassSpectrometryCommentBuilder evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return this;
    }

    public MassSpectrometryCommentBuilder addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }

    public MassSpectrometryMethod getMethod() {
        return method;
    }

    public Double getMolWeight() {
        return molWeight;
    }

    public Double getMolWeightError() {
        return molWeightError;
    }

    public String getNote() {
        return note;
    }

    public List<MassSpectrometryRange> getRanges() {
        return ranges;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}
