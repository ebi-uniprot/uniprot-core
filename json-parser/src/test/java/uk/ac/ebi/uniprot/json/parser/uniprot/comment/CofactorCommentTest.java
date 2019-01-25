package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
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
public class CofactorCommentTest {

    @Test
    public void testCofactorSimple() {

        CofactorComment comment = new CofactorCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("COFACTOR",jsonNode.get("commentType").asText());
    }


    @Test
    public void testCofactorComplete() {
        CofactorComment comment = getCofactorComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("COFACTOR",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("molecule",jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("cofactors"));
        assertEquals(1,jsonNode.get("cofactors").size());
        JsonNode cofactor = jsonNode.get("cofactors").get(0);
        assertNotNull(cofactor.get("name"));
        assertEquals("Cofactor Name",cofactor.get("name").asText());
        assertNotNull(cofactor.get("evidences"));
        assertEquals(1,cofactor.get("evidences").size());
        ValidateJson.validateEvidence(cofactor.get("evidences").get(0),"ECO:0000256","PIRNR","PIRNR001361");
        assertNotNull(cofactor.get("cofactorReference"));
        JsonNode reactionReferences = cofactor.get("cofactorReference");
        assertNotNull(reactionReferences.get("databaseType"));
        assertEquals("ChEBI",reactionReferences.get("databaseType").asText());
        assertNotNull(reactionReferences.get("id"));
        assertEquals("CHEBI:314",reactionReferences.get("id").asText());

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1,jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(valueEvidence,"value2","ECO:0000256","PIRNR","PIRNR001361");



    }

    public static CofactorComment getCofactorComment() {
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceBuilder<CofactorReferenceType>()
                .databaseType(CofactorReferenceType.CHEBI)
                .id("ChEBI:3243")
                .build();
        Cofactor cofactor = new CofactorBuilder()
                .name("Cofactor Name")
                .reference(reference)
                .evidences(createEvidences())
                .build();
        List<Cofactor> cofactors = Collections.singletonList(cofactor);

        Note note = new NoteBuilder(createEvidenceValues()).build();
        return new CofactorCommentBuilder()
                .addCofactor(cofactor)
                .molecule("molecule")
                .note(note)
                .build();
    }


    private static List<Evidence> createEvidences() {
        return CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
    }

    private static List<EvidencedValue> createEvidenceValues() {
        return CreateUtils.createEvidencedValueList("value2","ECO:0000256|PIRNR:PIRNR001361");
    }
}
