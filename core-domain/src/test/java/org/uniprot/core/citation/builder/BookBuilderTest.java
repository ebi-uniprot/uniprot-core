package org.uniprot.core.citation.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.impl.AuthorImpl;

class BookBuilderTest extends AbstractCitationBuilderTest {

    @Test
    void testBuildCitation() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
    }

    @Test
    void testAddBookName() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
    }

    @Test
    void testAddEditors() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        builder.editorsSet(Arrays.asList("David", "Charlie"));
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
    }

    @Test
    void testAddFirstPage() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        builder.editorsSet(Arrays.asList("David", "Charlie"));
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
    void testAddLastPage() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editorsSet(Arrays.asList("David", "Charlie"))
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
    void testAddVolume() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editorsSet(Arrays.asList("David", "Charlie"))
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
    void testAddPublisher() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editorsSet(Arrays.asList("David", "Charlie"))
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
    }

    @Test
    void testAddAddress() {
        BookBuilder builder = new BookBuilder();
        this.buildCitationParameters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editorsSet(Arrays.asList("David", "Charlie"))
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

    @Test
    void canAddSingleEditor() {
        Book book = new BookBuilder().editorsAdd("auth").build();
        assertFalse(book.getEditors().isEmpty());
        assertEquals(1, book.getEditors().size());
    }

    @Test
    void addingNullAuthCollection_willBeReplacedByEmpty() {
        Collection<Author> auth = null;
        Book book = new BookBuilder().editorsSet(auth).build();
        assertNotNull(book.getEditors());
        assertTrue(book.getEditors().isEmpty());
    }

    @Test
    void canAddAuthCol() {
        Book book =
                new BookBuilder().editorsSet(Collections.singleton(new AuthorImpl("auth"))).build();
        assertFalse(book.getEditors().isEmpty());
        assertEquals(1, book.getEditors().size());
    }

    @Test
    void canAddStringAuthor() {
        Book book = new BookBuilder().authorsAdd(("auth")).build();
        assertFalse(book.getAuthors().isEmpty());
        assertEquals(1, book.getAuthors().size());
    }

    @Test
    void nullStringAuthorWillBeIgnore() {
        String auth = null;
        Book book = new BookBuilder().authorsAdd(auth).build();
        assertTrue(book.getAuthors().isEmpty());
    }

    @Test
    void nullAuthorWillBeIgnored() {
        Author auth = null;
        Book book = new BookBuilder().authorsAdd(auth).build();
        assertTrue(book.getAuthors().isEmpty());
    }

    @Test
    void canAddAuthor() {
        Author auth = new AuthorImpl("auth");
        Book book = new BookBuilder().authorsAdd(auth).build();
        assertFalse(book.getAuthors().isEmpty());
        assertEquals(1, book.getAuthors().size());
    }

    @Test
    void canAddStringAuthGroup() {
        Book book = new BookBuilder().authorGroupAdd("authGroup").build();
        assertFalse(book.getAuthoringGroup().isEmpty());
        assertEquals(1, book.getAuthoringGroup().size());
    }

    @Test
    void nullAuthGroupWillIgnore() {
        Book book = new BookBuilder().authorGroupAdd(null).build();
        assertTrue(book.getAuthoringGroup().isEmpty());
    }

    @Test
    void canAddUnModifiableAuthList() {
        List<Author> authorList = Collections.singletonList(new AuthorImpl("auth"));
        Book book = new BookBuilder().authorsSet(authorList).build();
        assertFalse(book.getAuthors().isEmpty());
        assertEquals(1, book.getAuthors().size());
    }
}
