package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RnaEditingCommentImpl extends CommentImpl implements RnaEditingComment {
    public static Position createPosition(String position, List<Evidence> evidences) {
        return new PositionImpl(position, evidences);
    }
    private final RnaEditingLocationType locationType;
    private final List<Position> locations;
    private final Optional<Note> note;
    public RnaEditingCommentImpl(RnaEditingLocationType locationType, List<Position> locations,
            Note note) {
        super(CommentType.RNA_EDITING);
       this.locationType = locationType;
       if ((locations == null) || locations.isEmpty()) {
           this.locations = Collections.emptyList();
       } else {
           this.locations = Collections.unmodifiableList(locations);
       }
       this.note = (note == null)? Optional.empty():  Optional.of(note);
    }

    @Override
    public RnaEditingLocationType getLocationType() {
       return locationType;
    }

    @Override
    public List<Position> getPositions() {
       return locations;
    }
    @Override
    public Optional<Note> getNote() {
       return note;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((locationType == null) ? 0 : locationType.hashCode());
        result = prime * result + ((locations == null) ? 0 : locations.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
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
        RnaEditingCommentImpl other = (RnaEditingCommentImpl) obj;
        if (locationType != other.locationType)
            return false;
        if (locations == null) {
            if (other.locations != null)
                return false;
        } else if (!locations.equals(other.locations))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }
    
    static class PositionImpl implements Position{
        private final String position;
        private final  List<Evidence> evidences;
        public PositionImpl(String position, List<Evidence> evidences) {
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
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
            result = prime * result + ((position == null) ? 0 : position.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PositionImpl other = (PositionImpl) obj;
            if (evidences == null) {
                if (other.evidences != null)
                    return false;
            } else if (!evidences.equals(other.evidences))
                return false;
            if (position == null) {
                if (other.position != null)
                    return false;
            } else if (!position.equals(other.position))
                return false;
            return true;
        }
        
    }
}
