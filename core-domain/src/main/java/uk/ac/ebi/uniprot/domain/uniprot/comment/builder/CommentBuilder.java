package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;

public interface CommentBuilder<B extends Builder<B, T>, T extends Comment> extends Builder<B, T> {

}
