package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import static org.uniprot.core.common.Utils.nonNullAdd;
import static org.uniprot.core.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

public final class MassSpectrometryCommentBuilder implements CommentBuilder<MassSpectrometryCommentBuilder, MassSpectrometryComment> {
    private MassSpectrometryMethod method;
    private Float molWeight;
    private Float molWeightError;
    private String note;
    private List<MassSpectrometryRange> ranges = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    public MassSpectrometryComment build() {
        return new MassSpectrometryCommentImpl(method, molWeight, molWeightError, note, ranges, evidences);
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

    public MassSpectrometryCommentBuilder molWeight(Float molWeight) {
        this.molWeight = molWeight;
        return this;
    }

    public MassSpectrometryCommentBuilder molWeightError(Float molWeightError) {
        if ((molWeightError != null) && (Math.abs(molWeightError - 0.0) <= Float.MIN_VALUE)) {
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
        this.ranges = nonNullList(ranges);
        return this;
    }

    public MassSpectrometryCommentBuilder addRange(MassSpectrometryRange range) {
        nonNullAdd(range, this.ranges);
        return this;
    }

    public MassSpectrometryCommentBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public MassSpectrometryCommentBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }
}
