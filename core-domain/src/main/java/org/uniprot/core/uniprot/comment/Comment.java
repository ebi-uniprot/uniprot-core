package org.uniprot.core.uniprot.comment;

import java.io.Serializable;

public interface Comment extends Serializable {
    CommentType getCommentType();
    
}
