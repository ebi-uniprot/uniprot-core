package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.SubcellularLocation;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;

public final class SubcellularLocationCommentBuilder
        implements CommentBuilder<SubcellularLocationComment> {
    private String molecule;
    private Note note;
    private List<SubcellularLocation> subcellularLocations = new ArrayList<>();

    public @Nonnull SubcellularLocationComment build() {
        return new SubcellularLocationCommentImpl(molecule, subcellularLocations, note);
    }

    public static @Nonnull SubcellularLocationCommentBuilder from(
            @Nonnull SubcellularLocationComment instance) {
        return new SubcellularLocationCommentBuilder()
                .molecule(instance.getMolecule())
                .subcellularLocationsSet(instance.getSubcellularLocations())
                .note(instance.getNote());
    }

    public @Nonnull SubcellularLocationCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull SubcellularLocationCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public @Nonnull SubcellularLocationCommentBuilder subcellularLocationsSet(
            List<SubcellularLocation> subcellularLocations) {
        this.subcellularLocations = modifiableList(subcellularLocations);
        return this;
    }

    public @Nonnull SubcellularLocationCommentBuilder subcellularLocationsAdd(
            SubcellularLocation subcellularLocation) {
        addOrIgnoreNull(subcellularLocation, this.subcellularLocations);
        return this;
    }
}
