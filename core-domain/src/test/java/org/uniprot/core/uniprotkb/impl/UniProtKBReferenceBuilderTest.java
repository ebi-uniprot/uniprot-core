package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;

class UniProtKBReferenceBuilderTest {

    @Test
    void canSetReferenceNumber() {
        int referenceNumber = 10;
        UniProtKBReference reference = new UniProtKBReferenceBuilder().referenceNumber(referenceNumber).build();
        assertEquals(referenceNumber, reference.getReferenceNumber());
        assertTrue(reference.hasReferenceNumber());
    }

    @Test
    void canSetCitation() {
        ElectronicArticle ea = new ElectronicArticleBuilder().journalName("abc").build();
        UniProtKBReference reference = new UniProtKBReferenceBuilder().citation(ea).build();
        assertEquals(ea, reference.getCitation());
        assertTrue(reference.hasCitation());
        assertFalse(reference.hasReferenceNumber());
    }

    @Test
    void canSetReferencePositions() {
        List<String> refPositions = Collections.singletonList("ref");
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().referencePositionsSet(refPositions).build();
        assertEquals(refPositions, reference.getReferencePositions());
    }

    @Test
    void nullWillIgnoreInPositions() {
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().referencePositionsAdd(null).build();
        assertTrue(reference.getReferencePositions().isEmpty());
    }

    @Test
    void canAddSinglePosition() {
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().referencePositionsAdd("ref").build();
        assertFalse(reference.getReferencePositions().isEmpty());
        assertEquals("ref", reference.getReferencePositions().get(0));
        assertTrue(reference.hasReferencePositions());
    }

    @Test
    void canSetComments() {
        List<ReferenceComment> refComment =
                Collections.singletonList(new ReferenceCommentBuilder().build());
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().referenceCommentsSet(refComment).build();
        assertEquals(refComment, reference.getReferenceComments());
    }

    @Test
    void nullWillIgnoreInComments() {
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().referenceCommentsAdd(null).build();
        assertTrue(reference.getReferenceComments().isEmpty());
    }

    @Test
    void canAddSingleComment() {
        ReferenceComment comment = new ReferenceCommentBuilder().build();
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().referenceCommentsAdd(comment).build();
        assertFalse(reference.getReferenceComments().isEmpty());
        assertEquals(comment, reference.getReferenceComments().get(0));
        assertTrue(reference.hasReferenceComments());
    }

    @Test
    void canSetEvidences() {
        List<Evidence> evidences = Collections.singletonList(new EvidenceBuilder().build());
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().evidencesSet(evidences).build();
        assertEquals(evidences, reference.getEvidences());
    }

    @Test
    void nullWillIgnoreInEvidence() {
        UniProtKBReference reference = new UniProtKBReferenceBuilder().evidencesAdd(null).build();
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void canAddSingleEvidence() {
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().evidencesAdd(new EvidenceBuilder().build()).build();
        assertTrue(reference.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBReference obj = new UniProtKBReferenceBuilder().build();
        UniProtKBReferenceBuilder builder = UniProtKBReferenceBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtKBReference obj = new UniProtKBReferenceBuilder().build();
        UniProtKBReference obj2 = new UniProtKBReferenceBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
