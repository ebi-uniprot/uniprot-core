package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.APEventType;
import org.uniprot.core.uniprot.comment.APIsoform;
import org.uniprot.core.uniprot.comment.AlternativeProductsComment;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.AlternativeProductsCommentImpl;

public final class APCommentBuilder implements CommentBuilder<AlternativeProductsComment> {
    private List<APEventType> events = new ArrayList<>();
    private List<APIsoform> isoforms = new ArrayList<>();
    private Note note;

    public @Nonnull AlternativeProductsComment build() {
        return new AlternativeProductsCommentImpl(events, isoforms, note);
    }

    public static @Nonnull APCommentBuilder from(@Nonnull AlternativeProductsComment instance) {
        return new APCommentBuilder().events(instance.getEvents())
                .isoforms(instance.getIsoforms())
                .note(instance.getNote());
    }

    public @Nonnull APCommentBuilder events(List<APEventType> events) {
        this.events = modifiableList(events);
        return this;
    }

    public @Nonnull APCommentBuilder addEvent(APEventType event) {
        addOrIgnoreNull(event, this.events);
        return this;
    }

    public @Nonnull APCommentBuilder isoforms(List<APIsoform> isoforms) {
        this.isoforms = modifiableList(isoforms);
        return this;
    }

    public @Nonnull APCommentBuilder addIsoform(APIsoform isoform) {
        addOrIgnoreNull(isoform, this.isoforms);
        return this;
    }

    public @Nonnull APCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
