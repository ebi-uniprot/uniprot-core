package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.UnpublishedObservations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnpublishedObservationsBuilderTest extends AbstractCitationBuilderTest{

    @Test
    public void testBuildWithTitlePublication() {
        UnpublishedObservationsBuilder builder = UnpublishedObservationsBuilder.newInstance();
        String title ="Some title";
        builder.title(title)
        .publicationDate(UnpublishedObservationsBuilder.createPublicationDate("2015-MAY"));
        UnpublishedObservations citation = builder.build();
        assertEquals(title, citation.getTitle());
        assertEquals("2015-MAY", citation.getPublicationDate().getValue());
        assertTrue(citation.getAuthoringGroup().isEmpty());
        assertTrue(citation.getAuthors().isEmpty());
        assertEquals(CitationType.UNPUBLISHED_OBSERVATIONS, citation.getCitationType());

    }
    
    @Test
    public void testBuildAll() {
        UnpublishedObservationsBuilder builder = UnpublishedObservationsBuilder.newInstance();
        builderCitationParamters(builder);
        UnpublishedObservations citation = builder.build();
        verifyCitation(citation, CitationType.UNPUBLISHED_OBSERVATIONS);


    }

}
