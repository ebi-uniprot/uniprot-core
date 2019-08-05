package org.uniprot.core.flatfile.parser.impl.cc;

import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.uniprot.comment.Comment;

public interface CommentLineBuilder<T extends Comment> extends FFLineBuilder<T> {
	String buildString(T f, boolean showEvidence, boolean includeCommentType); 
}
