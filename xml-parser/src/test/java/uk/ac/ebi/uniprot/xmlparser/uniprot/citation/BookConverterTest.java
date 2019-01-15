package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class BookConverterTest {

	@Test
	void test() {
		BookBuilder builder = BookBuilder.newInstance();
		String bookName = "Some book name";
		String title = "Some article title";
		String date = "2009";
		builder.bookName(bookName)
				.editors(Arrays.asList(AbstractCitationBuilder.createAuthor("David"),
						AbstractCitationBuilder.createAuthor("Charlie")))
				.firstPage("234").lastPage("324C").volume("3").publisher("London Press").address("London").title(title)
				.publicationDate(AbstractCitationBuilder.createPublicationDate(date));
		;
		Book book = builder.build();
		BookConverter converter = new BookConverter();
		CitationType xmlCitation = converter.toXml(book);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
		Book converted = converter.fromXml(xmlCitation);
		assertEquals(book, converted);
	}

}
