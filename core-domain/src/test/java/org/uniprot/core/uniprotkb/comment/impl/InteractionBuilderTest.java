package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.Interactant;

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
    void testSetNumberOfExperiments() {
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder
                        .numberOfExperiments(3)
                        .build();
      
        assertEquals(3, interaction.getNumberOfExperiments());
        assertNull(interaction.getInteractantOne());
        assertNull(interaction.getInteractantTwo());
    }

    @Test
    void testSetFirstInteractor() {
    	Interactant interactor1 =
    			new InteractantBuilder()
    			.uniProtKBAccession("P12345").build();
    	
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder
                        .numberOfExperiments(3)
                        .interactantOne(interactor1)
                        .build();
    
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals(interactor1, interaction.getInteractantOne());
        assertNull(interaction.getInteractantTwo());
        assertFalse(interaction.isOrganismsDiffer());
    }

    @Test
    void testSetSecondInteractor() {
    	Interactant interactor1 =
    			new InteractantBuilder()
    			.uniProtKBAccession("P12345").build();
    	Interactant interactor2 =
    			new InteractantBuilder()
    			.uniProtKBAccession("P12346")
    			.geneName("gen1").build();
    	
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder
                        .numberOfExperiments(3)
                        .interactantOne(interactor1)
                        .interactantTwo(interactor2)
                        .build();
      
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals(interactor1, interaction.getInteractantOne());

        assertEquals(interactor2, interaction.getInteractantTwo());
        assertFalse(interaction.isOrganismsDiffer());
    }
    @Test
    void testSetIsXeno() {
    	Interactant interactor1 =
    			new InteractantBuilder()
    			.uniProtKBAccession("P12345").build();
    	Interactant interactor2 =
    			new InteractantBuilder()
    			.uniProtKBAccession("P12346")
    			.geneName("gen1").build();
    	
        InteractionBuilder builder = new InteractionBuilder();
        Interaction interaction =
                builder
                        .numberOfExperiments(3)
                        .interactantOne(interactor1)
                        .interactantTwo(interactor2)
                        .isOrganismDiffer(true)
                        .build();
      
        assertEquals(3, interaction.getNumberOfExperiments());
        assertEquals(interactor1, interaction.getInteractantOne());

        assertEquals(interactor2, interaction.getInteractantTwo());
        assertTrue(interaction.isOrganismsDiffer());
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
