package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public abstract class CommentImpl implements Comment {

    private CommentType commentType;

    private CommentImpl(){

    }

    public CommentImpl(CommentType commentType) {
        this.commentType = commentType;
      
    }

    @Override
    public CommentType getCommentType() {
       return commentType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commentType == null) ? 0 : commentType.hashCode());
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
        if (commentType != other.commentType)
            return false;
        return true;
    }

}
