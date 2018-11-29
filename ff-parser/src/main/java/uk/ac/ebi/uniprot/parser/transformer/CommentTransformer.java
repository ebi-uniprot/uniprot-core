package uk.ac.ebi.uniprot.parser.transformer;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public interface CommentTransformer<T extends Comment> {
	T transform(String annotation);
	T transform(CommentType type, String annotation);
}
