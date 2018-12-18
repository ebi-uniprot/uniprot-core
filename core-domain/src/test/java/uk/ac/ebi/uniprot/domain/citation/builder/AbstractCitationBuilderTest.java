package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AbstractCitationBuilderTest {

    @Test
    public void testCreateAuthor() {
        Author author = AbstractCitationBuilder.createAuthor("Tom");
        assertEquals("Tom", author.getValue());
        TestHelper.verifyJson(author);
    }

    @Test
    public void testCreatePublicaitonDate() {
        PublicationDate pubDate = AbstractCitationBuilder.createPublicationDate("2013-AUG");
        assertEquals("2013-AUG", pubDate.getValue());
        TestHelper.verifyJson(pubDate);
    }


    void builderCitationParamters(AbstractCitationBuilder<?> builder) {
        String title = "Some title";
        builder.title(title).publicationDate(UnpublishedBuilder.createPublicationDate("2015-MAY"))
                .authoringGroups(Arrays.asList(new String[]{"T1", "T2"})).authors(Arrays.asList(new Author[]{
                AbstractCitationBuilder.createAuthor("Tom"), AbstractCitationBuilder.createAuthor("John")}));
    }

    void verifyCitation(Citation citation, CitationType citationType) {
        String title = "Some title";
        assertEquals(title, citation.getTitle());
        assertEquals("2015-MAY", citation.getPublicationDate().getValue());
        assertEquals(2, citation.getAuthoringGroup().size());
        assertEquals(2, citation.getAuthors().size());
        assertEquals("John", citation.getAuthors().get(1).getValue());
        assertEquals("T1", citation.getAuthoringGroup().get(0));
        assertEquals(citationType, citation.getCitationType());

    }
}
