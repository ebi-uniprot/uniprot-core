package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SequenceCautionCommentImpl extends CommentImpl implements SequenceCautionComment {
    private SequenceCautionType sequenceCautionType;
    private String sequence;
    private List<String> positions;
    private String note;
    private List<Evidence> evidences;

    private SequenceCautionCommentImpl() {
        super(CommentType.SEQUENCE_CAUTION);
        this.evidences = Collections.emptyList();
        this.positions = Collections.emptyList();
    }

    public SequenceCautionCommentImpl(SequenceCautionCommentBuilder builder) {
        super(CommentType.SEQUENCE_CAUTION);
        this.sequenceCautionType = builder.getSequenceCautionType();
        this.sequence = builder.getSequence();
        if ((builder.getPositions() == null) || builder.getPositions().isEmpty()) {
            this.positions = Collections.emptyList();
        } else {
            this.positions = Collections.unmodifiableList(builder.getPositions());
        }
        this.note = builder.getNote();
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
        }
    }

    @Override
    public String getSequence() {
        return sequence;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public SequenceCautionType getSequenceCautionType() {
        return sequenceCautionType;
    }

    @Override
    public List<String> getPositions() {
        return positions;
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
        SequenceCautionCommentImpl that = (SequenceCautionCommentImpl) o;
        return sequenceCautionType == that.sequenceCautionType &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(positions, that.positions) &&
                Objects.equals(note, that.note) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sequenceCautionType, sequence, positions, note, evidences);
    }
}
