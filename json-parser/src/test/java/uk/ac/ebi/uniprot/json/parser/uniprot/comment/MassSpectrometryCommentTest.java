package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryRangeImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
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
public class MassSpectrometryCommentTest {

    @Test
    public void testInteractionSimple() {

        MassSpectrometryComment comment = MassSpectrometryCommentBuilder.newInstance()
                .build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("MASS SPECTROMETRY",jsonNode.get("commentType").asText());

    }

    @Test
    public void testInteractionComplete() {

        MassSpectrometryComment comment = getMassSpectrometryComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("MASS SPECTROMETRY",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("method"));
        assertEquals("LSI",jsonNode.get("method").asText());
        assertNotNull(jsonNode.get("molWeight"));
        assertEquals(2.1f,jsonNode.get("molWeight").asDouble(),0.1d);
        assertNotNull(jsonNode.get("molWeightError"));
        assertEquals(1.2f,jsonNode.get("molWeightError").asDouble(),0.1d);
        assertNotNull(jsonNode.get("note"));
        assertEquals("note value",jsonNode.get("note").asText());

        assertNotNull(jsonNode.get("ranges"));
        assertEquals(1,jsonNode.get("ranges").size());
        JsonNode rangeNode = jsonNode.get("ranges").get(0);

        assertNotNull(rangeNode.get("range"));
        JsonNode range = rangeNode.get("range");
        assertNotNull(range.get("start"));
        assertNotNull(range.get("start").get("value"));
        assertEquals(1,range.get("start").get("value").asInt());
        assertNotNull(range.get("start").get("modifier"));
        assertEquals("EXACT",range.get("start").get("modifier").asText());

        assertNotNull(range.get("end"));
        assertNotNull(range.get("end").get("value"));
        assertEquals(7,range.get("end").get("value").asInt());
        assertNotNull(range.get("end").get("modifier"));
        assertEquals("EXACT",range.get("end").get("modifier").asText());

        assertNotNull(rangeNode.get("isoformId"));
        assertEquals("isoformId value",rangeNode.get("isoformId").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        JsonNode evidence = jsonNode.get("evidences").get(0);
        ValidateJson.validateEvidence(evidence,"ECO:0000256","PIRNR","PIRNR001361");
    }

    public static MassSpectrometryComment getMassSpectrometryComment(){
        List<MassSpectrometryRange> range = Collections.singletonList(
               new MassSpectrometryRangeImpl(1,7,"isoformId value"));

        return MassSpectrometryCommentBuilder.newInstance()
                .massSpectrometryMethod(MassSpectrometryMethod.LSI)
                .molWeight(2.1f)
                .molWeightError(1.2f)
                .note("note value")
                .massSpectrometryRanges(range)
                .evidences(createEvidences())
                .build();
    }

    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

}
