package org.uniprot.core.xml.uniprot.comment;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.comment.impl.FreeTextCommentBuilder;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedValueConverter;

import com.google.common.base.Strings;

public class FreeTextCommentConverter implements CommentConverter<FreeTextComment> {
    private final ObjectFactory xmlUniprotFactory;
    private final EvidencedValueConverter eviValueConverter;

    public FreeTextCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public FreeTextCommentConverter(
            EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.eviValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
    }

    @Override
    public FreeTextComment fromXml(CommentType xmlObj) {
        if ((xmlObj == null) || xmlObj.getText().isEmpty()) return null;
        org.uniprot.core.uniprot.comment.CommentType type =
                org.uniprot.core.uniprot.comment.CommentType.typeOf(xmlObj.getType());
        List<EvidencedValue> texts =
                xmlObj.getText().stream()
                        .map(eviValueConverter::fromXml)
                        .collect(Collectors.toList());

        FreeTextCommentBuilder builder = new FreeTextCommentBuilder();
        // Molecule
        if (xmlObj.getMolecule() != null) {
            builder.molecule(xmlObj.getMolecule().getValue());
        }
        return builder.commentType(type).textsSet(texts).build();
    }

    @Override
    public CommentType toXml(FreeTextComment uniObj) {
        if (uniObj == null) return null;

        CommentType xmlObj = xmlUniprotFactory.createCommentType();
        // type
        xmlObj.setType(uniObj.getCommentType().toXmlDisplayName());

        if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(uniObj.getMolecule());
            xmlObj.setMolecule(mol);
        }

        uniObj.getTexts().forEach(val -> xmlObj.getText().add(eviValueConverter.toXml(val)));
        return xmlObj;
    }
}
