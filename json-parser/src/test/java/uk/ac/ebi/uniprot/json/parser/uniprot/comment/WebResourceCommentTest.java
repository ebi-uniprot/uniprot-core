package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.WebResourceCommentBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.*;
/**
 *
 * @author lgonzales
 */
public class WebResourceCommentTest {

    @Test
    public void testWebResourceSimple() {

        WebResourceComment comment = new WebResourceCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("WEB RESOURCE",jsonNode.get("commentType").asText());
    }

    @Test
    public void testWebResourceComplete() {

        WebResourceComment comment = getWebResourceComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("WEB RESOURCE",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("resourceName"));
        assertEquals("resource name",jsonNode.get("resourceName").asText());

        assertNotNull(jsonNode.get("resourceUrl"));
        assertEquals("resource URL",jsonNode.get("resourceUrl").asText());

        assertNotNull(jsonNode.get("ftp"));
        assertTrue(jsonNode.get("ftp").asBoolean());

        assertNotNull(jsonNode.get("note"));
        assertEquals("Note text",jsonNode.get("note").asText());

    }

    public static WebResourceComment getWebResourceComment(){
        return new WebResourceCommentBuilder()
                .resourceName("resource name")
                .resourceUrl("resource URL")
                .isFtp(true)
                .note("Note text")
                .build();
    }
}
