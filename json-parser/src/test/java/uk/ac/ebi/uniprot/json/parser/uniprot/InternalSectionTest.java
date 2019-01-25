package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.builder.EvidenceLineBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.InternalLineBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.InternalSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.SourceLineBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class InternalSectionTest {

    @Test
    public void testInternalSectionSimple() {
        InternalSection internalSection = new InternalSectionBuilder().build();
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
        InternalLine internalLine = new InternalLineBuilder()
                .type(InternalLineType.DR)
                .value("line value")
                .build();

        EvidenceLine evidenceLine = new EvidenceLineBuilder()
                .evidence("evidence value")
                .createDate(LocalDate.of(2018,12,25))
                .curator("curator value")
                .build();

        SourceLine sourceLine = new SourceLineBuilder()
                .value("source line value")
                .build();

        return new InternalSectionBuilder()
                .addEvidenceLine(evidenceLine)
                .addInternalLine(internalLine)
                .addSourceLine(sourceLine)
                .build();
    }

}
