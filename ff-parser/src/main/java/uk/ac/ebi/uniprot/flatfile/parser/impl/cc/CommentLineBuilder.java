package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import org.uniprot.core.uniprot.comment.Comment;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLineBuilder;

public interface CommentLineBuilder<T extends Comment> extends FFLineBuilder<T> {
	String buildString(T f, boolean showEvidence, boolean includeCommentType); 
}
