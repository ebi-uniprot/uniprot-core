package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.SequenceCautionComment;
import org.uniprot.core.uniprotkb.comment.SequenceCautionType;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class SequenceCautionCommentImpl extends CommentHasMoleculeImpl
        implements SequenceCautionComment {
    private static final long serialVersionUID = 4628964374292908502L;
    private SequenceCautionType sequenceCautionType;
    private String sequence;
    private String note;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    SequenceCautionCommentImpl() {
        super(CommentType.SEQUENCE_CAUTION, null);
        this.evidences = Collections.emptyList();
    }

    SequenceCautionCommentImpl(
            String molecule,
            SequenceCautionType sequenceCautionType,
            String sequence,
            String note,
            List<Evidence> evidences) {
        super(CommentType.SEQUENCE_CAUTION, molecule);
        this.sequenceCautionType = sequenceCautionType;
        this.sequence = sequence;
        this.note = note;
        this.evidences = Utils.unmodifiableList(evidences);
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
    public boolean hasSequence() {
        return Utils.notNullNotEmpty(this.sequence);
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
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SequenceCautionCommentImpl that = (SequenceCautionCommentImpl) o;
        return sequenceCautionType == that.sequenceCautionType
                && Objects.equals(sequence, that.sequence)
                && Objects.equals(note, that.note)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sequenceCautionType, sequence, note, evidences);
    }
}
