package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;
import org.uniprot.core.uniprot.comment.impl.SubcellularLocationCommentImpl;

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