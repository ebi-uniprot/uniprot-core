package org.uniprot.core.xml.uniprot.comment;

import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedValueConverter;

import com.google.common.base.Strings;

public class SCLCommentConverter implements CommentConverter<SubcellularLocationComment> {
    private final ObjectFactory xmlUniprotFactory;
    private final SubcellularLocationConverter locationConverter;
    private final EvidencedValueConverter evValueConverter;

    public SCLCommentConverter(EvidenceIndexMapper evRefMapper, SubcellLocationNameMap subcellLocationNameMap) {
        this(evRefMapper, new ObjectFactory(), subcellLocationNameMap);
    }

    public SCLCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory, SubcellLocationNameMap subcellLocationNameMap) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.locationConverter = new SubcellularLocationConverter(evRefMapper, xmlUniprotFactory, subcellLocationNameMap);
        this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
    }

    @Override
    public SubcellularLocationComment fromXml(CommentType xmlObj) {
        if (xmlObj == null) return null;
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();

        // Molecule
        if (xmlObj.getMolecule() != null) {
            builder.molecule(xmlObj.getMolecule().getValue());
        }
        builder.subcellularLocationsSet(
                xmlObj.getSubcellularLocation().stream()
                        .map(locationConverter::fromXml)
                        .collect(Collectors.toList()));
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
    public CommentType toXml(SubcellularLocationComment uniObj) {
        if (uniObj == null) return null;
        CommentType commentXML = xmlUniprotFactory.createCommentType();
        commentXML.setType(uniObj.getCommentType().getDisplayName().toLowerCase());
        if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(uniObj.getMolecule());
            commentXML.setMolecule(mol);
        }
        uniObj.getSubcellularLocations()
                .forEach(
                        val ->
                                commentXML
                                        .getSubcellularLocation()
                                        .add(locationConverter.toXml(val)));
        Note note = uniObj.getNote();
        if ((note != null) && (!note.getTexts().isEmpty())) {
            note.getTexts().forEach(val -> commentXML.getText().add(evValueConverter.toXml(val)));
        }
        return commentXML;
    }
}
