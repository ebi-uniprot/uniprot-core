package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class InternalSectionTest {

    @Test
    public void testInternalSectionSimple() {
        InternalSection internalSection= UniProtFactory.INSTANCE.createInternalSection(null,null,null);
        ValidateJson.verifyJsonRoundTripParser(internalSection);
    }
    @Test
    public void testInternalSectionComplete() {
        InternalSection internalSection = getInternalSection();
        ValidateJson.verifyJsonRoundTripParser(internalSection);
        ValidateJson.verifyEmptyFields(internalSection);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(internalSection);

        assertNotNull(jsonNode.get("internalLines"));
        assertEquals(1,jsonNode.get("internalLines").size());
        JsonNode internalLine = jsonNode.get("internalLines").get(0);
        assertNotNull(internalLine.get("value"));
        assertEquals("line value",internalLine.get("value").asText());
        assertNotNull(internalLine.get("type"));
        assertEquals("DR",internalLine.get("type").asText());

        assertNotNull(jsonNode.get("evidenceLines"));
        assertEquals(1,jsonNode.get("evidenceLines").size());
        JsonNode evidenceLine = jsonNode.get("evidenceLines").get(0);
        assertNotNull(evidenceLine.get("evidence"));
        assertEquals("evidence value",evidenceLine.get("evidence").asText());
        assertNotNull(evidenceLine.get("createDate"));
        assertEquals("2018-12-25",evidenceLine.get("createDate").asText());
        assertNotNull(evidenceLine.get("curator"));
        assertEquals("curator value",evidenceLine.get("curator").asText());

        assertNotNull(jsonNode.get("sourceLines"));
        assertEquals(1,jsonNode.get("sourceLines").size());
        JsonNode sourceLine = jsonNode.get("sourceLines").get(0);
        assertNotNull(sourceLine.get("value"));
        assertEquals("source line value",sourceLine.get("value").asText());

    }

    static InternalSection getInternalSection() {
        List<InternalLine> internalLines = Collections.singletonList(
                UniProtFactory.INSTANCE.createInternalLine(InternalLineType.DR,"line value")
        );
        List<EvidenceLine> evidenceLines = Collections.singletonList(
                UniProtFactory.INSTANCE.createEvidenceLine("evidence value",
                        LocalDate.of(2018,12,25),"curator value")
        );
        List<SourceLine> sourceLines = Collections.singletonList(
                UniProtFactory.INSTANCE.createSourceLine("source line value")
        );

        return UniProtFactory.INSTANCE.createInternalSection(internalLines,evidenceLines,sourceLines);
    }

}
