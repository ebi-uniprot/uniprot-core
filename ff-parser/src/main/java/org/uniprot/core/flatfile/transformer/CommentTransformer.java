package org.uniprot.core.flatfile.transformer;

import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;

public interface CommentTransformer<T extends Comment> {
    T transform(String annotation);

    T transform(CommentType type, String annotation);
}
