package uk.ac.ebi.uniprot.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.UnpublishedBuilder;
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
        Citation citation =  new UnpublishedBuilder().build();
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
        DBCrossReference<CitationXrefType> xref = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("somepID1").build();
        return new UnpublishedBuilder()
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(xref))
                .build();
    }
}
