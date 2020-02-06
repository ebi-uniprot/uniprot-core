package org.uniprot.core.uniprot.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.builder.ElectronicArticleBuilder;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;

class UniProtReferenceBuilderTest {

    @Test
    void canSetCitation() {
        ElectronicArticle ea = new ElectronicArticleBuilder().journalName("abc").build();
        UniProtReference reference = new UniProtReferenceBuilder().citation(ea).build();
        assertEquals(ea, reference.getCitation());
        assertTrue(reference.hasCitation());
    }

    @Test
    void canSetReferencePositions() {
        List<String> refPositions = Collections.singletonList("ref");
        UniProtReference reference =
                new UniProtReferenceBuilder().referencePositionsSet(refPositions).build();
        assertEquals(refPositions, reference.getReferencePositions());
    }

    @Test
    void nullWillIgnoreInPositions() {
        UniProtReference reference =
                new UniProtReferenceBuilder().referencePositionsAdd(null).build();
        assertTrue(reference.getReferencePositions().isEmpty());
    }

    @Test
    void canAddSinglePosition() {
        UniProtReference reference =
                new UniProtReferenceBuilder().referencePositionsAdd("ref").build();
        assertFalse(reference.getReferencePositions().isEmpty());
        assertEquals("ref", reference.getReferencePositions().get(0));
        assertTrue(reference.hasReferencePositions());
    }

    @Test
    void canSetComments() {
        List<ReferenceComment> refComment =
                Collections.singletonList(new ReferenceCommentBuilder().build());
        UniProtReference reference =
                new UniProtReferenceBuilder().referenceCommentsSet(refComment).build();
        assertEquals(refComment, reference.getReferenceComments());
    }

    @Test
    void nullWillIgnoreInComments() {
        UniProtReference reference =
                new UniProtReferenceBuilder().referenceCommentsAdd(null).build();
        assertTrue(reference.getReferenceComments().isEmpty());
    }

    @Test
    void canAddSingleComment() {
        ReferenceComment comment = new ReferenceCommentBuilder().build();
        UniProtReference reference =
                new UniProtReferenceBuilder().referenceCommentsAdd(comment).build();
        assertFalse(reference.getReferenceComments().isEmpty());
        assertEquals(comment, reference.getReferenceComments().get(0));
        assertTrue(reference.hasReferenceComments());
    }

    @Test
    void canSetEvidences() {
        List<Evidence> evidences = Collections.singletonList(new EvidenceBuilder().build());
        UniProtReference reference = new UniProtReferenceBuilder().evidencesSet(evidences).build();
        assertEquals(evidences, reference.getEvidences());
    }

    @Test
    void nullWillIgnoreInEvidence() {
        UniProtReference reference = new UniProtReferenceBuilder().evidencesAdd(null).build();
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void canAddSingleEvidence() {
        UniProtReference reference =
                new UniProtReferenceBuilder().evidencesAdd(new EvidenceBuilder().build()).build();
        assertTrue(reference.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtReference obj = new UniProtReferenceBuilder().build();
        UniProtReferenceBuilder builder = UniProtReferenceBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtReference obj = new UniProtReferenceBuilder().build();
        UniProtReference obj2 = new UniProtReferenceBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
