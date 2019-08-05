package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.common.Utils;


public class RnaEditingCommentImpl extends CommentImpl implements RnaEditingComment {
    private static final long serialVersionUID = -5382803114400917004L;
    private RnaEditingLocationType locationType;
    private List<RnaEdPosition> positions;
    private Note note;

    private RnaEditingCommentImpl() {
        super(CommentType.RNA_EDITING);
        this.positions = Collections.emptyList();
    }

    public RnaEditingCommentImpl(RnaEditingLocationType locationType,
                                 List<RnaEdPosition> positions, Note note) {
        super(CommentType.RNA_EDITING);
        this.locationType = locationType;
        if ((positions == null) || positions.isEmpty()) {
            this.positions = Collections.emptyList();
        } else {
            this.positions = Collections.unmodifiableList(positions);
        }
        this.note = note;
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
    public boolean hasLocationType() {
        return this.locationType != null;
    }

    @Override
    public boolean hasPositions() {
        return Utils.notEmpty(this.positions);
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
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
        public boolean hasEvidences() {
            return Utils.notEmpty(this.evidences);
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
