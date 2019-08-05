package org.uniprot.core.citation.builder;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.builder.UnpublishedBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnpublishedBuilderTest extends AbstractCitationBuilderTest {
    @Test
    public void testBuildWithTitlePublication() {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        String title = "Some title";
        builder.title(title)
                .publicationDate("2015-MAY");
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
        UnpublishedBuilder builder = new UnpublishedBuilder();
        buildCitationParameters(builder);
        Unpublished citation = builder.build();
        verifyCitation(citation, CitationType.UNPUBLISHED);
        TestHelper.verifyJson(citation);
    }
}
