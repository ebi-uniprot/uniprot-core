package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InteractionCommentImpl extends CommentImpl implements InteractionComment {
    private List<Interaction> interactions;

    private InteractionCommentImpl(){
        super(CommentType.INTERACTION);
        this.interactions = Collections.emptyList();
    }

    public InteractionCommentImpl(List<Interaction> interactions) {
        super(CommentType.INTERACTION);
        if((interactions ==null) || interactions.isEmpty()){
            this.interactions = Collections.emptyList();
        }else{
            this.interactions =Collections.unmodifiableList(interactions);
        }
    }

    @Override
    public List<Interaction> getInteractions() {
       return interactions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((interactions == null) ? 0 : interactions.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        InteractionCommentImpl other = (InteractionCommentImpl) obj;
        if (interactions == null) {
            if (other.interactions != null)
                return false;
        } else if (!interactions.equals(other.interactions))
            return false;
        return true;
    }

}
