package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class RnaEditingCommentImpl extends CommentImpl implements RnaEditingComment, Serializable {
    private static final long serialVersionUID = 7875885904317295953L;
    private RnaEditingLocationType locationType;
    private List<RnaEdPosition> positions;
    private Note note;

    public RnaEditingCommentImpl() {
        super(CommentType.RNA_EDITING);
        this.positions = Collections.emptyList();
    }

    public RnaEditingCommentImpl(RnaEditingCommentBuilder builder) {
        super(CommentType.RNA_EDITING);
        this.locationType = builder.getLocationType();
        if ((builder.getPositions() == null) || builder.getPositions().isEmpty()) {
            this.positions = Collections.emptyList();
        } else {
            this.positions = Collections.unmodifiableList(builder.getPositions());
        }
        this.note = builder.getNote();
    }

    @Override
    public RnaEditingLocationType getLocationType() {
        return locationType;
    }

    @Override
    public List<RnaEdPosition> getPositions() {
        return positions;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RnaEditingCommentImpl that = (RnaEditingCommentImpl) o;
        return locationType == that.locationType &&
                Objects.equals(positions, that.positions) &&
                Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), locationType, positions, note);
    }

    public static class RnaEdPositionImpl implements RnaEdPosition {
        private String position;
        private List<Evidence> evidences;

        private RnaEdPositionImpl() {
            this.evidences = Collections.emptyList();
        }

        public RnaEdPositionImpl(String position, List<Evidence> evidences) {
            this.position = position;
            if ((evidences == null) || evidences.isEmpty()) {
                this.evidences = Collections.emptyList();
            } else {
                this.evidences = Collections.unmodifiableList(evidences);
            }
        }

        @Override
        public List<Evidence> getEvidences() {
            return evidences;
        }

        @Override
        public String getPosition() {
            return position;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RnaEdPositionImpl that = (RnaEdPositionImpl) o;
            return Objects.equals(position, that.position) &&
                    Objects.equals(evidences, that.evidences);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, evidences);
        }
    }
}
