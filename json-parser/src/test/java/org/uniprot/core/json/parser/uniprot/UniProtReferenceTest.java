package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.citation.*;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.impl.ReferenceCommentBuilder;
import org.uniprot.core.uniprot.impl.UniProtReferenceBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class UniProtReferenceTest {

    @Test
    void testUniProtReferenceSimple() {
        UniProtReference uniprotReference = new UniProtReferenceBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(uniprotReference);
    }

    @Test
    void testUniProtReferenceComplete() {
        UniProtReference uniprotReference = getUniProtReference();

        ValidateJson.verifyJsonRoundTripParser(uniprotReference);
        ValidateJson.verifyEmptyFields(uniprotReference);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniprotReference);
        assertNotNull(
                jsonNode.get(
                        "citation")); // citation information is being tested in BooksTest class

        assertNotNull(jsonNode.get("referencePositions"));
        assertEquals(1, jsonNode.get("referencePositions").size());
        assertEquals("position 1", jsonNode.get("referencePositions").get(0).asText());

        assertNotNull(jsonNode.get("referenceComments"));
        assertEquals(1, jsonNode.get("referenceComments").size());
        JsonNode referenceComment = jsonNode.get("referenceComments").get(0);
        ValidateJson.validateValueEvidence(
                referenceComment, "reference comment value", "ECO:0000269", "PubMed", "11389730");
        assertNotNull(referenceComment.get("type"));
        assertEquals("PLASMID", referenceComment.get("type").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000269", "PubMed", "11389730");
    }

    public static List<UniProtReference> getUniProtReferences() {
        List<UniProtReference> uniProtReferences = new ArrayList<>();
        uniProtReferences.add(getUniProtReference(BookTest.getBook()));
        uniProtReferences.add(getUniProtReference(ElectronicArticleTest.getElectronicArticle()));
        uniProtReferences.add(getUniProtReference(JournalArticleTest.getJournalArticle()));
        uniProtReferences.add(getUniProtReference(PatentTest.getPatent()));
        uniProtReferences.add(getUniProtReference(SubmissionTest.getSubmission()));
        uniProtReferences.add(getUniProtReference(ThesisTest.getThesis()));
        uniProtReferences.add(getUniProtReference(UnpublishedTest.getUnpublished()));
        return uniProtReferences;
    }

    private static UniProtReference getUniProtReference() {
        return getUniProtReference(BookTest.getBook());
    }

    private static UniProtReference getUniProtReference(Citation citation) {
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        ReferenceComment referenceComment =
                new ReferenceCommentBuilder()
                        .type(ReferenceCommentType.PLASMID)
                        .value("reference comment value")
                        .evidencesSet(evidences)
                        .build();

        return new UniProtReferenceBuilder()
                .citation(citation)
                .referenceCommentsAdd(referenceComment)
                .referencePositionsAdd("position 1")
                .evidencesSet(evidences)
                .build();
    }
}
