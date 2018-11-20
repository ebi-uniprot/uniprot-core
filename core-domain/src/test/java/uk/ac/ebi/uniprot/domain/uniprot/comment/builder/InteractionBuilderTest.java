package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class InteractionBuilderTest {
    @Test
    public void testNewInstance() {
        InteractionBuilder builder1 = InteractionBuilder.newInstance();
        InteractionBuilder builder2 = InteractionBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1== builder2);
    }
    
    
    @Test
    public void testSetInteractionType() {
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
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
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
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
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
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
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractor("first1"))
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
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractor("first1"))
                .secondInteractor(InteractionBuilder.createInteractor("first2"))
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
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractor("first1"))
                .secondInteractor(InteractionBuilder.createInteractor("first2"))
                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("P12345"))
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
        InteractionBuilder builder = InteractionCommentBuilder.newInteractionBuilder();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractor("first1"))
                .secondInteractor(InteractionBuilder.createInteractor("first2"))
                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("P12345"))
                .build();
        List<Interaction> interactions = Arrays.asList(interaction);
        InteractionCommentBuilder commentBuilder = InteractionCommentBuilder.newInstance();
        
        InteractionComment comment = 
                commentBuilder.interactions(interactions)
                .build();
        assertEquals(interactions, comment.getInteractions());
        assertEquals(CommentType.INTERACTION, comment.getCommentType());
        TestHelper.verifyJson(interaction);
    }

    @Test
    public void testCreateInteractor() {
       Interactor interAcc = InteractionBuilder.createInteractor("some Id");
       assertEquals("some Id", interAcc.getValue());
    }

}
