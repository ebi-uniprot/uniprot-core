package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationCommentImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

public final class SubcellularLocationCommentBuilder implements CommentBuilder<SubcellularLocationCommentBuilder, SubcellularLocationComment> {
    private String molecule;
    private Note note;
    private List<SubcellularLocation> subcellularLocations = new ArrayList<>();

    public SubcellularLocationComment build() {
        return new SubcellularLocationCommentImpl(molecule, subcellularLocations, note);
    }

    @Override
    public SubcellularLocationCommentBuilder from(SubcellularLocationComment instance) {
        subcellularLocations.clear();
        return this
                .molecule(instance.getMolecule())
                .subcellularLocations(instance.getSubcellularLocations())
                .note(instance.getNote());
    }

    public SubcellularLocationCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public SubcellularLocationCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public SubcellularLocationCommentBuilder subcellularLocations(List<SubcellularLocation> subcellularLocations) {
        this.subcellularLocations = nonNullList(subcellularLocations);
        return this;
    }

    public SubcellularLocationCommentBuilder addSubcellularLocation(SubcellularLocation subcellularLocation) {
        nonNullAdd(subcellularLocation, this.subcellularLocations);
        return this;
    }
}
