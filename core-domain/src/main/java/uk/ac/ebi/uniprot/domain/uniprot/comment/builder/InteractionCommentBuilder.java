package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionCommentImpl;

import java.util.ArrayList;
import java.util.List;

public class InteractionCommentBuilder implements CommentBuilder<InteractionCommentBuilder, InteractionComment> {
    private List<Interaction> interactions = new ArrayList<>();

    public InteractionCommentBuilder interactions(List<Interaction> interactions) {
        this.interactions.addAll(interactions);
        return this;
    }

    public InteractionCommentBuilder addInteraction(Interaction interaction) {
        this.interactions.add(interaction);
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
