package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;

import java.util.Collections;
import java.util.List;

public class SubcellularLocationCommentImpl extends CommentImpl implements SubcellularLocationComment {
    private String molecule;
    private List<SubcellularLocation> subcellularLocations;
    private Note note;

    private SubcellularLocationCommentImpl() {
        super(CommentType.SUBCELLULAR_LOCATION);
        this.molecule = "";
        this.subcellularLocations = Collections.emptyList();
    }

    public SubcellularLocationCommentImpl(
            String molecule,
            List<SubcellularLocation> subcellularLocations,
            Note note) {
        super(CommentType.SUBCELLULAR_LOCATION);
        if (molecule == null || molecule.isEmpty()) {
            this.molecule = "";
        } else {
            this.molecule = molecule;
        }

        if ((subcellularLocations == null) || subcellularLocations.isEmpty()) {
            this.subcellularLocations = Collections.emptyList();
        } else {
            this.subcellularLocations = Collections.unmodifiableList(subcellularLocations);
        }
        this.note = note;
    }

    @Override
    public String getMolecule() {
        return molecule;
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((molecule == null) ? 0 : molecule.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((subcellularLocations == null) ? 0 : subcellularLocations.hashCode());
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
        SubcellularLocationCommentImpl other = (SubcellularLocationCommentImpl) obj;
        if (molecule == null) {
            if (other.molecule != null)
                return false;
        } else if (!molecule.equals(other.molecule))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (subcellularLocations == null) {
            if (other.subcellularLocations != null)
                return false;
        } else if (!subcellularLocations.equals(other.subcellularLocations))
            return false;
        return true;
    }

}
