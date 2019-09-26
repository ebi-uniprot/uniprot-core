package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.APEventType;
import org.uniprot.core.uniprot.comment.APIsoform;
import org.uniprot.core.uniprot.comment.AlternativeProductsComment;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.AlternativeProductsCommentImpl;

public final class APCommentBuilder implements CommentBuilder<APCommentBuilder, AlternativeProductsComment> {
    private List<APEventType> events = new ArrayList<>();
    private List<APIsoform> isoforms = new ArrayList<>();
    private Note note;

    public AlternativeProductsComment build() {
        return new AlternativeProductsCommentImpl(events, isoforms, note);
    }

    @Override
    public APCommentBuilder from(AlternativeProductsComment instance) {
        events.clear();
        isoforms.clear();
        return this
                .events(instance.getEvents())
                .isoforms(instance.getIsoforms())
                .note(instance.getNote());
    }

    public APCommentBuilder events(List<APEventType> events) {
        this.events = modifiableList(events);
        return this;
    }

    public APCommentBuilder addEvent(APEventType event) {
        addOrIgnoreNull(event, this.events);
        return this;
    }

    public APCommentBuilder isoforms(List<APIsoform> isoforms) {
        this.isoforms = modifiableList(isoforms);
        return this;
    }

    public APCommentBuilder addIsoform(APIsoform isoform) {
        addOrIgnoreNull(isoform, this.isoforms);
        return this;
    }

    public APCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public List<APEventType> getEvents() {
        return events;
    }

    public List<APIsoform> getIsoforms() {
        return isoforms;
    }

    public Note getNote() {
        return note;
    }
}
