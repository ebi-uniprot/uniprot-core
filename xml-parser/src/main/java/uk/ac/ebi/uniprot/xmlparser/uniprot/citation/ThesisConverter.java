package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class ThesisConverter implements Converter<CitationType, Thesis> {

	private final ObjectFactory xmlUniprotFactory;

	public ThesisConverter() {
		this(new ObjectFactory());
	}

	public ThesisConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Thesis fromXml(CitationType xmlObj) {
		ThesisBuilder builder = ThesisBuilder.newInstance();
		CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
		builder.institute(xmlObj.getInstitute());
		builder.address(xmlObj.getCountry());
	
		return builder.build();
	}

	@Override
	public CitationType toXml(Thesis uniObj) {
		CitationType xmlCitation = xmlUniprotFactory.createCitationType();
		CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
		xmlCitation.setType("thesis");
		xmlCitation.setInstitute(uniObj.getInstitute());
		xmlCitation.setCountry(uniObj.getAddress());
		return xmlCitation;
	}


}
