package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;
import org.uniprot.core.util.Utils;

public class SubcellularLocationCommentImpl extends CommentImpl implements SubcellularLocationComment {
    private static final long serialVersionUID = 955858123969540661L;
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
        return Utils.notNullOrEmpty(this.molecule);
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
        return Objects.equals(molecule, that.molecule) &&
                Objects.equals(note, that.note) &&
                Objects.equals(subcellularLocations, that.subcellularLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), molecule, note, subcellularLocations);
    }
}
