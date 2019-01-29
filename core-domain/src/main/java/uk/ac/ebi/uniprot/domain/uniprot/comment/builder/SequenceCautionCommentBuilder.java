package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SequenceCautionCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

public final class SequenceCautionCommentBuilder implements CommentBuilder<SequenceCautionCommentBuilder, SequenceCautionComment> {
    private SequenceCautionType sequenceCautionType;
    private String sequence;
    private String note;
    private List<String> positions = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    public SequenceCautionComment build() {
        return new SequenceCautionCommentImpl(this);
    }

    @Override
    public SequenceCautionCommentBuilder from(SequenceCautionComment instance) {
        positions.clear();
        evidences.clear();
        return this
                .sequenceCautionType(instance.getSequenceCautionType())
                .sequence(instance.getSequence())
                .evidences(instance.getEvidences())
                .note(instance.getNote())
                .positions(instance.getPositions());
    }

    public SequenceCautionCommentBuilder sequenceCautionType(SequenceCautionType sequenceCautionType) {
        this.sequenceCautionType = sequenceCautionType;
        return this;
    }

    public SequenceCautionCommentBuilder sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public SequenceCautionCommentBuilder positions(List<String> positions) {
        nonNullAddAll(positions, this.positions);
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
        nonNullAddAll(evidences, this.evidences);
        return this;
    }

    public SequenceCautionCommentBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }

    public SequenceCautionType getSequenceCautionType() {
        return sequenceCautionType;
    }

    public String getSequence() {
        return sequence;
    }

    public String getNote() {
        return note;
    }

    public List<String> getPositions() {
        return positions;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}
