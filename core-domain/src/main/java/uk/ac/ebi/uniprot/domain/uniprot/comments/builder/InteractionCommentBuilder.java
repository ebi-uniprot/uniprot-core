package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.InteractionCommentImpl;

import java.util.List;

public class InteractionCommentBuilder implements CommentBuilder<InteractionComment> {
    public static InteractionCommentBuilder newInstance(){
        return new InteractionCommentBuilder();
    }
    public static InteractionBuilder newInteractionBuilder(){
        return InteractionBuilder.newInstance();
    }
    
    private List<Interaction> interactions;
    public InteractionCommentBuilder setInteractions(List<Interaction> interactions){
        this.interactions = interactions;
        return this;
    }
    
    @Override
    public InteractionComment build() {
        return new InteractionCommentImpl(interactions);
    }

}
