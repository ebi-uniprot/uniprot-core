package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractorAccession;
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
        assertEquals(InteractionType.BINARY, interaction.getInteractionType());
        assertNull(interaction.getInteractionGeneName());
        assertNull(interaction.getInteractorUniProtAccession());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    public void testSetGeneName() {
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .build();
        assertEquals(InteractionType.BINARY, interaction.getInteractionType());
        assertEquals("gn22", interaction.getInteractionGeneName());
        assertNull(interaction.getInteractorUniProtAccession());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    public void testSetNumberOfExperiments() {
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .build();
        assertEquals(InteractionType.BINARY, interaction.getInteractionType());
        assertEquals("gn22", interaction.getInteractionGeneName());
        assertNull(interaction.getInteractorUniProtAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertNull(interaction.getFirstInteractor());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    public void testSetFirstInteractor() {
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractorAccession("first1"))
                .build();
        assertEquals(InteractionType.BINARY, interaction.getInteractionType());
        assertEquals("gn22", interaction.getInteractionGeneName());
        assertNull(interaction.getInteractorUniProtAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());
        assertNull(interaction.getSecondInteractor());
    }

    @Test
    public void testSetSecondInteractor() {
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractorAccession("first1"))
                .secondInteractor(InteractionBuilder.createInteractorAccession("first2"))
                .build();
        assertEquals(InteractionType.BINARY, interaction.getInteractionType());
        assertEquals("gn22", interaction.getInteractionGeneName());
        assertNull(interaction.getInteractorUniProtAccession());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());
        
        assertEquals("first2", interaction.getSecondInteractor().getValue());
    }

    @Test
    public void testSetUniProtAccession() {
        InteractionBuilder builder = InteractionBuilder.newInstance();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractorAccession("first1"))
                .secondInteractor(InteractionBuilder.createInteractorAccession("first2"))
                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("P12345"))
                .build();
        assertEquals(InteractionType.BINARY, interaction.getInteractionType());
        assertEquals("gn22", interaction.getInteractionGeneName());
        assertEquals("P12345", interaction.getInteractorUniProtAccession().getValue());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals("first1", interaction.getFirstInteractor().getValue());
        
        assertEquals("first2", interaction.getSecondInteractor().getValue());
    }

    @Test
    public void testCreateInteractionComment() {
        InteractionBuilder builder = InteractionCommentBuilder.newInteractionBuilder();
        Interaction interaction =builder.interactionType(InteractionType.BINARY)
                .geneName("gn22")
                .numberOfExperiments(3)
                .firstInteractor(InteractionBuilder.createInteractorAccession("first1"))
                .secondInteractor(InteractionBuilder.createInteractorAccession("first2"))
                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("P12345"))
                .build();
        List<Interaction> interactions = Arrays.asList(interaction);
        InteractionCommentBuilder commentBuilder = InteractionCommentBuilder.newInstance();
        
        InteractionComment comment = 
                commentBuilder.interactions(interactions)
                .build();
        assertEquals(interactions, comment.getInteractions());
        assertEquals(CommentType.INTERACTION, comment.getCommentType());
    }

    @Test
    public void testCreateInteractorAccession() {
       InteractorAccession interAcc = InteractionBuilder.createInteractorAccession("some Id");
       assertEquals("some Id", interAcc.getValue());
    }

}
