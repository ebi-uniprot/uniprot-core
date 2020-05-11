package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.uniprotkb.comment.RedoxPotential;
import org.uniprot.core.uniprotkb.comment.impl.RedoxPotentialBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedValueConverter;

import java.util.stream.Collectors;

public class BPCPRedoxPotentialConverter
        implements Converter<CommentType.RedoxPotential, RedoxPotential> {
    private final ObjectFactory xmlUniprotFactory;
    private final EvidencedValueConverter evValueConverter;

    public BPCPRedoxPotentialConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public BPCPRedoxPotentialConverter(
            EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
    }

    @Override
    public RedoxPotential fromXml(CommentType.RedoxPotential xmlObj) {
        if (xmlObj == null) return null;
        return new RedoxPotentialBuilder(
                        xmlObj.getText().stream()
                                .map(evValueConverter::fromXml)
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CommentType.RedoxPotential toXml(RedoxPotential uniObj) {
        if (uniObj == null) return null;
        CommentType.RedoxPotential redxoXml = xmlUniprotFactory.createCommentTypeRedoxPotential();
        uniObj.getTexts().forEach(val -> redxoXml.getText().add(evValueConverter.toXml(val)));

        return redxoXml;
    }
}
