package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.io.Serializable;

public interface Comment extends Serializable {
    public CommentType getCommentType();

}
