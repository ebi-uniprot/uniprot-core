package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.impl.InteractionCommentImpl;

public class InteractionCommentBuilder
        implements CommentBuilder<InteractionCommentBuilder, InteractionComment> {
    private List<Interaction> interactions = new ArrayList<>();

    public @Nonnull InteractionCommentBuilder interactions(List<Interaction> interactions) {
        this.interactions = modifiableList(interactions);
        return this;
    }

    public @Nonnull InteractionCommentBuilder addInteraction(Interaction interaction) {
        addOrIgnoreNull(interaction, this.interactions);
        return this;
    }

    @Override
    public @Nonnull InteractionComment build() {
        return new InteractionCommentImpl(interactions);
    }

    @Override
    public @Nonnull InteractionCommentBuilder from(@Nonnull InteractionComment instance) {
        interactions.clear();
        return this.interactions(instance.getInteractions());
    }
}
