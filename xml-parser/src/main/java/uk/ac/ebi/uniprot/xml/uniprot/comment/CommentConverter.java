package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.uniprot.core.uniprot.comment.Comment;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
public interface CommentConverter<T extends Comment> extends Converter<CommentType, T>{

}
