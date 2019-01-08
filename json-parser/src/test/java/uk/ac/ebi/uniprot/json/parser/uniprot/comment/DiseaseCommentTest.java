package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
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
public class DiseaseCommentTest {


    @Test
    public void testDiseaseSimple() {

        DiseaseComment comment = DiseaseCommentBuilder.newInstance().build();
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
        DiseaseBuilder builder = DiseaseCommentBuilder.newDiseaseBuilder();
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(DiseaseReferenceType.MIM, "3124");

        Disease disease = builder.diseaseId("Disease Id")
                .acronym("someAcron")
                .description("some description")
                .reference(reference)
                .diseaseAc("Disease AC")
                .evidences(createEvidences())
                .build();

        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        return DiseaseCommentBuilder.newInstance()
                .disease(disease)
                .note(note)
                .build();
    }

    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001362"));
        return evidences;
    }

    private static List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", createEvidences()));
        return evidencedValues;
    }
}
