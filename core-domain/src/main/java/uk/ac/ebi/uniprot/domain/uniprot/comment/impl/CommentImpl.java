package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

import java.util.Objects;

public abstract class CommentImpl implements Comment {
    private CommentType commentType;

    public CommentImpl() {
    }

    public CommentImpl(CommentType commentType) {
        this.commentType = commentType;
    }

    @Override
    public CommentType getCommentType() {
        return commentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentImpl comment = (CommentImpl) o;
        return commentType == comment.commentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentType);
    }
}
