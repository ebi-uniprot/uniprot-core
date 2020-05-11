package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.comment.APEventType;
import org.uniprot.core.uniprotkb.comment.APIsoform;
import org.uniprot.core.uniprotkb.comment.AlternativeProductsComment;
import org.uniprot.core.uniprotkb.comment.Note;

public final class AlternativeProductsCommentBuilder
        implements CommentBuilder<AlternativeProductsComment> {
    private List<APEventType> events = new ArrayList<>();
    private List<APIsoform> isoforms = new ArrayList<>();
    private Note note;

    public @Nonnull AlternativeProductsComment build() {
        return new AlternativeProductsCommentImpl(events, isoforms, note);
    }

    public static @Nonnull AlternativeProductsCommentBuilder from(
            @Nonnull AlternativeProductsComment instance) {
        return new AlternativeProductsCommentBuilder()
                .eventsSet(instance.getEvents())
                .isoformsSet(instance.getIsoforms())
                .note(instance.getNote());
    }

    public @Nonnull AlternativeProductsCommentBuilder eventsSet(List<APEventType> events) {
        this.events = modifiableList(events);
        return this;
    }

    public @Nonnull AlternativeProductsCommentBuilder eventsAdd(APEventType event) {
        addOrIgnoreNull(event, this.events);
        return this;
    }

    public @Nonnull AlternativeProductsCommentBuilder isoformsSet(List<APIsoform> isoforms) {
        this.isoforms = modifiableList(isoforms);
        return this;
    }

    public @Nonnull AlternativeProductsCommentBuilder isoformsAdd(APIsoform isoform) {
        addOrIgnoreNull(isoform, this.isoforms);
        return this;
    }

    public @Nonnull AlternativeProductsCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
