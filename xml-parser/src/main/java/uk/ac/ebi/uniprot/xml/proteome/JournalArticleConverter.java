package uk.ac.ebi.uniprot.xml.proteome;

import java.util.stream.Collectors;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.builder.JournalArticleBuilder;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.JournalType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ReferenceType;

public class JournalArticleConverter implements Converter<ReferenceType, JournalArticle> {
	private final ObjectFactory xmlFactory;

	public JournalArticleConverter() {
		this(new ObjectFactory());
	}

	public JournalArticleConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}
	
	
	@Override
	public JournalArticle fromXml(ReferenceType xmlObj) {
		JournalArticleBuilder builder = new JournalArticleBuilder();
		ReferenceConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
		JournalType journal = xmlObj.getJournal();
		builder.title(journal.getTitle()).firstPage(journal.getFirst()).lastPage(journal.getLast())
				.volume(journal.getVolume()).journalName(journal.getName())
				.citationXrefs(journal.getDbReference().stream().map(this::fromXml).collect(Collectors.toList()));
		return builder.build();
	}

	@Override
	public ReferenceType toXml(JournalArticle uniObj) {
		  ReferenceType xmlCitation = xmlFactory.createReferenceType();
		  ReferenceConverterHelper.updateToXmlCitatation(xmlFactory, xmlCitation, uniObj);
		  JournalType xmlJournal = xmlFactory.createJournalType();
		  xmlJournal.setFirst(uniObj.getFirstPage());
		  xmlJournal.setLast(uniObj.getLastPage());
		  xmlJournal.setTitle(uniObj.getTitle());
		  xmlJournal.setName(uniObj.getJournal().getName());
		  xmlJournal.setVolume(uniObj.getVolume());
		  uniObj.getCitationXrefs().stream()
		  .map(this::toXml)
		  .forEach(val ->xmlJournal.getDbReference().add(val));
		  xmlCitation.setJournal(xmlJournal);
		return xmlCitation;
	}

	private DBCrossReference<CitationXrefType> fromXml(DbReferenceType xmlRef) {
		CitationXrefType type = CitationXrefType.typeOf(xmlRef.getType());
		return new DBCrossReferenceBuilder<CitationXrefType>().databaseType(type).id(xmlRef.getId()).build();
	}
	private DbReferenceType toXml( DBCrossReference<CitationXrefType> xref) {
		DbReferenceType dbReferenceType = xmlFactory.createDbReferenceType();
		dbReferenceType.setId(xref.getId());
		dbReferenceType.setType(xref.getDatabaseType().getName());
		return dbReferenceType;
	}
}
