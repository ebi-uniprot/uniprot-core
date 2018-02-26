package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocationComment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SubcellularLocationCommentImpl extends CommentImpl implements SubcellularLocationComment {
    private final Optional<String> molecule;
    private final List<SubcellularLocation>  subcellularLocations;
    private final Optional<CommentNote> note;
    public SubcellularLocationCommentImpl(String molecule, 
            List<SubcellularLocation>  subcellularLocations, CommentNote note) {
        super(CommentType.SUBCELLULAR_LOCATION);
        this.molecule = ((molecule ==null )|| molecule.isEmpty())? Optional.empty() : Optional.of(molecule);
        if ((subcellularLocations == null) || subcellularLocations.isEmpty()) {
            this.subcellularLocations = Collections.emptyList();
        } else {
            this.subcellularLocations = Collections.unmodifiableList(subcellularLocations);
        }
        this.note = (note ==null )? Optional.empty() : Optional.of(note);
    }

    @Override
    public Optional<String> getMolecule() {
       return molecule;
    }

    @Override
    public Optional<CommentNote> getNote() {
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
