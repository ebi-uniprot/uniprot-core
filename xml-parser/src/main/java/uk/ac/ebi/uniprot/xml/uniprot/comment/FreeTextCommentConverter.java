package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.EvidencedValueConverter;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.comment.builder.FreeTextCommentBuilder;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class FreeTextCommentConverter implements CommentConverter<FreeTextComment> {
    private final ObjectFactory xmlUniprotFactory;
    private final EvidencedValueConverter eviValueConverter;

    public FreeTextCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public FreeTextCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.eviValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
    }

    @Override
    public FreeTextComment fromXml(CommentType xmlObj) {
        if ((xmlObj == null) || xmlObj.getText().isEmpty())
            return null;
        org.uniprot.core.uniprot.comment.CommentType type =
                org.uniprot.core.uniprot.comment.CommentType.typeOf(xmlObj.getType());
        List<EvidencedValue> texts =
                xmlObj.getText().stream()
                        .map(eviValueConverter::fromXml)
                        .collect(Collectors.toList());

        return new FreeTextCommentBuilder().commentType(type).texts(texts).build();
    }

    @Override
    public CommentType toXml(FreeTextComment uniObj) {
        if (uniObj == null)
            return null;

        CommentType xmlObj = xmlUniprotFactory.createCommentType();
        // type
        xmlObj.setType(uniObj.getCommentType().toXmlDisplayName());
        uniObj.getTexts().forEach(val -> xmlObj.getText().add(eviValueConverter.toXml(val)));
        return xmlObj;
    }


}
