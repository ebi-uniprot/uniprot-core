package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtReferenceFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SourceDataType;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

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
		return UniProtReferenceFactory.INSTANCE.createUniProtReference(
				citationConverter.fromXml(xmlObj.getCitation()),
				xmlObj.getScope(), rcConverter.fromXml(xmlObj.getSource()),
				evRefMapper.parseEvidenceIds(xmlObj.getEvidence()));
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
