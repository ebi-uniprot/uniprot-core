package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.CitationType;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BookBuilderTest extends AbstractCitationBuilderTest {

    @Test
    public void testBuildCitation() {
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
    }

    @Test
    public void testAddBookName() {
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
    }

    @Test
    public void testAddEditors() {
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        builder.editors(Arrays.asList(new Author[]{AbstractCitationBuilder.createAuthor("David"),
                                                   AbstractCitationBuilder.createAuthor("Charlie")
                                      }
        ));
        Book citation = builder.build();
        this.verifyCitation(citation, CitationType.BOOK);
        assertEquals(bookName, citation.getBookName());
        assertEquals(2, citation.getEditors().size());
        assertEquals("David", citation.getEditors().get(0).getValue());
    }

    @Test
    public void testAddFirstPage() {
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName);
        builder.editors(Arrays.asList(new Author[]{AbstractCitationBuilder.createAuthor("David"),
                                                   AbstractCitationBuilder.createAuthor("Charlie")
                                      }
        ));
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
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList(new Author[]{AbstractCitationBuilder.createAuthor("David"),
                                                    AbstractCitationBuilder.createAuthor("Charlie")
                                       }
                ))
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
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList(new Author[]{AbstractCitationBuilder.createAuthor("David"),
                                                    AbstractCitationBuilder.createAuthor("Charlie")
                                       }
                ))
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
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList(new Author[]{AbstractCitationBuilder.createAuthor("David"),
                                                    AbstractCitationBuilder.createAuthor("Charlie")
                                       }
                ))
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
        BookBuilder builder = BookBuilder.newInstance();
        this.builderCitationParamters(builder);
        String bookName = "Some book name";
        builder.bookName(bookName)
                .editors(Arrays.asList(new Author[]{AbstractCitationBuilder.createAuthor("David"),
                                                    AbstractCitationBuilder.createAuthor("Charlie")
                                       }
                ))
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
