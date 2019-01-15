package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class SequenceCautionCommentImpl extends CommentImpl implements SequenceCautionComment, Serializable {
    private static final long serialVersionUID = 948798817799733755L;
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((positions == null) ? 0 : positions.hashCode());
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
        result = prime * result + ((sequenceCautionType == null) ? 0 : sequenceCautionType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SequenceCautionCommentImpl other = (SequenceCautionCommentImpl) obj;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (positions == null) {
            if (other.positions != null)
                return false;
        } else if (!positions.equals(other.positions))
            return false;
        if (sequence == null) {
            if (other.sequence != null)
                return false;
        } else if (!sequence.equals(other.sequence))
            return false;
        if (sequenceCautionType != other.sequenceCautionType)
            return false;
        return true;
    }
}
