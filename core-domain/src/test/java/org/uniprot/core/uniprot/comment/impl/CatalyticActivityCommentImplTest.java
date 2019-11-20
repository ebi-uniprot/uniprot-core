package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createPhyReactions;
import static org.uniprot.core.ObjectsForTests.createReaction;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.builder.CatalyticActivityCommentBuilder;

class CatalyticActivityCommentImplTest {
    @Test
    void testAll() {
        Reaction reaction = createReaction();
        List<PhysiologicalReaction> phyReactions = createPhyReactions();
        String molecule ="Isoform 2";
        CatalyticActivityComment comment = new CatalyticActivityCommentImpl(molecule, reaction, phyReactions);
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
        assertEquals(reaction, comment.getReaction());
        assertEquals(phyReactions, comment.getPhysiologicalReactions());
        assertEquals(molecule, comment.getMolecule());
    }

    @Test
    void testOnlyReaction() {
        Reaction reaction = createReaction();

        CatalyticActivityComment comment =
                new CatalyticActivityCommentImpl(null, reaction, Collections.emptyList());
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
        assertEquals(reaction, comment.getReaction());
        assertTrue(comment.getPhysiologicalReactions().isEmpty());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CatalyticActivityComment obj = new CatalyticActivityCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CatalyticActivityComment impl =
                new CatalyticActivityCommentImpl("Isoform 3", createReaction(), createPhyReactions());
        CatalyticActivityComment obj = new CatalyticActivityCommentBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
