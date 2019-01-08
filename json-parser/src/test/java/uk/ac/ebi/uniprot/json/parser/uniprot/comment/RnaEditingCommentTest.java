package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingLocationType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl;
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
public class RnaEditingCommentTest {

    @Test
    public void testRnaEditingSimple() {

        RnaEditingComment comment = RnaEditingCommentBuilder.newInstance()
                .build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("RNA EDITING",jsonNode.get("commentType").asText());

    }

    @Test
    public void testRnaEditingComplete() {
        RnaEditingComment comment = getRnaEditingComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("RNA EDITING",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("locationType"));
        assertEquals("Known",jsonNode.get("locationType").asText());

        assertNotNull(jsonNode.get("positions"));
        assertEquals(1,jsonNode.get("positions").size());
        JsonNode position = jsonNode.get("positions").get(0);

        assertNotNull(position.get("position"));
        assertEquals("rna position",position.get("position").asText());

        assertNotNull(position.get("evidences"));
        assertEquals(1,position.get("evidences").size());
        ValidateJson.validateEvidence(position.get("evidences").get(0),"ECO:0000256","PIRNR","PIRNR001361");

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1,jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(valueEvidence,"value","ECO:0000256","PIRNR","PIRNR001361");
    }

    public static RnaEditingComment getRnaEditingComment(){
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        List<RnaEdPosition> rnaEdPositions = Collections.singletonList(
                RnaEditingCommentImpl.createPosition("rna position",createEvidences()));

        return RnaEditingCommentBuilder.newInstance()
                .rnaEditingLocationType(RnaEditingLocationType.Known)
                .locations(rnaEdPositions)
                .note(note)
                .build();
    }

    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    private static List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value", createEvidences()));
        return evidencedValues;
    }
}
