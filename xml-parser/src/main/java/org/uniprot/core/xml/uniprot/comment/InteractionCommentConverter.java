package org.uniprot.core.xml.uniprot.comment;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.impl.InteractionCommentBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class InteractionCommentConverter
        implements Converter<List<CommentType>, InteractionComment> {
    private final InteractionConverter interactionConverter;

    public InteractionCommentConverter() {
        this(new ObjectFactory());
    }

    public InteractionCommentConverter(ObjectFactory xmlUniprotFactory) {
        this.interactionConverter = new InteractionConverter(xmlUniprotFactory);
    }

    @Override
    public InteractionComment fromXml(List<CommentType> xmlObj) {
        if ((xmlObj == null) || xmlObj.isEmpty()) return null;
        InteractionCommentBuilder builder = new InteractionCommentBuilder();
        return builder.interactionsSet(
                        xmlObj.stream()
                                .map(interactionConverter::fromXml)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<CommentType> toXml(InteractionComment uniObj) {
        if (uniObj == null) return null;
        return uniObj.getInteractions().stream()
                .map(interactionConverter::toXml)
                .collect(Collectors.toList());
    }
}
