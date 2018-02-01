package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentsImpl implements Comments {
    private final List<Comment> comments;
    public CommentsImpl(List<Comment> comments){
        if ((comments == null) || comments.isEmpty()) {
            this.comments = Collections.emptyList();
        } else {
            this.comments = Collections.unmodifiableList(comments);
        }
    }
    @Override
    public List<Comment> getAllComments() {
        return comments;
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T extends Comment> List<T> getComments(CommentType type) {
        List< Comment> typedComments = new ArrayList<>();
        for(Comment comment: comments){
            if(comment.getCommentType() == type){
                typedComments.add(comment);
            }
        }
        return (List<T>) typedComments;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
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
        CommentsImpl other = (CommentsImpl) obj;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        return true;
    }

}
