package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.comment.impl.WebResourceCommentBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class WebResourceCommentTest {

    @Test
    void testWebResourceSimple() {

        WebResourceComment comment = new WebResourceCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("WEB RESOURCE", jsonNode.get("commentType").asText());
    }

    @Test
    void testWebResourceComplete() {

        WebResourceComment comment = getWebResourceComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("WEB RESOURCE", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("Isoform 2", jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("resourceName"));
        assertEquals("resource name", jsonNode.get("resourceName").asText());

        assertNotNull(jsonNode.get("resourceUrl"));
        assertEquals("resource URL", jsonNode.get("resourceUrl").asText());

        assertNotNull(jsonNode.get("ftp"));
        assertTrue(jsonNode.get("ftp").asBoolean());

        assertNotNull(jsonNode.get("note"));
        assertEquals("Note text", jsonNode.get("note").asText());
    }

    public static WebResourceComment getWebResourceComment() {
        return new WebResourceCommentBuilder()
                .molecule("Isoform 2")
                .resourceName("resource name")
                .resourceUrl("resource URL")
                .isFtp(true)
                .note("Note text")
                .build();
    }
}
