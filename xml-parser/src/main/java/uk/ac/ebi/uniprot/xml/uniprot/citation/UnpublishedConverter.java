package uk.ac.ebi.uniprot.xml.uniprot.citation;

import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.builder.UnpublishedBuilder;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;

public class UnpublishedConverter implements Converter<CitationType, Unpublished> {

	private final ObjectFactory xmlUniprotFactory;

	public UnpublishedConverter() {
		this(new ObjectFactory());
	}

	public UnpublishedConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Unpublished fromXml(CitationType xmlObj) {
		UnpublishedBuilder builder =new UnpublishedBuilder();
		CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);	
		return builder.build();
	}

	@Override
	public CitationType toXml(Unpublished uniObj) {
		CitationType xmlCitation = xmlUniprotFactory.createCitationType();
		CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
		xmlCitation.setType(uniObj.getCitationType().getValue());
		return xmlCitation;
	}


}
