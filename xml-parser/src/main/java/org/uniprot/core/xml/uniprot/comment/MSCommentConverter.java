package org.uniprot.core.xml.uniprot.comment;

import com.google.common.base.Strings;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

public class MSCommentConverter implements CommentConverter<MassSpectrometryComment> {
    private final ObjectFactory xmlUniprotFactory;
    private final MSRangeConverter rangeConverter;
    private final EvidenceIndexMapper evRefMapper;

    public MSCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public MSCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.rangeConverter = new MSRangeConverter(xmlUniprotFactory);
    }

    @Override
    public MassSpectrometryComment fromXml(CommentType xmlObj) {
        if (xmlObj == null)
            return null;
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();

        if (xmlObj.getMethod() != null)
            builder.method(MassSpectrometryMethod.toType(xmlObj.getMethod()));

        if (xmlObj.getError() != null)
            builder.molWeightError(Float.parseFloat(xmlObj.getError()));

        if (xmlObj.getMass() != null)
            builder.molWeight(xmlObj.getMass());

        // Ranges
        builder.ranges(
                xmlObj.getLocation().stream().map(rangeConverter::fromXml).collect(Collectors.toList()));

        // Note
        if (!xmlObj.getText().isEmpty()) {
            builder.note(xmlObj.getText().get(0).getValue());
        }

        // Sources
        if (!xmlObj.getEvidence().isEmpty()) {
            List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
            builder.evidences(evidences);
        }
        return builder.build();

    }

    @Override
    public CommentType toXml(MassSpectrometryComment uniObj) {
        if (uniObj == null)
            return null;
        CommentType commentXML = xmlUniprotFactory.createCommentType();
        commentXML.setType(uniObj.getCommentType().toDisplayName().toLowerCase());
        // Method
        if (uniObj.getMethod() != null)
            commentXML.setMethod(uniObj.getMethod().getValue());

        // Mass
        if ((uniObj.getMolWeight() != null) && (uniObj.getMolWeight() > 0))
            commentXML.setMass(uniObj.getMolWeight());

        // Error
        if ((uniObj.getMolWeightError() != null) && (uniObj.getMolWeightError() > 0))
            commentXML.setError(Float.toString(uniObj.getMolWeightError()));

        // Ranges
        if (uniObj.getRanges() != null) {
            uniObj.getRanges().forEach(val -> commentXML.getLocation().add(rangeConverter.toXml(val)));
        }

        // Note
        if (!Strings.isNullOrEmpty(uniObj.getNote())) {
            EvidencedStringType noteXML = xmlUniprotFactory.createEvidencedStringType();
            noteXML.setValue(uniObj.getNote());
            commentXML.getText().add(noteXML);
        }

        if (!uniObj.getEvidences().isEmpty()) {
            List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
            if (!ev.isEmpty())
                commentXML.getEvidence().addAll(ev);
        }
        return commentXML;

    }

}