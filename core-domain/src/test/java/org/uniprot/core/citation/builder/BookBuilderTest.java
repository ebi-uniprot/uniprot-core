package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.CitationType;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookBuilderTest extends AbstractCitationBuilderTest {

    @Test
    public void testBuildCitation() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
    }

    @Test
    public void testAddBookName() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
    }

    @Test
    public void testAddEditors() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        builder.editors(Arrays.asList("David", "Charlie"));
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
    }

    @Test
    public void testAddFirstPage() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        builder.editors(Arrays.asList("David", "Charlie"));
        ;
        builder.firstPage("234");
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
        assertEquals("234", citation.getFirstPage());
    }

    @Test
    public void testAddLastPage() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList("David", "Charlie"))
                .firstPage("234")
                .lastPage("324C");
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
        assertEquals("234", citation.getFirstPage());
        assertEquals("324C", citation.getLastPage());
    }

    @Test
    public void testAddVolume() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList("David", "Charlie"))
                .firstPage("234")
                .lastPage("324C")
                .volume("3");
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
        assertEquals("234", citation.getFirstPage());
        assertEquals("324C", citation.getLastPage());
        assertEquals("3", citation.getVolume());
    }

    @Test
    public void testAddPublisher() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList("David", "Charlie"))
                .firstPage("234")
                .lastPage("324C")
                .volume("3")
                .publisher("London Press");
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
        assertEquals("234", citation.getFirstPage());
        assertEquals("324C", citation.getLastPage());
        assertEquals("3", citation.getVolume());
        assertEquals("London Press", citation.getPublisher());

        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddAddress() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList("David", "Charlie"))
                .firstPage("234")
                .lastPage("324C")
                .volume("3")
                .publisher("London Press")
                .address("London");
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
        assertEquals("234", citation.getFirstPage());
        assertEquals("324C", citation.getLastPage());
        assertEquals("3", citation.getVolume());
        assertEquals("London Press", citation.getPublisher());
        assertEquals("London", citation.getAddress());
    }
}
