package org.uniprot.core.xml.uniprot.comment;

import java.util.List;

import org.uniprot.core.uniprotkb.comment.WebResourceComment;
import org.uniprot.core.uniprotkb.comment.impl.WebResourceCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

import com.google.common.base.Strings;

public class WRCommentConverter implements CommentConverter<WebResourceComment> {
    private final ObjectFactory xmlUniprotFactory;

    public WRCommentConverter() {
        this(new ObjectFactory());
    }

    public WRCommentConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public WebResourceComment fromXml(CommentType xmlObject) {
        if (xmlObject == null) return null;
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();

        // Molecule
        if (xmlObject.getMolecule() != null) {
            builder.molecule(xmlObject.getMolecule().getValue());
        }

        if (xmlObject.getName() != null) {
            builder.resourceName(xmlObject.getName());
        }

        List<CommentType.Link> listLinks = xmlObject.getLink();

        for (CommentType.Link linkXML : listLinks) {
            String link = linkXML.getUri();
            builder.resourceUrl(link);
        }
        // Value
        if (!xmlObject.getText().isEmpty()) {
            String text = xmlObject.getText().get(0).getValue();
            builder.note(text);
        }

        return builder.build();
    }

    @Override
    public CommentType toXml(WebResourceComment uniObj) {
        if (uniObj == null) return null;
        CommentType commentType = xmlUniprotFactory.createCommentType();
        commentType.setType(WebResourceComment.ONLINE_INFORMATION_XMLTAG);

        if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(uniObj.getMolecule());
            commentType.setMolecule(mol);
        }

        if (!Strings.isNullOrEmpty(uniObj.getResourceName())) {
            commentType.setName(uniObj.getResourceName());
        }
        if (!Strings.isNullOrEmpty(uniObj.getResourceUrl())) {
            CommentType.Link xmlLink = xmlUniprotFactory.createCommentTypeLink();
            xmlLink.setUri(uniObj.getResourceUrl());
            commentType.getLink().add(xmlLink);
        }
        if (!Strings.isNullOrEmpty(uniObj.getNote())) {
            EvidencedStringType evidencedStringType = xmlUniprotFactory.createEvidencedStringType();
            evidencedStringType.setValue(uniObj.getNote());
            commentType.getText().add(evidencedStringType);
        }
        return commentType;
    }
}
