package uk.ac.ebi.uniprot.xml.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.builder.JournalArticleBuilder;
import org.uniprot.core.impl.DBCrossReferenceImpl;

import uk.ac.ebi.uniprot.xml.jaxb.proteome.ConsortiumType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.JournalType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.NameListType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.PersonType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ReferenceType;

class JournalArticleConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
	JournalArticleConverter converter = new JournalArticleConverter();
	ReferenceConverter referenceConverter = new ReferenceConverter();
	@Test
	void testFromXml() {
		ReferenceType reference = xmlFactory.createReferenceType();
		reference.setDate("2018");
		NameListType nameList = xmlFactory.createNameListType();
		ConsortiumType consortium = xmlFactory.createConsortiumType();
		consortium.setName("Some consortium");
		nameList.getConsortiumOrPerson().add(consortium);
		
		PersonType p1= xmlFactory.createPersonType();
		p1.setName("James");
		nameList.getConsortiumOrPerson().add(p1);
		PersonType p2= xmlFactory.createPersonType();
		p2.setName("James");
		nameList.getConsortiumOrPerson().add(p2);
		
		reference.setAuthorList(nameList);
		JournalType journal = xmlFactory.createJournalType();
		journal.setFirst("25");
		journal.setLast("50");
		journal.setVolume("21");
		journal.setName("Nature");
		journal.setTitle("Some Protein related to Variation");
		reference.setJournal(journal);
		JournalArticle journalArticle = converter.fromXml(reference);
		assertEquals("Nature", journalArticle.getJournal().getName());
		assertEquals("50", journalArticle.getLastPage());
		assertEquals("Some Protein related to Variation", journalArticle.getTitle());
		assertEquals("21", journalArticle.getVolume());
		Citation converted = referenceConverter.fromXml(reference);
		assertEquals(journalArticle, converted);
		
		
	}

	@Test
	void testToXml() {
		JournalArticle ja = create();
		ReferenceType reference =converter.toXml(ja);
		ReferenceType refConverted = referenceConverter.toXml(ja);
		assertEquals(reference, refConverted);
		JournalArticle converted = converter.fromXml(reference);
		assertEquals(ja, converted);
	}
	private JournalArticle create() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName).firstPage("213").lastPage("223").volume("2").title(title)
                .publicationDate(date)
                .addAuthor("Sulson J.E.")
                .addAuthor("JWaterston R.")
                .authoringGroups(Arrays.asList("The C. elegans sequencing consortium"))
                .citationXrefs(Arrays.asList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "9851916"),
                                             new DBCrossReferenceImpl<>(CitationXrefType.DOI,
                                                                        "https://doi.org/10.1126/science.282.5396.2012")));
        JournalArticle citation = builder.build();
        return citation;
    }
}
