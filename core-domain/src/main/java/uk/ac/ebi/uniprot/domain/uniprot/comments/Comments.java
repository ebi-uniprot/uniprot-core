package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface Comments {
    List<CofactorComment> getCofactorComment();
    
    <T extends Comment>  List<T> getAllComments();
}
