package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Comment;

public interface CommentBuilder<B extends Builder2<B, T>, T extends Comment> extends Builder2<B, T> {

}
