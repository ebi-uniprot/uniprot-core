package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

public final class RnaEditingCommentBuilder implements CommentBuilder<RnaEditingCommentBuilder, RnaEditingComment> {
    private RnaEditingLocationType locationType;
    private List<RnaEdPosition> positions = new ArrayList<>();
    private Note note;

    public RnaEditingComment build() {
        return new RnaEditingCommentImpl(this);
    }

    @Override
    public RnaEditingCommentBuilder from(RnaEditingComment instance) {
        positions.clear();
        return this
                .positions(instance.getPositions())
                .locationType(instance.getLocationType());
    }

    public RnaEditingCommentBuilder locationType(RnaEditingLocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public RnaEditingCommentBuilder positions(List<RnaEdPosition> positions) {
        this.positions = nonNullList(positions);
        return this;
    }

    public RnaEditingCommentBuilder addPosition(RnaEdPosition position) {
        nonNullAdd(position, this.positions);
        return this;
    }

    public RnaEditingCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public RnaEditingLocationType getLocationType() {
        return locationType;
    }

    public List<RnaEdPosition> getPositions() {
        return positions;
    }

    public Note getNote() {
        return note;
    }
}
