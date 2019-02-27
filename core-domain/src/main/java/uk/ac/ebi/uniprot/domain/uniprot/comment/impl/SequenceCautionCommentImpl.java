package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SequenceCautionCommentImpl extends CommentImpl implements SequenceCautionComment {
    private static final long serialVersionUID = 4628964374292908502L;
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

    public SequenceCautionCommentImpl(SequenceCautionType sequenceCautionType, String sequence,
                                      List<String> positions, String note, List<Evidence> evidences) {
        super(CommentType.SEQUENCE_CAUTION);
        this.sequenceCautionType = sequenceCautionType;
        this.sequence = sequence;
        if ((positions == null) || positions.isEmpty()) {
            this.positions = Collections.emptyList();
        } else {
            this.positions = Collections.unmodifiableList(positions);
        }
        this.note = note;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
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
    public boolean hasSequence() {
        return Utils.notEmpty(this.sequence);
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean hasSequenceCautionType() {
        return this.sequenceCautionType != null;
    }

    @Override
    public boolean hasPositions() {
        return Utils.notEmpty(this.positions);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notEmpty(this.evidences);
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
