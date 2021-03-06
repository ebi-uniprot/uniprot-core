package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;

public class InteractionCommentBuilder implements CommentBuilder<InteractionComment> {
    private List<Interaction> interactions = new ArrayList<>();

    public @Nonnull InteractionCommentBuilder interactionsSet(List<Interaction> interactions) {
        this.interactions = modifiableList(interactions);
        return this;
    }

    public @Nonnull InteractionCommentBuilder interactionsAdd(Interaction interaction) {
        addOrIgnoreNull(interaction, this.interactions);
        return this;
    }

    @Override
    public @Nonnull InteractionComment build() {
        return new InteractionCommentImpl(interactions);
    }

    public static @Nonnull InteractionCommentBuilder from(@Nonnull InteractionComment instance) {
        return new InteractionCommentBuilder().interactionsSet(instance.getInteractions());
    }
}
