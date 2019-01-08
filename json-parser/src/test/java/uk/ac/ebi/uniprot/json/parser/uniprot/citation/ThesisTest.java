package uk.ac.ebi.uniprot.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ThesisBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class ThesisTest {


    @Test
    public void testThesisSimple() {
        Citation citation =  ThesisBuilder.newInstance().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("thesis", jsonNode.get("citationType").asText());
    }

    @Test
    public void testThesisComplete() {
        Citation citation = getThesis();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("thesis", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("institute"));
        assertEquals("thesis institute", jsonNode.get("institute").asText());

        assertNotNull(jsonNode.get("address"));
        assertEquals("thesis address", jsonNode.get("address").asText());

    }

    public static Thesis getThesis(){
        return ThesisBuilder.newInstance()
                .address("thesis address")
                .institute("thesis institute")
                .publicationDate(AbstractCitationBuilder.createPublicationDate("date value"))
                .authoringGroups(Collections.singletonList("auth group"))
                .authors(Collections.singletonList(AbstractCitationBuilder.createAuthor("author Leo")))
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1")))
                .build();
    }
}
