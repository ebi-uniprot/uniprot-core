package org.uniprot.core.xml.uniprot.comment;

import java.util.List;

import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.builder.SequenceCautionCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

import com.google.common.base.Strings;

public class SCCommentConverter implements CommentConverter<SequenceCautionComment> {

    private final ObjectFactory xmlUniprotFactory;
    private final SCConflictConverter conflictConverter;
    private final EvidenceIndexMapper evRefMapper;

    public SCCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public SCCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.conflictConverter = new SCConflictConverter(xmlUniprotFactory);
        this.evRefMapper = evRefMapper;
    }

    @Override
    public SequenceCautionComment fromXml(CommentType xmlObj) {
        if (xmlObj == null) return null;

        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();

        // Molecule
        if (xmlObj.getMolecule() != null) {
            builder.molecule(xmlObj.getMolecule().getValue());
        }

        CommentType.Conflict conflict = xmlObj.getConflict();
        SequenceCautionType scType = SequenceCautionType.UNKNOWN;
        if (conflict != null) {
            builder.sequence(conflictConverter.fromXml(conflict));
            scType = SequenceCautionType.typeOf(conflict.getType());
            builder.sequenceCautionType(scType);
        }
        if (!xmlObj.getText().isEmpty()) {
            String text = xmlObj.getText().get(0).getValue();
            //			if(text.endsWith(".")){
            //				text= text.substring(0, text.length() - 1);
            //			}
            builder.note(text);
        }

        if (!xmlObj.getEvidence().isEmpty()) {
            List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
            builder.evidences(evidences);
        }
        return builder.build();
    }

    @Override
    public CommentType toXml(SequenceCautionComment uniObj) {
        if (uniObj == null) return null;
        CommentType commentXML = xmlUniprotFactory.createCommentType();
        commentXML.setType(uniObj.getCommentType().toDisplayName().toLowerCase());

        if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(uniObj.getMolecule());
            commentXML.setMolecule(mol);
        }

        // Sequence Conflict
        if (uniObj.getSequence() != null) {
            commentXML.setConflict(conflictConverter.toXml(uniObj.getSequence()));
        }

        if (uniObj.getSequenceCautionType() != null) {
            // set display name to lower case to match enumerated values from uniprot.xsd
            commentXML
                    .getConflict()
                    .setType(uniObj.getSequenceCautionType().toDisplayName().toLowerCase());
        }
        if (!Strings.isNullOrEmpty(uniObj.getNote())) {
            EvidencedStringType text = xmlUniprotFactory.createEvidencedStringType();
            String note = uniObj.getNote();
            //			if (!note.endsWith(".")) {
            //				note += ".";
            //			}
            text.setValue(note);
            commentXML.getText().add(text);
        }

        // Evidences
        if (!uniObj.getEvidences().isEmpty()) {
            commentXML.getEvidence().addAll(evRefMapper.writeEvidences(uniObj.getEvidences()));
        }

        return commentXML;
    }
}
