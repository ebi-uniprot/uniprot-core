package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.Interactor;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

class InteractionImplTest {
	private Interactor interactor1;
	private Interactor interactor2;
	
    @BeforeEach
    void setup() {
    	 interactor1 =
   			  new InteractorImpl(
   					  new UniProtKBAccessionBuilder("P12346").build(),
   					  "", "", "EBI-1223708");
    	
    	 interactor2 =
    			  new InteractorImpl(
    					  new UniProtKBAccessionBuilder("P12345").build(),
    					  "gene1", "P_1234", "EBI-1223708");
    	
    }
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Interaction obj = new InteractionImpl();
        assertNotNull(obj);
    }

    
    @Test
    void completeObjConstructor() {
    	Interaction interaction = new InteractionImpl(
    		      interactor1, interactor2, 3, false);
    	assertEquals(interactor1, interaction.getFirstInteractor())  ;
    	assertEquals(interactor2, interaction.getSecondInteractor());
    	assertEquals(3, interaction.getNumberOfExperiments());
    	assertFalse(interaction.isXeno());
        
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
    	Interaction interaction = new InteractionImpl(
  		      interactor1, interactor2, 5, true);
        Interaction obj = InteractionBuilder.from(interaction).build();
        assertEquals(interaction, obj);
        assertEquals(interaction.hashCode(), obj.hashCode());
    }
}
