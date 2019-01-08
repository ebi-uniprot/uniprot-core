package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.ArrayList;
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

        CofactorComment comment = CofactorCommentBuilder.newInstance().build();
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
        CofactorReferenceType type = CofactorReferenceType.CHEBI;
        String referenceId = "CHEBI:314";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(type, referenceId);

        Cofactor cofactor = CofactorCommentBuilder.createCofactor("Cofactor Name", reference, createEvidences());
        List<Cofactor> cofactors = Collections.singletonList(cofactor);

        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        return CofactorCommentBuilder.newInstance()
                .cofactors(cofactors)
                .molecule("molecule")
                .note(note)
                .build();
    }


    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    private static List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", createEvidences()));
        return evidencedValues;
    }
}
