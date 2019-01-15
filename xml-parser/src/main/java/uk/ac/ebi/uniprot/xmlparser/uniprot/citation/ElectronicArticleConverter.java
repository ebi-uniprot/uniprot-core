package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.builder.ElectronicArticleBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class ElectronicArticleConverter implements Converter<CitationType, ElectronicArticle> {

	private final ObjectFactory xmlUniprotFactory;

	public ElectronicArticleConverter() {
		this(new ObjectFactory());
	}

	public ElectronicArticleConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public ElectronicArticle fromXml(CitationType xmlObj) {
		ElectronicArticleBuilder builder =ElectronicArticleBuilder.newInstance();
		CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
		builder.journalName(xmlObj.getName());
		builder.locator(xmlObj.getLocator());
		return builder.build();
	}

	@Override
	public CitationType toXml(ElectronicArticle uniObj) {
		CitationType xmlCitation = xmlUniprotFactory.createCitationType();
		CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);

		 xmlCitation.setType("online journal article");
		 xmlCitation.setName(uniObj.getJournal().getName());
		 xmlCitation.setLocator(uniObj.getLocator().getValue());
		return xmlCitation;
	}

}
