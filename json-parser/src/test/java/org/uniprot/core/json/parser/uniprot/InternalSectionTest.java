package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.impl.EvidenceLineBuilder;
import org.uniprot.core.uniprot.impl.InternalLineBuilder;
import org.uniprot.core.uniprot.impl.InternalSectionBuilder;
import org.uniprot.core.uniprot.impl.SourceLineBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class InternalSectionTest {

    @Test
    void testInternalSectionSimple() {
        InternalSection internalSection = new InternalSectionBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(internalSection);
    }

    @Test
    void testInternalSectionComplete() {
        InternalSection internalSection = getInternalSection();
        ValidateJson.verifyJsonRoundTripParser(internalSection);
        ValidateJson.verifyEmptyFields(internalSection);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(internalSection);

        assertNotNull(jsonNode.get("internalLines"));
        assertEquals(1, jsonNode.get("internalLines").size());
        JsonNode internalLine = jsonNode.get("internalLines").get(0);
        assertNotNull(internalLine.get("value"));
        assertEquals("line value", internalLine.get("value").asText());
        assertNotNull(internalLine.get("type"));
        assertEquals("DR", internalLine.get("type").asText());

        assertNotNull(jsonNode.get("evidenceLines"));
        assertEquals(1, jsonNode.get("evidenceLines").size());
        JsonNode evidenceLine = jsonNode.get("evidenceLines").get(0);
        assertNotNull(evidenceLine.get("evidence"));
        assertEquals("evidence value", evidenceLine.get("evidence").asText());
        assertNotNull(evidenceLine.get("createDate"));
        assertEquals("2018-12-25", evidenceLine.get("createDate").asText());
        assertNotNull(evidenceLine.get("curator"));
        assertEquals("curator value", evidenceLine.get("curator").asText());

        assertNotNull(jsonNode.get("sourceLines"));
        assertEquals(1, jsonNode.get("sourceLines").size());
        JsonNode sourceLine = jsonNode.get("sourceLines").get(0);
        assertNotNull(sourceLine.get("value"));
        assertEquals("source line value", sourceLine.get("value").asText());
    }

    public static InternalSection getInternalSection() {
        InternalLine internalLine =
                new InternalLineBuilder(InternalLineType.DR, "line value").build();

        EvidenceLine evidenceLine =
                new EvidenceLineBuilder()
                        .evidence("evidence value")
                        .creationDate(LocalDate.of(2018, 12, 25))
                        .curator("curator value")
                        .build();

        SourceLine sourceLine = new SourceLineBuilder("source line value").build();

        return new InternalSectionBuilder()
                .evidenceLinesAdd(evidenceLine)
                .internalLinesAdd(internalLine)
                .sourceLinesAdd(sourceLine)
                .build();
    }
}
