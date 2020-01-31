package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.builder.BookBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class BookConverterTest {

    @Test
    void test() {
        Book citation = create();
        BookConverter converter = new BookConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Book converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {

        Citation citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Citation converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    private Book create() {
        BookBuilder builder = new BookBuilder();
        String bookName = "Some book name";
        String title = "Some article title";
        String date = "2009";
        builder.bookName(bookName)
                .editorsAdd("David")
                .editorsAdd("Charlie")
                .firstPage("234")
                .lastPage("324C")
                .volume("3")
                .publisher("London Press")
                .address("London")
                .title(title)
                .publicationDate(date);
        return builder.build();
    }
}
