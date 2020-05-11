package org.uniprot.core.xml.uniprot.comment;

import com.google.common.base.Strings;

import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprotkb.comment.impl.MassSpectrometryCommentBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

import java.util.List;

public class MSCommentConverter implements CommentConverter<MassSpectrometryComment> {
    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceIndexMapper evRefMapper;

    public MSCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public MSCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public MassSpectrometryComment fromXml(CommentType xmlObj) {
        if (xmlObj == null) return null;
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();

        // Molecule
        if (xmlObj.getMolecule() != null) {
            builder.molecule(xmlObj.getMolecule().getValue());
        }

        if (xmlObj.getMethod() != null)
            builder.method(MassSpectrometryMethod.typeOf(xmlObj.getMethod()));

        if (xmlObj.getError() != null) builder.molWeightError(Float.parseFloat(xmlObj.getError()));

        if (xmlObj.getMass() != null) builder.molWeight(xmlObj.getMass());

        // Note
        if (!xmlObj.getText().isEmpty()) {
            builder.note(xmlObj.getText().get(0).getValue());
        }

        // Sources
        if (!xmlObj.getEvidence().isEmpty()) {
            List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
            builder.evidencesSet(evidences);
        }
        return builder.build();
    }

    @Override
    public CommentType toXml(MassSpectrometryComment uniObj) {
        if (uniObj == null) return null;
        CommentType commentXML = xmlUniprotFactory.createCommentType();
        commentXML.setType(uniObj.getCommentType().getDisplayName().toLowerCase());

        if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(uniObj.getMolecule());
            commentXML.setMolecule(mol);
        }

        // Method
        if (uniObj.getMethod() != null) commentXML.setMethod(uniObj.getMethod().getName());

        // Mass
        if ((uniObj.getMolWeight() != null) && (uniObj.getMolWeight() > 0))
            commentXML.setMass(uniObj.getMolWeight());

        // Error
        if ((uniObj.getMolWeightError() != null) && (uniObj.getMolWeightError() > 0))
            commentXML.setError(Float.toString(uniObj.getMolWeightError()));

        // Note
        if (!Strings.isNullOrEmpty(uniObj.getNote())) {
            EvidencedStringType noteXML = xmlUniprotFactory.createEvidencedStringType();
            noteXML.setValue(uniObj.getNote());
            commentXML.getText().add(noteXML);
        }

        if (!uniObj.getEvidences().isEmpty()) {
            List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
            if (!ev.isEmpty()) commentXML.getEvidence().addAll(ev);
        }
        return commentXML;
    }
}
