package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;


import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class JournalArticleConverter implements Converter<CitationType, JournalArticle> {

	private final ObjectFactory xmlUniprotFactory;
	private final PageConverter pageConverter = new PageConverter();

	public JournalArticleConverter() {
		this(new ObjectFactory());
	}

	public JournalArticleConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public JournalArticle fromXml(CitationType xmlObj) {
		JournalArticleBuilder builder =JournalArticleBuilder.newInstance();
		CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
		builder.journalName(xmlObj.getName());
		builder.firstPage(pageConverter.fromXml(xmlObj.getFirst()));
		builder.lastPage(pageConverter.fromXml(xmlObj.getLast()));
		builder.volume(xmlObj.getVolume());


		return builder.build();
	}

	@Override
	public CitationType toXml(JournalArticle uniObj) {
		CitationType xmlCitation = xmlUniprotFactory.createCitationType();
		CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);


		 xmlCitation.setType("journal article");
		 xmlCitation.setName(uniObj.getJournal().getName());
		 xmlCitation.setFirst(pageConverter.toXml(uniObj.getFirstPage()));
		 xmlCitation.setLast(pageConverter.toXml(uniObj.getLastPage()));
		 xmlCitation.setVolume(uniObj.getVolume());

		return xmlCitation;
	}


}
