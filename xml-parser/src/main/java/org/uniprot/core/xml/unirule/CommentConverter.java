package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.comment.CommentConverterFactory;

public class CommentConverter implements Converter<CommentType, Comment> {
    private final EvidenceIndexMapper evidenceIndexMapper;
    private final ObjectFactory xmlUniprotFactory;
    private final String subcellFile;

    public CommentConverter() {
        this("");
    }

    public CommentConverter(String subcellFile) {
        this.evidenceIndexMapper = new EvidenceIndexMapper();
        this.xmlUniprotFactory = new ObjectFactory();
        this.subcellFile = subcellFile;
    }

    @Override
    public Comment fromXml(CommentType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        org.uniprot.core.uniprotkb.comment.CommentType type =
                org.uniprot.core.uniprotkb.comment.CommentType.typeOf(xmlObj.getType());
        Comment comment =
                CommentConverterFactory.INSTANCE
                        .createCommentConverter(
                                type, evidenceIndexMapper, xmlUniprotFactory, this.subcellFile)
                        .fromXml(xmlObj);

        return comment;
    }

    @Override
    public CommentType toXml(Comment uniObj) {
        if (Objects.isNull(uniObj)) return null;

        CommentType commentType =
                CommentConverterFactory.INSTANCE
                        .createCommentConverter(
                                uniObj.getCommentType(),
                                evidenceIndexMapper,
                                xmlUniprotFactory,
                                this.subcellFile)
                        .toXml(uniObj);

        return commentType;
    }
}
