package uk.ac.ebi.uniprot.xml.uniprot.citation;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;

public class CitationConverter implements Converter<CitationType, Citation> {
	private final ObjectFactory xmlUniprotFactory;
	
	public CitationConverter() {
		this( new ObjectFactory());
	}

	public CitationConverter( ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Citation fromXml(CitationType xmlObj) {
		uk.ac.ebi.uniprot.domain.citation.CitationType type = 
				uk.ac.ebi.uniprot.domain.citation.CitationType.typeOf(xmlObj.getType());
		Converter<CitationType, Citation> converter=CitationConverterFactory.
				INSTANCE.getConverter(xmlUniprotFactory, type);	
		if(converter !=null)
			return converter.fromXml(xmlObj);
		else
			return null;
	}

	@Override
	public CitationType toXml(Citation uniObj) {
		Converter<CitationType, Citation> converter=CitationConverterFactory.
				INSTANCE.getConverter(xmlUniprotFactory, uniObj.getCitationType());	
		if(converter !=null)
			return converter.toXml(uniObj);
		else
			return null;
	}
	
}
