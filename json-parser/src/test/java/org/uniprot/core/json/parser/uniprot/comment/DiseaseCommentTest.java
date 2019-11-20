package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.DiseaseReferenceType;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;
import org.uniprot.core.uniprot.comment.builder.DiseaseCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class DiseaseCommentTest {

    @Test
    void testDiseaseSimple() {

        DiseaseComment comment = new DiseaseCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISEASE", jsonNode.get("commentType").asText());
    }

    @Test
    void testDiseaseComplete() {
        DiseaseComment comment = getDiseaseComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISEASE", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("disease"));
        JsonNode disease = jsonNode.get("disease");
        assertNotNull(disease.get("diseaseId"));
        assertEquals("Disease Id", disease.get("diseaseId").asText());
        assertNotNull(disease.get("diseaseAccession"));
        assertEquals("Disease AC", disease.get("diseaseAccession").asText());
        assertNotNull(disease.get("acronym"));
        assertEquals("someAcron", disease.get("acronym").asText());
        assertNotNull(disease.get("description"));
        assertEquals("some description", disease.get("description").asText());

        assertNotNull(disease.get("evidences"));
        assertEquals(1, disease.get("evidences").size());
        ValidateJson.validateEvidence(
                disease.get("evidences").get(0), "ECO:0000256", "PIRNR", "PIRNR001362");

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1, jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(
                valueEvidence, "value2", "ECO:0000256", "PIRNR", "PIRNR001362");
    }

    public static DiseaseComment getDiseaseComment() {
        DiseaseBuilder builder = new DiseaseBuilder();
        DBCrossReference<DiseaseReferenceType> reference =
                new DBCrossReferenceBuilder<DiseaseReferenceType>()
                        .databaseType(DiseaseReferenceType.MIM)
                        .id("3124")
                        .build();
        Disease disease =
                builder.diseaseId("Disease Id")
                        .acronym("someAcron")
                        .description("some description")
                        .reference(reference)
                        .diseaseAc("Disease AC")
                        .addEvidence(CreateUtils.createEvidence("ECO:0000256|PIRNR:PIRNR001362"))
                        .build();

        Note note =
                new NoteBuilder(
                                CreateUtils.createEvidencedValueList(
                                        "value2", "ECO:0000256|PIRNR:PIRNR001362"))
                        .build();
        return new DiseaseCommentBuilder().molecule("Isoform 3").disease(disease).note(note).build();
    }
}
