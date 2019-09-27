package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.impl.SequenceCautionCommentImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

public final class SequenceCautionCommentBuilder
        implements CommentBuilder<SequenceCautionCommentBuilder, SequenceCautionComment> {
    private SequenceCautionType sequenceCautionType;
    private String sequence;
    private String note;
    private List<String> positions = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    public SequenceCautionComment build() {
        return new SequenceCautionCommentImpl(
                sequenceCautionType, sequence, positions, note, evidences);
    }

    @Override
    public SequenceCautionCommentBuilder from(SequenceCautionComment instance) {
        positions.clear();
        evidences.clear();
        return this.sequenceCautionType(instance.getSequenceCautionType())
                .sequence(instance.getSequence())
                .evidences(instance.getEvidences())
                .note(instance.getNote())
                .positions(instance.getPositions());
    }

    public SequenceCautionCommentBuilder sequenceCautionType(
            SequenceCautionType sequenceCautionType) {
        this.sequenceCautionType = sequenceCautionType;
        return this;
    }

    public SequenceCautionCommentBuilder sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public SequenceCautionCommentBuilder positions(List<String> positions) {
        this.positions = nonNullList(positions);
        return this;
    }

    public SequenceCautionCommentBuilder addPosition(String position) {
        nonNullAdd(position, this.positions);
        return this;
    }

    public SequenceCautionCommentBuilder note(String note) {
        this.note = note;
        return this;
    }

    public SequenceCautionCommentBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public SequenceCautionCommentBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }
}
