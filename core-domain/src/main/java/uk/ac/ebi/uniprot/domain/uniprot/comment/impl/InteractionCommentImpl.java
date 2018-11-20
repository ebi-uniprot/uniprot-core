package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InteractionCommentImpl extends CommentImpl implements InteractionComment {
    private final List<Interaction> interactions;
    @JsonCreator
    public InteractionCommentImpl(@JsonProperty("interactions") List<Interaction> interactions) {
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
