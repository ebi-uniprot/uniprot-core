package org.uniprot.core.xml.uniprot.comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.APIsoform;
import org.uniprot.core.uniprot.comment.IsoformName;
import org.uniprot.core.uniprot.comment.IsoformSequenceStatus;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.builder.APIsoformBuilder;
import org.uniprot.core.uniprot.comment.builder.IsoformNameBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.IsoformType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedValueConverter;

public class APIsoformConverter implements Converter<IsoformType, APIsoform> {

    private final ObjectFactory xmlUniprotFactory;
    private final EvidencedValueConverter evValueConverter;
    private final EvidenceIndexMapper evRefMapper;
    private static final Pattern SPACE = Pattern.compile(" ");

    public APIsoformConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public APIsoformConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
        this.evRefMapper = evRefMapper;
    }

    @Override
    public APIsoform fromXml(IsoformType xmlObj) {
        APIsoformBuilder builder = new APIsoformBuilder();

        // IsoformId info
        for (String id : xmlObj.getId()) {
            if (id != null && !id.isEmpty()) builder.addId(id);
        }
        // IsoformName and IsoformSynonym info
        boolean isName =
                true; // The first component in the file is the Name and the rest are the synonyms
        List<IsoformName> synonyms = new ArrayList<>();
        for (IsoformType.Name name : xmlObj.getName()) {
            IsoformName isoformName = isoformNameFromXml(name);
            if (isName) {
                builder.name(isoformName);
                isName = false;
            } else {
                synonyms.add(isoformName);
            }
        }
        builder.synonyms(synonyms);

        // Isoform Sequence info
        IsoformType.Sequence isoformSequence = xmlObj.getSequence();
        if (isoformSequence.getRef() != null && !isoformSequence.getRef().isEmpty()) {
            String[] isoformSequenceRefs = SPACE.split(isoformSequence.getRef());
            builder.sequenceIds(Arrays.asList(isoformSequenceRefs));
        }

        if (isoformSequence.getType() != null) {
            builder.sequenceStatus(IsoformSequenceStatus.typeOf(isoformSequence.getType()));
        }

        // Isoform Note info
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
    public IsoformType toXml(APIsoform isoform) {
        if (isoform == null) return null;
        IsoformType isoformType = xmlUniprotFactory.createIsoformType();
        if ((isoform.getIsoformIds() != null) && !isoform.getIsoformIds().isEmpty()) {
            isoform.getIsoformIds().forEach(val -> isoformType.getId().add(val.getValue()));
        }
        if (isoform.getName() != null) {
            isoformType.getName().add(isoformNameToXml(isoform.getName()));
        }

        // IsoformSynonyms
        if (isoform.getSynonyms() != null && !isoform.getSynonyms().isEmpty()) {
            isoform.getSynonyms()
                    .forEach(name -> isoformType.getName().add(isoformNameToXml(name)));
        }

        Note note = isoform.getNote();
        if ((note != null) && (!note.getTexts().isEmpty())) {
            note.getTexts().forEach(val -> isoformType.getText().add(evValueConverter.toXml(val)));
        }

        // IsoformSequence
        if (isoform.getSequenceIds().isEmpty()) {
            IsoformType.Sequence sequenceXML = xmlUniprotFactory.createIsoformTypeSequence();
            sequenceXML.setType(isoform.getIsoformSequenceStatus().getValue());
            isoformType.setSequence(sequenceXML);
        } else {
            IsoformType.Sequence sequenceXML = xmlUniprotFactory.createIsoformTypeSequence();
            sequenceXML.setType(isoform.getIsoformSequenceStatus().getValue());
            sequenceXML.setRef(isoform.getSequenceIds().stream().collect(Collectors.joining(" ")));
            isoformType.setSequence(sequenceXML);
        }

        return isoformType;
    }

    private IsoformName isoformNameFromXml(IsoformType.Name xmlObj) {
        List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
        return new IsoformNameBuilder(xmlObj.getValue(), evidences).build();
    }

    private IsoformType.Name isoformNameToXml(IsoformName name) {
        IsoformType.Name xmlName = xmlUniprotFactory.createIsoformTypeName();
        xmlName.setValue(name.getValue());
        if ((name.getEvidences() != null) && !name.getEvidences().isEmpty()) {
            xmlName.getEvidence().addAll(evRefMapper.writeEvidences(name.getEvidences()));
        }
        return xmlName;
    }
}
