package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.Comment;

public interface CommentBuilder<B extends Builder<B, T>, T extends Comment> extends Builder<B, T> {

}
