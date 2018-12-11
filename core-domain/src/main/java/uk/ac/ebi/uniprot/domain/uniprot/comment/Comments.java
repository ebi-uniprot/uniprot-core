package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

public interface Comments {
    List<Comment> getComments();
    <T extends Comment> List<T> getCommentByType(CommentType type);
}
