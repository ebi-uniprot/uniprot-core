package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APIsoformBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.IsoformNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidencedValueBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import uk.ac.ebi.uniprot.json.parser.uniprot.CreateUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class AlternativeProductsCommentTest {

    @Test
    public void testAlternativeProductsCommentSimple() {

        AlternativeProductsComment comment = new APCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("ALTERNATIVE PRODUCTS",jsonNode.get("commentType").asText());
    }

    @Test
    public void testAlternativeProductsCommentComplete() {
        AlternativeProductsComment comment = getAlternativeProductsComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("ALTERNATIVE PRODUCTS",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("events"));
        assertEquals(1,jsonNode.get("events").size());
        assertEquals("Alternative initiation",jsonNode.get("events").get(0).asText());

        assertNotNull(jsonNode.get("isoforms"));
        assertEquals(1,jsonNode.get("isoforms").size());
        JsonNode isoforms = jsonNode.get("isoforms").get(0);

        assertNotNull(isoforms.get("name"));
        ValidateJson.validateValueEvidence(isoforms.get("name"),"name","ECO:0000255","PROSITE-ProRule","PRU10028");

        assertNotNull(isoforms.get("synonyms"));
        assertEquals(1,isoforms.get("synonyms").size());
        ValidateJson.validateValueEvidence(isoforms.get("synonyms").get(0),"syn value","ECO:0000255","PROSITE-ProRule","PRU10028");

        assertNotNull(isoforms.get("note"));
        assertNotNull(isoforms.get("note").get("texts"));
        assertEquals(1,isoforms.get("note").get("texts").size());
        JsonNode noteIsoform = isoforms.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(noteIsoform,"value1","ECO:0000255","PROSITE-ProRule","PRU10028");

        assertNotNull(isoforms.get("isoformIds"));
        assertEquals(1,isoforms.get("isoformIds").size());
        assertEquals("isoID1",isoforms.get("isoformIds").get(0).asText());

        assertNotNull(isoforms.get("sequenceIds"));
        assertEquals(1,isoforms.get("sequenceIds").size());
        assertEquals("SequenceID",isoforms.get("sequenceIds").get(0).asText());

        assertNotNull(isoforms.get("isoformSequenceStatus"));
        assertEquals("described",isoforms.get("isoformSequenceStatus").asText());

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1,jsonNode.get("note").get("texts").size());
        JsonNode note = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(note,"value1","ECO:0000255","PROSITE-ProRule","PRU10028");

    }

    public static AlternativeProductsComment getAlternativeProductsComment() {
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10028");
        List<IsoformName> isoformSynonyms = Collections.singletonList(new IsoformNameBuilder("syn value", evidences).build());
        List<EvidencedValue> evidencedValues = Collections.singletonList(new EvidencedValueBuilder("value1", evidences).build());
        Note note = new NoteBuilder(evidencedValues).build();
        List<String> isoformIds = Collections.singletonList("isoID1");
        IsoformName name = new IsoformNameBuilder("name", evidences).build();
        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform = isoformBuilder.name(name)
                .synonyms(isoformSynonyms)
                .ids(isoformIds)
                .sequenceStatus(IsoformSequenceStatus.DESCRIBED)
                .note(note)
                .sequenceIds(Collections.singletonList("SequenceID"))
                .build();

        List<APEventType> events = Collections.singletonList(APEventType.ALTERNATIVE_INITIATION);
        List<APIsoform> isoforms = Collections.singletonList(apIsoform);


        return new APCommentBuilder()
                .events(events)
                .isoforms(isoforms)
                .note(note)
                .build();
    }
}
