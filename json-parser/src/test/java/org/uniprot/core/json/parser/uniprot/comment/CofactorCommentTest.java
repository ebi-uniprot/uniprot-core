package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorComment;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.impl.CofactorBuilder;
import org.uniprot.core.uniprotkb.comment.impl.CofactorCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.Collections;
import java.util.List;

/** @author lgonzales */
public class CofactorCommentTest {

    @Test
    void testCofactorSimple() {

        CofactorComment comment = new CofactorCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("COFACTOR", jsonNode.get("commentType").asText());
    }

    @Test
    void testCofactorComplete() {
        CofactorComment comment = getCofactorComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("COFACTOR", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("molecule", jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("cofactors"));
        assertEquals(1, jsonNode.get("cofactors").size());
        JsonNode cofactor = jsonNode.get("cofactors").get(0);
        assertNotNull(cofactor.get("name"));
        assertEquals("Cofactor Name", cofactor.get("name").asText());
        assertNotNull(cofactor.get("evidences"));
        assertEquals(1, cofactor.get("evidences").size());
        ValidateJson.validateEvidence(
                cofactor.get("evidences").get(0), "ECO:0000256", "PIRNR", "PIRNR001361");
        assertNotNull(cofactor.get("cofactorCrossReference"));
        JsonNode cofactorCrossReference = cofactor.get("cofactorCrossReference");
        assertNotNull(cofactorCrossReference.get("database"));
        assertEquals("ChEBI", cofactorCrossReference.get("database").asText());
        assertNotNull(cofactorCrossReference.get("id"));
        assertEquals("CHEBI:314", cofactorCrossReference.get("id").asText());

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1, jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(
                valueEvidence, "value2", "ECO:0000256", "PIRNR", "PIRNR001361");
    }

    public static CofactorComment getCofactorComment() {
        CrossReference<CofactorDatabase> reference =
                new CrossReferenceBuilder<CofactorDatabase>()
                        .database(CofactorDatabase.CHEBI)
                        .id("CHEBI:314")
                        .build();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name("Cofactor Name")
                        .cofactorCrossReference(reference)
                        .evidencesSet(createEvidences())
                        .build();
        List<Cofactor> cofactors = Collections.singletonList(cofactor);

        Note note = new NoteBuilder(createEvidenceValues()).build();
        return new CofactorCommentBuilder()
                .cofactorsAdd(cofactor)
                .molecule("molecule")
                .note(note)
                .build();
    }

    private static List<Evidence> createEvidences() {
        return CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
    }

    private static List<EvidencedValue> createEvidenceValues() {
        return CreateUtils.createEvidencedValueList("value2", "ECO:0000256|PIRNR:PIRNR001361");
    }
}
