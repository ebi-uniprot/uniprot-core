package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.SubcellularLocation;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class SubcellularLocationCommentTest {

    @Test
    void testSubcellularLocationSimple() {

        SubcellularLocationComment comment = new SubcellularLocationCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SUBCELLULAR LOCATION", jsonNode.get("commentType").asText());
    }

    @Test
    void testSubcellularLocationComplete() {

        SubcellularLocationComment comment = getSubcellularLocationComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SUBCELLULAR LOCATION", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("molecule value", jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("subcellularLocations"));
        assertEquals(1, jsonNode.get("subcellularLocations").size());
        JsonNode subcellularLocation = jsonNode.get("subcellularLocations").get(0);

        assertNotNull(subcellularLocation.get("location"));
        ValidateJson.validateValueEvidence(
                subcellularLocation.get("location"),
                "location value",
                "ECO:0000269",
                "PIRNR",
                "PIRNR001361");

        assertNotNull(subcellularLocation.get("topology"));
        ValidateJson.validateValueEvidence(
                subcellularLocation.get("topology"),
                "topology value",
                "ECO:0000269",
                "PIRNR",
                "PIRNR001361");

        assertNotNull(subcellularLocation.get("orientation"));
        ValidateJson.validateValueEvidence(
                subcellularLocation.get("orientation"),
                "orientation value",
                "ECO:0000269",
                "PIRNR",
                "PIRNR001361");

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1, jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(
                valueEvidence, "value", "ECO:0000269", "PIRNR", "PIRNR001361");
    }

    public static SubcellularLocationComment getSubcellularLocationComment() {
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PIRNR:PIRNR001361");
        SubcellularLocation sublocation =
                new SubcellularLocationBuilder()
                        .location(
                                new SubcellularLocationValueBuilder()
                                        .id("id1")
                                        .value("location value")
                                        .evidencesSet(evidences)
                                        .build())
                        .orientation(
                                new SubcellularLocationValueBuilder()
                                        .id("id2")
                                        .value("orientation value")
                                        .evidencesSet(evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder()
                                        .id("id2")
                                        .value("topology value")
                                        .evidencesSet(evidences)
                                        .build())
                        .build();

        List<SubcellularLocation> subcellularLocations = Collections.singletonList(sublocation);
        Note note =
                new NoteBuilder(
                                CreateUtils.createEvidencedValueList(
                                        "value", "ECO:0000269|PIRNR:PIRNR001361"))
                        .build();
        return new SubcellularLocationCommentBuilder()
                .molecule("molecule value")
                .subcellularLocationsSet(subcellularLocations)
                .note(note)
                .build();
    }
}
