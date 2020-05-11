package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.RnaEdPosition;
import org.uniprot.core.uniprotkb.comment.RnaEditingComment;
import org.uniprot.core.uniprotkb.comment.RnaEditingLocationType;

public final class RnaEditingCommentBuilder implements CommentBuilder<RnaEditingComment> {
    private String molecule;
    private RnaEditingLocationType locationType;
    private List<RnaEdPosition> positions = new ArrayList<>();
    private Note note;

    public @Nonnull RnaEditingComment build() {
        return new RnaEditingCommentImpl(molecule, locationType, positions, note);
    }

    public static @Nonnull RnaEditingCommentBuilder from(@Nonnull RnaEditingComment instance) {
        return new RnaEditingCommentBuilder()
                .positionsSet(instance.getPositions())
                .locationType(instance.getLocationType())
                .molecule(instance.getMolecule())
                .note(instance.getNote());
    }

    public @Nonnull RnaEditingCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull RnaEditingCommentBuilder locationType(RnaEditingLocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public @Nonnull RnaEditingCommentBuilder positionsSet(List<RnaEdPosition> positions) {
        this.positions = modifiableList(positions);
        return this;
    }

    public @Nonnull RnaEditingCommentBuilder positionsAdd(RnaEdPosition position) {
        addOrIgnoreNull(position, this.positions);
        return this;
    }

    public @Nonnull RnaEditingCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
