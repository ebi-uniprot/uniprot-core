package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.Interactor;

class InteractionCommentImplTest {
    private List<Interaction> interactions ;
         
    @BeforeEach
    void setup() {
    	Interactor interactor1 = new InteractorBuilder().uniProtAccession("P12345")
    			.intActId("EBI-12498321")
    			.build();
    	Interactor interactor2= new InteractorBuilder().uniProtAccession("P12346")
    			.geneName("gene1")
    			.intActId("EBI-12498322")
    			.build();
    	Interactor interactor3 = new InteractorBuilder().chainId("P_123456")
    			.intActId("EBI-12498323")
    			.build();
    	Interactor interactor4 = new InteractorBuilder().chainId("P_123457")
    			.uniProtAccession("P12347")
    			.intActId("EBI-12498324")
    			.build();
    	Interaction interaction1 = new InteractionBuilder()
    			.firstInteractor(interactor1)
    			.secondInteractor(interactor2)
    			.numberOfExperiments(4)
    			.build();
    	Interaction interaction2 = new InteractionBuilder()
    			.firstInteractor(interactor3)
    			.secondInteractor(interactor4)
    			.numberOfExperiments(2)
    			.isXeno(true)
    			.build();
    	
    	interactions = Arrays.asList(interaction1, interaction2);
    }
    @Test
    void testInteractionCommentImpl() {
        InteractionComment comment = new InteractionCommentImpl(interactions);
        assertEquals(interactions, comment.getInteractions());
        assertEquals(CommentType.INTERACTION, comment.getCommentType());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        InteractionComment obj = new InteractionCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        InteractionComment impl = new InteractionCommentImpl(interactions);
        InteractionComment obj = InteractionCommentBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
