package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.util.Utils;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentsImpl implements Comments {
    private final List<Comment> comments;
	@JsonCreator
    public CommentsImpl(@JsonProperty("comments")  List<Comment> comments){
		this.comments = Utils.unmodifierList(comments);
    }
    @Override
    public List<Comment> getComments() {
        return comments;
    }
	@JsonIgnore
    @SuppressWarnings("unchecked")
	@Override
    public <T extends Comment> List<T> getCommentByType(CommentType type) {
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
