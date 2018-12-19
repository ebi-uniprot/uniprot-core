package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionCommentImpl;

import java.util.List;

public class InteractionCommentBuilder implements CommentBuilder<InteractionComment> {
    private List<Interaction> interactions;

    public static InteractionCommentBuilder newInstance() {
        return new InteractionCommentBuilder();
    }

    public static InteractionBuilder newInteractionBuilder() {
        return InteractionBuilder.newInstance();
    }

    public InteractionCommentBuilder interactions(List<Interaction> interactions) {
        this.interactions = interactions;
        return this;
    }

    @Override
    public InteractionComment build() {
        return new InteractionCommentImpl(interactions);
    }

}
