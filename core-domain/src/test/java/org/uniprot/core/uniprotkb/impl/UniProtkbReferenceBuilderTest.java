package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.UniProtkbReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;

class UniProtkbReferenceBuilderTest {

    @Test
    void canSetCitation() {
        ElectronicArticle ea = new ElectronicArticleBuilder().journalName("abc").build();
        UniProtkbReference reference = new UniProtkbReferenceBuilder().citation(ea).build();
        assertEquals(ea, reference.getCitation());
        assertTrue(reference.hasCitation());
    }

    @Test
    void canSetReferencePositions() {
        List<String> refPositions = Collections.singletonList("ref");
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().referencePositionsSet(refPositions).build();
        assertEquals(refPositions, reference.getReferencePositions());
    }

    @Test
    void nullWillIgnoreInPositions() {
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().referencePositionsAdd(null).build();
        assertTrue(reference.getReferencePositions().isEmpty());
    }

    @Test
    void canAddSinglePosition() {
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().referencePositionsAdd("ref").build();
        assertFalse(reference.getReferencePositions().isEmpty());
        assertEquals("ref", reference.getReferencePositions().get(0));
        assertTrue(reference.hasReferencePositions());
    }

    @Test
    void canSetComments() {
        List<ReferenceComment> refComment =
                Collections.singletonList(new ReferenceCommentBuilder().build());
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().referenceCommentsSet(refComment).build();
        assertEquals(refComment, reference.getReferenceComments());
    }

    @Test
    void nullWillIgnoreInComments() {
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().referenceCommentsAdd(null).build();
        assertTrue(reference.getReferenceComments().isEmpty());
    }

    @Test
    void canAddSingleComment() {
        ReferenceComment comment = new ReferenceCommentBuilder().build();
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().referenceCommentsAdd(comment).build();
        assertFalse(reference.getReferenceComments().isEmpty());
        assertEquals(comment, reference.getReferenceComments().get(0));
        assertTrue(reference.hasReferenceComments());
    }

    @Test
    void canSetEvidences() {
        List<Evidence> evidences = Collections.singletonList(new EvidenceBuilder().build());
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().evidencesSet(evidences).build();
        assertEquals(evidences, reference.getEvidences());
    }

    @Test
    void nullWillIgnoreInEvidence() {
        UniProtkbReference reference = new UniProtkbReferenceBuilder().evidencesAdd(null).build();
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void canAddSingleEvidence() {
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().evidencesAdd(new EvidenceBuilder().build()).build();
        assertTrue(reference.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtkbReference obj = new UniProtkbReferenceBuilder().build();
        UniProtkbReferenceBuilder builder = UniProtkbReferenceBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtkbReference obj = new UniProtkbReferenceBuilder().build();
        UniProtkbReference obj2 = new UniProtkbReferenceBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
