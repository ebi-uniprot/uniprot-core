package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryRangeBuilder;

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

        assertNotNull(jsonNode.get("ranges"));
        assertEquals(1, jsonNode.get("ranges").size());
        JsonNode rangeNode = jsonNode.get("ranges").get(0);

        assertNotNull(rangeNode.get("range"));
        JsonNode range = rangeNode.get("range");
        assertNotNull(range.get("start"));
        assertNotNull(range.get("start").get("value"));
        assertEquals(1, range.get("start").get("value").asInt());
        assertNotNull(range.get("start").get("modifier"));
        assertEquals("EXACT", range.get("start").get("modifier").asText());

        assertNotNull(range.get("end"));
        assertNotNull(range.get("end").get("value"));
        assertEquals(7, range.get("end").get("value").asInt());
        assertNotNull(range.get("end").get("modifier"));
        assertEquals("EXACT", range.get("end").get("modifier").asText());

        assertNotNull(rangeNode.get("isoformId"));
        assertEquals("isoformId value", rangeNode.get("isoformId").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        JsonNode evidence = jsonNode.get("evidences").get(0);
        ValidateJson.validateEvidence(evidence, "ECO:0000256", "PIRNR", "PIRNR001361");
    }

    public static MassSpectrometryComment getMassSpectrometryComment() {
        Range range = new Range(1, 7);
        MassSpectrometryRange massSpectrometryRange =
                new MassSpectrometryRangeBuilder()
                        .range(range)
                        .isoformId("isoformId value")
                        .build();

        return new MassSpectrometryCommentBuilder()
                .method(MassSpectrometryMethod.LSI)
                .molWeight(2.1f)
                .molWeightError(1.2f)
                .note("note value")
                .addRange(massSpectrometryRange)
                .evidences(CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361"))
                .build();
    }
}
