package org.uniprot.core.flatfile.transformer;

import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;

public interface CommentTransformer<T extends Comment> {
	T transform(String annotation);
	T transform(CommentType type, String annotation);
}
