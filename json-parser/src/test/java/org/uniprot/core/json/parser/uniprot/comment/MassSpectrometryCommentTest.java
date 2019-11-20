package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryCommentBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class MassSpectrometryCommentTest {

    @Test
    void testInteractionSimple() {

        MassSpectrometryComment comment = new MassSpectrometryCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("MASS SPECTROMETRY", jsonNode.get("commentType").asText());
    }

    @Test
    void testInteractionComplete() {

        MassSpectrometryComment comment = getMassSpectrometryComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("MASS SPECTROMETRY", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("method"));
        assertEquals("LSI", jsonNode.get("method").asText());
        assertNotNull(jsonNode.get("molWeight"));
        assertEquals(2.1f, jsonNode.get("molWeight").asDouble(), 0.1d);
        assertNotNull(jsonNode.get("molWeightError"));
        assertEquals(1.2f, jsonNode.get("molWeightError").asDouble(), 0.1d);
        assertNotNull(jsonNode.get("note"));
        assertEquals("note value", jsonNode.get("note").asText());

      

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        JsonNode evidence = jsonNode.get("evidences").get(0);
        ValidateJson.validateEvidence(evidence, "ECO:0000256", "PIRNR", "PIRNR001361");
    }

    public static MassSpectrometryComment getMassSpectrometryComment() {
       

        return new MassSpectrometryCommentBuilder()
        		.molecule("isoform 1")
                .method(MassSpectrometryMethod.LSI)
                .molWeight(2.1f)
                .molWeightError(1.2f)
                .note("note value")
                .evidences(CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361"))
                .build();
    }
}
