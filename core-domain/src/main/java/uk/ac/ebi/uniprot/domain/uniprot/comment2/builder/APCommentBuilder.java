package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.AlternativeProductsCommentImpl;

import java.util.ArrayList;
import java.util.List;

public final class APCommentBuilder implements CommentBuilder<APCommentBuilder, AlternativeProductsComment> {
    private List<APEventType> events = new ArrayList<>();
    private List<APIsoform> isoforms = new ArrayList<>();
    private Note note;

    public AlternativeProductsComment build() {
        return new AlternativeProductsCommentImpl(this);
    }

    @Override
    public APCommentBuilder from(AlternativeProductsComment instance) {
        return new APCommentBuilder()
                .events(instance.getEvents())
                .isoforms(instance.getIsoforms())
                .note(instance.getNote());
    }

    public APCommentBuilder events(List<APEventType> events) {
        this.events.addAll(events);
        return this;
    }

    public APCommentBuilder addEvent(APEventType event) {
        this.events.add(event);
        return this;
    }

    public APCommentBuilder isoforms(List<APIsoform> isoforms) {
        this.isoforms.addAll(isoforms);
        return this;
    }

    public APCommentBuilder addIsoform(APIsoform isoform) {
        this.isoforms.add(isoform);
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
