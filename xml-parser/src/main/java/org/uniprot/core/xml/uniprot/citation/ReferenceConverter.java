package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.impl.UniProtKBReferenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.SourceDataType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

public class ReferenceConverter implements Converter<ReferenceType, UniProtKBReference> {
    private final EvidenceIndexMapper evRefMapper;
    private final ObjectFactory xmlUniprotFactory;
    private final ReferenceCommentConverter rcConverter;
    private final CitationConverter citationConverter;

    public ReferenceConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public ReferenceConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.rcConverter = new ReferenceCommentConverter(evRefMapper, xmlUniprotFactory);
        this.citationConverter = new CitationConverter(xmlUniprotFactory);
    }

    @Override
    public UniProtKBReference fromXml(ReferenceType xmlObj) {
        return new UniProtKBReferenceBuilder()
                .referenceNumber(Integer.parseInt(xmlObj.getKey()))
                .citation(citationConverter.fromXml(xmlObj.getCitation()))
                .evidencesSet(evRefMapper.parseEvidenceIds(xmlObj.getEvidence()))
                .referencePositionsSet(xmlObj.getScope())
                .referenceCommentsSet(rcConverter.fromXml(xmlObj.getSource()))
                .build();
    }

    @Override
    public ReferenceType toXml(UniProtKBReference uniObj) {
        ReferenceType xmlReference = xmlUniprotFactory.createReferenceType();
        xmlReference.setKey(String.valueOf(uniObj.getReferenceNumber()));
        // SCOPE
        if (uniObj.getReferencePositions() != null) {
            uniObj.getReferencePositions().forEach(val -> xmlReference.getScope().add(val));
        }
        SourceDataType sourceDataType = rcConverter.toXml(uniObj.getReferenceComments());
        if (sourceDataType != null) xmlReference.setSource(sourceDataType);
        xmlReference.setCitation(citationConverter.toXml(uniObj.getCitation()));
        if (!uniObj.getEvidences().isEmpty()) {
            xmlReference.getEvidence().addAll(evRefMapper.writeEvidences(uniObj.getEvidences()));
        }
        return xmlReference;
    }
}
