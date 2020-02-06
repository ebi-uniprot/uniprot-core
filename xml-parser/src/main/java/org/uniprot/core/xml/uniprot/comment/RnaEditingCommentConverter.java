package org.uniprot.core.xml.uniprot.comment;

import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.RnaEditingCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedValueConverter;

import com.google.common.base.Strings;

public class RnaEditingCommentConverter implements CommentConverter<RnaEditingComment> {

    private final ObjectFactory xmlUniprotFactory;
    private final RnaEdPositionConverter positionConverter;
    private final EvidencedValueConverter evValueConverter;

    public RnaEditingCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public RnaEditingCommentConverter(
            EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.positionConverter = new RnaEdPositionConverter(evRefMapper, xmlUniprotFactory);
        evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
    }

    @Override
    public RnaEditingComment fromXml(CommentType xmlObj) {
        if (xmlObj == null) return null;
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();

        // Molecule
        if (xmlObj.getMolecule() != null) {
            builder.molecule(xmlObj.getMolecule().getValue());
        }

        if (xmlObj.getLocation() != null && !xmlObj.getLocation().isEmpty()) {
            builder.positionsSet(
                    xmlObj.getLocation().stream()
                            .map(positionConverter::fromXml)
                            .collect(Collectors.toList()));
        }
        if (xmlObj.getLocationType() != null) {
            builder.locationType(RnaEditingLocationType.getType(xmlObj.getLocationType()));
        }

        // Note
        if (!xmlObj.getText().isEmpty()) {
            Note note =
                    new NoteBuilder(
                                    xmlObj.getText().stream()
                                            .map(evValueConverter::fromXml)
                                            .collect(Collectors.toList()))
                            .build();
            builder.note(note);
        }
        return builder.build();
    }

    @Override
    public CommentType toXml(RnaEditingComment uniObj) {
        if (uniObj == null) return null;

        CommentType commentXML = xmlUniprotFactory.createCommentType();
        commentXML.setType("RNA editing");

        if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(uniObj.getMolecule());
            commentXML.setMolecule(mol);
        }

        if ((uniObj.getNote() != null) && (!uniObj.getNote().getTexts().isEmpty())) {
            uniObj.getNote()
                    .getTexts()
                    .forEach(val -> commentXML.getText().add(evValueConverter.toXml(val)));
        }
        // LocationType - enum
        if (uniObj.getLocationType() == RnaEditingLocationType.Not_applicable
                || uniObj.getLocationType() == RnaEditingLocationType.Undetermined) {
            commentXML.setLocationType(uniObj.getLocationType().name());
        } else {
            uniObj.getPositions()
                    .forEach(pos -> commentXML.getLocation().add(positionConverter.toXml(pos)));
        }

        return commentXML;
    }
}
