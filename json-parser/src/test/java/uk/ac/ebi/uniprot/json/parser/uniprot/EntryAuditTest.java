package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.builder.EntryAuditBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
/**
 *
 * @author lgonzales
 */
public class EntryAuditTest {

    @Test
    public void testEntryAudit() {
        EntryAudit entryAudit = getEntryAudit();
        ValidateJson.verifyJsonRoundTripParser(entryAudit);
        ValidateJson.verifyEmptyFields(entryAudit);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(entryAudit);
        assertEquals("2015-08-02",jsonNode.get("firstPublicDate").asText());
        assertEquals("2016-04-24",jsonNode.get("lastAnnotationUpdateDate").asText());
        assertEquals("2017-01-21",jsonNode.get("lastSequenceUpdateDate").asText());
        assertEquals(20,jsonNode.get("entryVersion").asInt());
        assertEquals(5,jsonNode.get("sequenceVersion").asInt());
    }

    public static EntryAudit getEntryAudit() {
        LocalDate firstPublicDate = LocalDate.of(2015, Month.AUGUST, 2);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2016, Month.APRIL, 24);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2017, Month.JANUARY, 21);
        int entryVersion = 20;
        int sequenceVersion = 5;
        return new EntryAuditBuilder()
                .firstPublic(firstPublicDate)
                .lastAnnotationUpdate(lastAnnotationUpdateDate)
                .lastSequenceUpdate(lastSequenceUpdateDate)
                .entryVersion(entryVersion)
                .sequenceVersion(sequenceVersion)
                .build();
    }

}
