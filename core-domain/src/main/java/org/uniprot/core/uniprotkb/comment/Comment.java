package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

public interface Comment extends Serializable {
    CommentType getCommentType();
}
