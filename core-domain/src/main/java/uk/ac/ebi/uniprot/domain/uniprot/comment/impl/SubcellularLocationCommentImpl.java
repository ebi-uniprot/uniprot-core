package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SubcellularLocationCommentImpl extends CommentImpl implements SubcellularLocationComment {
    private String molecule;
    private Note note;
    private List<SubcellularLocation> subcellularLocations;

    private SubcellularLocationCommentImpl() {
        super(CommentType.SUBCELLULAR_LOCATION);
        this.molecule = "";
        this.subcellularLocations = Collections.emptyList();
    }

    public SubcellularLocationCommentImpl(String molecule,
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
    public boolean hasMolecule() {
        return Utils.notEmpty(this.molecule);
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean hasSubcellularLocations() {
        return Utils.notEmpty(this.subcellularLocations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubcellularLocationCommentImpl that = (SubcellularLocationCommentImpl) o;
        return Objects.equals(molecule, that.molecule) &&
                Objects.equals(note, that.note) &&
                Objects.equals(subcellularLocations, that.subcellularLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), molecule, note, subcellularLocations);
    }
}
