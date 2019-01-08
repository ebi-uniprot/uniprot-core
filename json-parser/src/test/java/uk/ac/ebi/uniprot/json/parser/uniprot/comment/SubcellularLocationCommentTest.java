package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class SubcellularLocationCommentTest {

    @Test
    public void testSubcellularLocationSimple() {

        SubcellularLocationComment comment = SubcellularLocationCommentBuilder.newInstance().build();
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
        SubcellularLocation sublocation = SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("location value", createEvidences()),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("topology value", createEvidences()),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("orientation value", createEvidences()));

        List<SubcellularLocation> subcellularLocations = Collections.singletonList(sublocation);
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        return SubcellularLocationCommentBuilder.newInstance()
                .molecule("molecule value")
                .subcellularLocations(subcellularLocations)
                .note(note)
                .build();
    }

    private static List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value", createEvidences()));
        return evidencedValues;
    }

    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
