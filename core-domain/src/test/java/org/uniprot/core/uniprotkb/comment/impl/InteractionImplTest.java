package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

class InteractionImplTest {
    private Interactant interactant1;
    private Interactant interactant2;

    @BeforeEach
    void setup() {
        interactant1 =
                new InteractantImpl(
                        new UniProtKBAccessionBuilder("P12346").build(), "", "", "EBI-1223708");

        interactant2 =
                new InteractantImpl(
                        new UniProtKBAccessionBuilder("P12345").build(),
                        "gene1",
                        "P_1234",
                        "EBI-1223708");
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Interaction obj = new InteractionImpl();
        assertNotNull(obj);
    }

    @Test
    void completeObjConstructor() {
        Interaction interaction = new InteractionImpl(interactant1, interactant2, 3, false);
        assertEquals(interactant1, interaction.getInteractantOne());
        assertEquals(interactant2, interaction.getInteractantTwo());
        assertEquals(3, interaction.getNumberOfExperiments());
        assertFalse(interaction.isOrganismsDiffer());
    }

    @Test
    void completeObjHasMethods() {
        Interaction interaction = new InteractionImpl(interactant1, interactant2, 3, true);
        assertTrue(interaction.hasInteractantOne());
        assertTrue(interaction.hasInteractantTwo());
        assertTrue(interaction.hasNumberOfExperiments());
        assertTrue(interaction.isOrganismsDiffer());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Interaction interaction = new InteractionImpl(interactant1, interactant2, 5, true);
        Interaction obj = InteractionBuilder.from(interaction).build();
        assertEquals(interaction, obj);
        assertEquals(interaction.hashCode(), obj.hashCode());
    }
}
