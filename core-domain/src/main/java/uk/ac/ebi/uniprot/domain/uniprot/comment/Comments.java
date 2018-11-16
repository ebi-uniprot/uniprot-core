package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

public interface Comments {
  //  List<CofactorComment> getCofactorComment();   
    List<Comment> getAllComments();
    <T extends Comment> List<T> getComments(CommentType type);
}
