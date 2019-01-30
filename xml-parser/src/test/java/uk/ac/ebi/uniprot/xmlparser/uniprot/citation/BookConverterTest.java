package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookConverterTest {

    @Test
    void test() {
        Book citation = create();
        BookConverter converter = new BookConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Book converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {

        Citation citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Citation converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    private Book create() {
        BookBuilder builder = new BookBuilder();
        String bookName = "Some book name";
        String title = "Some article title";
        String date = "2009";
        builder.bookName(bookName)
                .addEditor("David")
                .addEditor("Charlie")
                .firstPage("234").lastPage("324C").volume("3").publisher("London Press").address("London").title(title)
                .publicationDate(date);
        return builder.build();
    }
}
