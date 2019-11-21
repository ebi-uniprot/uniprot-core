package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class RnaEditingCommentImpl extends CommentHasMoleculeImpl implements RnaEditingComment {
    private static final long serialVersionUID = -5382803114400917004L;
    private RnaEditingLocationType locationType;
    private List<RnaEdPosition> positions;
    private Note note;

    RnaEditingCommentImpl() {
        super(CommentType.RNA_EDITING, null);
        this.positions = Collections.emptyList();
    }

    public RnaEditingCommentImpl(
            String molecule,
            RnaEditingLocationType locationType,
            List<RnaEdPosition> positions,
            Note note) {
        super(CommentType.RNA_EDITING, molecule);
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
        return Utils.notNullOrEmpty(this.positions);
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
        return locationType == that.locationType
                && Objects.equals(positions, that.positions)
                && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), locationType, positions, note);
    }

    public static class RnaEdPositionImpl implements RnaEdPosition {
        /** */
        private static final long serialVersionUID = 1L;

        private String position;
        private List<Evidence> evidences;

        RnaEdPositionImpl() {
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
            return Utils.notNullOrEmpty(this.evidences);
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
            return Objects.equals(position, that.position)
                    && Objects.equals(evidences, that.evidences);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, evidences);
        }
    }
}
