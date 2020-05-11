package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;

public abstract class CommentImpl implements Comment {
    private static final long serialVersionUID = 4940248496275660209L;
    private CommentType commentType;

    CommentImpl(CommentType commentType) {
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
