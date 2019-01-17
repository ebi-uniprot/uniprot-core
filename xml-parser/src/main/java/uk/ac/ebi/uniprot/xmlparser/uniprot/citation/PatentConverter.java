package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.builder.PatentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class PatentConverter implements Converter<CitationType, Patent> {

	private final ObjectFactory xmlUniprotFactory;

	public PatentConverter() {
		this(new ObjectFactory());
	}

	public PatentConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Patent fromXml(CitationType xmlObj) {
		PatentBuilder builder =PatentBuilder.newInstance();
		CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);	
		builder.patentNumber(xmlObj.getNumber());
		return builder.build();
	}

	@Override
	public CitationType toXml(Patent uniObj) {
		CitationType xmlCitation = xmlUniprotFactory.createCitationType();
		CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
		xmlCitation.setType(uniObj.getCitationType().getValue());
		xmlCitation.setNumber(uniObj.getPatentNumber());
		return xmlCitation;
	}

}
