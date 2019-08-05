package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.impl.InteractionCommentImpl;

public class InteractionCommentBuilder implements CommentBuilder<InteractionCommentBuilder, InteractionComment> {
    private List<Interaction> interactions = new ArrayList<>();

    public InteractionCommentBuilder interactions(List<Interaction> interactions) {
        this.interactions = nonNullList(interactions);
        return this;
    }

    public InteractionCommentBuilder addInteraction(Interaction interaction) {
        nonNullAdd(interaction, this.interactions);
        return this;
    }

    @Override
    public InteractionComment build() {
        return new InteractionCommentImpl(interactions);
    }

    @Override
    public InteractionCommentBuilder from(InteractionComment instance) {
        interactions.clear();
        return this
                .interactions(instance.getInteractions());
    }
}
