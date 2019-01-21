package uk.ac.ebi.uniprot.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class UnpublishedTest {

    @Test
    public void testUnpublishedSimple() {
        Citation citation =  UnpublishedBuilder.newInstance().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("unpublished observations", jsonNode.get("citationType").asText());
    }

    @Test
    public void testUnpublishedComplete() {
        Citation citation = getUnpublished();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("unpublished observations", jsonNode.get("citationType").asText());

    }


    public static Unpublished getUnpublished(){
        return UnpublishedBuilder.newInstance()
                .publicationDate(AbstractCitationBuilder.createPublicationDate("date value"))
                .authoringGroups(Collections.singletonList("auth group"))
                .authors(Collections.singletonList(AbstractCitationBuilder.createAuthor("author Leo")))
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1")))
                .build();
    }
}
