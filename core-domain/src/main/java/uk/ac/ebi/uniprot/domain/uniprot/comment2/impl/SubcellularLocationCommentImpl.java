package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.SubcellularLocationCommentBuilder;

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

    public SubcellularLocationCommentImpl(SubcellularLocationCommentBuilder builder) {
        super(CommentType.SUBCELLULAR_LOCATION);
        if (builder.getMolecule() == null || builder.getMolecule().isEmpty()) {
            this.molecule = "";
        } else {
            this.molecule = builder.getMolecule();
        }

        if ((builder.getSubcellularLocations() == null) || builder.getSubcellularLocations().isEmpty()) {
            this.subcellularLocations = Collections.emptyList();
        } else {
            this.subcellularLocations = Collections.unmodifiableList(builder.getSubcellularLocations());
        }
        this.note = builder.getNote();
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
