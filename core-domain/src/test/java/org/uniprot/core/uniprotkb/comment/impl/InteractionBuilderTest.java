package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.InteractionType;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

class InteractionBuilderTest {
    @Test
    void testNewInstance() {
        InteractionBuilder builder1 = new InteractionBuilder();
        InteractionBuilder builder2 = new InteractionBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetInteractionType() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY).build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertNull(interaction.getGeneName());
        assertNull(interaction.getUniProtkbAccession());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    void testSetGeneName() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.BINARY).geneName("gn22").build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtkbAccession());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    void testSetNumberOfExperiments() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.BINARY)
                        .geneName("gn22")
                        .numberOfExperiments(3)
                        .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtkbAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    void testSetFirstInteractor() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.BINARY)
                        .geneName("gn22")
                        .numberOfExperiments(3)
                        .firstInteractor("first1")
                        .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtkbAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    void testSetSecondInteractor() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.BINARY)
                        .geneName("gn22")
                        .numberOfExperiments(3)
                        .firstInteractor("first1")
                        .secondInteractor("first2")
                        .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtkbAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());

        assertEquals("first2", interaction.getSecondInteractor().getValue());
    }

    @Test
    void testSetUniProtAccession() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.BINARY)
                        .geneName("gn22")
                        .numberOfExperiments(3)
                        .firstInteractor("first1")
                        .secondInteractor("first2")
                        .uniProtAccession("P12345")
                        .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertEquals("P12345", interaction.getUniProtkbAccession().getValue());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());

        assertEquals("first2", interaction.getSecondInteractor().getValue());
    }

    @Test
    void testCreateInteractionComment() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder.interactionType(InteractionType.BINARY)
                        .geneName("gn22")
                        .numberOfExperiments(3)
                        .firstInteractor("first1")
                        .secondInteractor("first2")
                        .uniProtAccession("P12345")
                        .build();
        List<Interaction> interactions = Collections.singletonList(interaction);
        InteractionCommentBuilder commentBuilder = new InteractionCommentBuilder();

        InteractionComment comment = commentBuilder.interactionsSet(interactions).build();
        assertEquals(interactions, comment.getInteractions());
        assertEquals(CommentType.INTERACTION, comment.getCommentType());
    }

    @Test
    void canSetUniProtAccession() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("ac").build();
        Interaction obj = new InteractionBuilder().uniProtAccession(accession).build();
        assertEquals(accession, obj.getUniProtkbAccession());
    }

    @Test
    void canCreateBuilderFromInstance() {
        Interaction obj = new InteractionBuilder().build();
        InteractionBuilder builder = InteractionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Interaction obj = new InteractionBuilder().build();
        Interaction obj2 = new InteractionBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
