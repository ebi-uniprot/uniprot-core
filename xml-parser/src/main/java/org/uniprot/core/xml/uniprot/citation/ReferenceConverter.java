package org.uniprot.core.xml.uniprot.citation;

import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.builder.UniProtReferenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.SourceDataType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;

public class ReferenceConverter implements Converter<ReferenceType, UniProtReference> {
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
	public UniProtReference fromXml(ReferenceType xmlObj) {
		return new UniProtReferenceBuilder()
				.citation(citationConverter.fromXml(xmlObj.getCitation()))
				.evidences(evRefMapper.parseEvidenceIds(xmlObj.getEvidence()))
				.positions(xmlObj.getScope())
				.comments(rcConverter.fromXml(xmlObj.getSource()))
				.build();
	}

	@Override
	public ReferenceType toXml(UniProtReference uniObj) {
		ReferenceType xmlReference = xmlUniprotFactory.createReferenceType();
		 // SCOPE
        if (uniObj.getReferencePositions() != null) {
        	uniObj.getReferencePositions()
        	.forEach(val -> xmlReference.getScope().add(val));     
        }
        SourceDataType sourceDataType = rcConverter.toXml(uniObj.getReferenceComments());
        if(sourceDataType !=null)
        	xmlReference.setSource(sourceDataType);
        xmlReference.setCitation(citationConverter.toXml(uniObj.getCitation()));
    	if (!uniObj.getEvidences().isEmpty()) {
			xmlReference.getEvidence().addAll( evRefMapper.writeEvidences(uniObj.getEvidences()));
    	}
		return xmlReference;
	}

}
