package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;
import org.uniprot.core.uniprot.comment.impl.SubcellularLocationCommentImpl;

import javax.annotation.Nonnull;

public final class SubcellularLocationCommentBuilder
        implements CommentBuilder<SubcellularLocationCommentBuilder, SubcellularLocationComment> {
    private String molecule;
    private Note note;
    private List<SubcellularLocation> subcellularLocations = new ArrayList<>();

    public @Nonnull SubcellularLocationComment build() {
        return new SubcellularLocationCommentImpl(molecule, subcellularLocations, note);
    }

    @Override
    public @Nonnull SubcellularLocationCommentBuilder from(@Nonnull SubcellularLocationComment instance) {
        subcellularLocations.clear();
        return this.molecule(instance.getMolecule())
                .subcellularLocations(instance.getSubcellularLocations())
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

    public @Nonnull SubcellularLocationCommentBuilder subcellularLocations(
            List<SubcellularLocation> subcellularLocations) {
        this.subcellularLocations = modifiableList(subcellularLocations);
        return this;
    }

    public @Nonnull SubcellularLocationCommentBuilder addSubcellularLocation(
            SubcellularLocation subcellularLocation) {
        addOrIgnoreNull(subcellularLocation, this.subcellularLocations);
        return this;
    }
}
