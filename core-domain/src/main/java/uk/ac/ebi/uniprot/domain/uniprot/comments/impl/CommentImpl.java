package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;

public abstract class CommentImpl implements Comment {

    private final CommentType type;

    public CommentImpl(CommentType type) {
        this.type = type;
      
    }

    @Override
    public CommentType getCommentType() {
       return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommentImpl other = (CommentImpl) obj;
        if (type != other.type)
            return false;
        return true;
    }

}
