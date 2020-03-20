package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;

public interface CommentConverter<T extends Comment> extends Converter<CommentType, T> {}
