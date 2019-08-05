package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.AlternativeProductsCommentImpl;

import static org.uniprot.core.common.Utils.nonNullAdd;
import static org.uniprot.core.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

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
        this.events = nonNullList(events);
        return this;
    }

    public APCommentBuilder addEvent(APEventType event) {
        nonNullAdd(event, this.events);
        return this;
    }

    public APCommentBuilder isoforms(List<APIsoform> isoforms) {
        this.isoforms = nonNullList(isoforms);
        return this;
    }

    public APCommentBuilder addIsoform(APIsoform isoform) {
        nonNullAdd(isoform, this.isoforms);
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
