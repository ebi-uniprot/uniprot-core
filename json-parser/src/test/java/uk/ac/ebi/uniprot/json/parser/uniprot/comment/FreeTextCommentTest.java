package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class FreeTextCommentTest {

    @Test
    public void testFreeTextSimple() {

        FreeTextComment comment = FreeTextCommentBuilder.newInstance()
                .commentType(CommentType.DISRUPTION_PHENOTYPE)
                .build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISRUPTION PHENOTYPE",jsonNode.get("commentType").asText());

    }

    @Test
    public void testFreeTextComplete() {
        FreeTextComment comment = getFreeTextComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISRUPTION PHENOTYPE",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("texts"));
        assertEquals(1,jsonNode.get("texts").size());
        JsonNode valueEvidence = jsonNode.get("texts").get(0);
        ValidateJson.validateValueEvidence(valueEvidence,"value","ECO:0000256","PIRNR","PIRNR001360");

    }

    public static FreeTextComment getFreeTextComment(){
        return FreeTextCommentBuilder.newInstance()
                .commentType(CommentType.DISRUPTION_PHENOTYPE)
                .texts(createEvidenceValues())
                .build();
    }

    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001360"));
        return evidences;
    }

    private static List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value", createEvidences()));
        return evidencedValues;
    }
}
