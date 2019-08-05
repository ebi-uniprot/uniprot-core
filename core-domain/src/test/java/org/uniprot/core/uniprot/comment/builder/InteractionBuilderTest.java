package org.uniprot.core.uniprot.comment.builder;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.builder.InteractionBuilder;
import org.uniprot.core.uniprot.comment.builder.InteractionCommentBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class InteractionBuilderTest {
    @Test
    public void testNewInstance() {
        InteractionBuilder builder1 = new InteractionBuilder();
        InteractionBuilder builder2 = new InteractionBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetInteractionType() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY)
                .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertNull(interaction.getGeneName());
        assertNull(interaction.getUniProtAccession());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
        TestHelper.verifyJson(interaction);
    }

    @Test
    public void testSetGeneName() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtAccession());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
        TestHelper.verifyJson(interaction);
    }

    @Test
    public void testSetNumberOfExperiments() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
        TestHelper.verifyJson(interaction);
    }

    @Test
    public void testSetFirstInteractor() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor("first1")
                .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());
        assertNull(interaction.getSecondInteractor());
        TestHelper.verifyJson(interaction);
    }

    @Test
    public void testSetSecondInteractor() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor("first1")
                .secondInteractor("first2")
                .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertNull(interaction.getUniProtAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());

        assertEquals("first2", interaction.getSecondInteractor().getValue());
        TestHelper.verifyJson(interaction);
    }

    @Test
    public void testSetUniProtAccession() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor("first1")
                .secondInteractor("first2")
                .uniProtAccession("P12345")
                .build();
        assertEquals(InteractionType.BINARY, interaction.getType());
        assertEquals("gn22", interaction.getGeneName());
        assertEquals("P12345", interaction.getUniProtAccession().getValue());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());

        assertEquals("first2", interaction.getSecondInteractor().getValue());
        TestHelper.verifyJson(interaction);
    }

    @Test
    public void testCreateInteractionComment() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction = builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor("first1")
                .secondInteractor("first2")
                .uniProtAccession("P12345")
                .build();
        List<Interaction> interactions = Arrays.asList(interaction);
        InteractionCommentBuilder commentBuilder = new InteractionCommentBuilder();

        InteractionComment comment =
                commentBuilder.interactions(interactions)
                        .build();
        assertEquals(interactions, comment.getInteractions());
        assertEquals(CommentType.INTERACTION, comment.getCommentType());
        TestHelper.verifyJson(interaction);
    }
}
