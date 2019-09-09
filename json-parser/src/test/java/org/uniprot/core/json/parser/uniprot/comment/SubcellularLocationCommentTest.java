package org.uniprot.core.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class SubcellularLocationCommentTest {

    @Test
    public void testSubcellularLocationSimple() {

        SubcellularLocationComment comment = new SubcellularLocationCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SUBCELLULAR LOCATION",jsonNode.get("commentType").asText());

    }

    @Test
    public void testSubcellularLocationComplete() {

        SubcellularLocationComment comment = getSubcellularLocationComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SUBCELLULAR LOCATION",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("molecule value",jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("subcellularLocations"));
        assertEquals(1,jsonNode.get("subcellularLocations").size());
        JsonNode subcellularLocation = jsonNode.get("subcellularLocations").get(0);

        assertNotNull(subcellularLocation.get("location"));
        ValidateJson.validateValueEvidence(subcellularLocation.get("location")
                ,"location value","ECO:0000256","PIRNR","PIRNR001361");

        assertNotNull(subcellularLocation.get("topology"));
        ValidateJson.validateValueEvidence(subcellularLocation.get("topology"),
                "topology value","ECO:0000256","PIRNR","PIRNR001361");

        assertNotNull(subcellularLocation.get("orientation"));
        ValidateJson.validateValueEvidence(subcellularLocation.get("orientation"),
                "orientation value","ECO:0000256","PIRNR","PIRNR001361");

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1,jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(valueEvidence,"value","ECO:0000256","PIRNR","PIRNR001361");
    }

    public static SubcellularLocationComment getSubcellularLocationComment(){
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
        SubcellularLocation sublocation = new SubcellularLocationBuilder()
                .location(new SubcellularLocationValueBuilder("id1", "location value",evidences).build())
                .orientation(new SubcellularLocationValueBuilder("id2", "orientation value",evidences).build())
                .topology(new SubcellularLocationValueBuilder("id2", "topology value",evidences).build())
                .build();

        List<SubcellularLocation> subcellularLocations = Collections.singletonList(sublocation);
        Note note = new NoteBuilder(
                CreateUtils.createEvidencedValueList("value","ECO:0000256|PIRNR:PIRNR001361")).build();
        return new SubcellularLocationCommentBuilder()
                .molecule("molecule value")
                .subcellularLocations(subcellularLocations)
                .note(note)
                .build();
    }

}
