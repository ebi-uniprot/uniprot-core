package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import uk.ac.ebi.uniprot.json.parser.uniprot.CreateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class DiseaseCommentTest {


    @Test
    public void testDiseaseSimple() {

        DiseaseComment comment = new DiseaseCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISEASE",jsonNode.get("commentType").asText());

    }

    @Test
    public void testDiseaseComplete() {
        DiseaseComment comment = getDiseaseComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISEASE",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("disease"));
        JsonNode disease = jsonNode.get("disease");
        assertNotNull(disease.get("diseaseId"));
        assertEquals("Disease Id",disease.get("diseaseId").asText());
        assertNotNull(disease.get("diseaseAccession"));
        assertEquals("Disease AC",disease.get("diseaseAccession").asText());
        assertNotNull(disease.get("acronym"));
        assertEquals("someAcron",disease.get("acronym").asText());
        assertNotNull(disease.get("description"));
        assertEquals("some description",disease.get("description").asText());

        assertNotNull(disease.get("evidences"));
        assertEquals(1,disease.get("evidences").size());
        ValidateJson.validateEvidence(disease.get("evidences").get(0),"ECO:0000256","PIRNR","PIRNR001362");

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1,jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(valueEvidence,"value2","ECO:0000256","PIRNR","PIRNR001362");
    }

    public static DiseaseComment getDiseaseComment() {
        DiseaseBuilder builder = new DiseaseBuilder();
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceBuilder<DiseaseReferenceType>()
                .databaseType(DiseaseReferenceType.MIM)
                .id("3124")
                .build();
        Disease disease = builder.diseaseId("Disease Id")
                .acronym("someAcron")
                .description("some description")
                .reference(reference)
                .diseaseAc("Disease AC")
                .addEvidence(CreateUtils.createEvidence("ECO:0000256|PIRNR:PIRNR001362"))
                .build();

        Note note = new NoteBuilder(
                CreateUtils.createEvidencedValueList("value2","ECO:0000256|PIRNR:PIRNR001362")).build();
        return new DiseaseCommentBuilder()
                .disease(disease)
                .note(note)
                .build();
    }

}
