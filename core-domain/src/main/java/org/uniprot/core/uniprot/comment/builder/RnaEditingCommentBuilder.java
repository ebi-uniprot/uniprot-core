package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;
import org.uniprot.core.uniprot.comment.impl.RnaEditingCommentImpl;

public final class RnaEditingCommentBuilder
        implements CommentBuilder<RnaEditingCommentBuilder, RnaEditingComment> {
    private RnaEditingLocationType locationType;
    private List<RnaEdPosition> positions = new ArrayList<>();
    private Note note;

    public RnaEditingComment build() {
        return new RnaEditingCommentImpl(locationType, positions, note);
    }

    @Override
    public RnaEditingCommentBuilder from(RnaEditingComment instance) {
        positions.clear();
        return this.positions(instance.getPositions()).locationType(instance.getLocationType());
    }

    public RnaEditingCommentBuilder locationType(RnaEditingLocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public RnaEditingCommentBuilder positions(List<RnaEdPosition> positions) {
        this.positions = modifiableList(positions);
        return this;
    }

    public RnaEditingCommentBuilder addPosition(RnaEdPosition position) {
        addOrIgnoreNull(position, this.positions);
        return this;
    }

    public RnaEditingCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
