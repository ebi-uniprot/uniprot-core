package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnpublishedBuilderTest extends AbstractCitationBuilderTest {

    @Test
    public void testBuildWithTitlePublication() {
        UnpublishedBuilder builder = UnpublishedBuilder.newInstance();
        String title = "Some title";
        builder.title(title)
                .publicationDate(UnpublishedBuilder.createPublicationDate("2015-MAY"));
        Unpublished citation = builder.build();
        assertEquals(title, citation.getTitle());
        assertEquals("2015-MAY", citation.getPublicationDate().getValue());
        assertTrue(citation.getAuthoringGroup().isEmpty());
        assertTrue(citation.getAuthors().isEmpty());
        assertEquals(CitationType.UNPUBLISHED, citation.getCitationType());
        TestHelper.verifyJson(citation);

    }

    @Test
    public void testBuildAll() {
        UnpublishedBuilder builder = UnpublishedBuilder.newInstance();
        buildCitationParameters(builder);
        Unpublished citation = builder.build();
        verifyCitation(citation, CitationType.UNPUBLISHED);
        TestHelper.verifyJson(citation);


    }

}
