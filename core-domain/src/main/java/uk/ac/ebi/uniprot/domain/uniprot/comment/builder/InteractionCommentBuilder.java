package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionCommentImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

public class InteractionCommentBuilder implements CommentBuilder<InteractionCommentBuilder, InteractionComment> {
    private List<Interaction> interactions = new ArrayList<>();

    public InteractionCommentBuilder interactions(List<Interaction> interactions) {
        nonNullAddAll(interactions, this.interactions);
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
