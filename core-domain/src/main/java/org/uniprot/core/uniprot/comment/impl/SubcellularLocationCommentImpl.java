package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;
import org.uniprot.core.util.Utils;

public class SubcellularLocationCommentImpl extends CommentHasMoleculeImpl
        implements SubcellularLocationComment {
    private static final long serialVersionUID = 955858123969540661L;
    private Note note;
    private List<SubcellularLocation> subcellularLocations;

    SubcellularLocationCommentImpl() {
        super(CommentType.SUBCELLULAR_LOCATION, null);
        this.subcellularLocations = Collections.emptyList();
    }

    public SubcellularLocationCommentImpl(
            String molecule, List<SubcellularLocation> subcellularLocations, Note note) {
        super(CommentType.SUBCELLULAR_LOCATION, molecule);
        if ((subcellularLocations == null) || subcellularLocations.isEmpty()) {
            this.subcellularLocations = Collections.emptyList();
        } else {
            this.subcellularLocations = Collections.unmodifiableList(subcellularLocations);
        }
        this.note = note;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public List<SubcellularLocation> getSubcellularLocations() {
        return subcellularLocations;
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean hasSubcellularLocations() {
        return Utils.notNullOrEmpty(this.subcellularLocations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubcellularLocationCommentImpl that = (SubcellularLocationCommentImpl) o;
        return Objects.equals(note, that.note)
                && Objects.equals(subcellularLocations, that.subcellularLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), note, subcellularLocations);
    }
}
