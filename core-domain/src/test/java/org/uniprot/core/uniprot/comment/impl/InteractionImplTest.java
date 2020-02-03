package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.Interactor;
import org.uniprot.core.uniprot.comment.builder.InteractionBuilder;

class InteractionImplTest {

    private Interaction impl =
            new InteractionImpl(
                    InteractionType.BINARY,
                    new UniProtAccessionBuilder("ac").build(),
                    "gene",
                    12,
                    new InteractionImpl.InteractorImpl("1st"),
                    new InteractionImpl.InteractorImpl("2nd"));

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Interaction obj = new InteractionImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_InteractorImpl() {
        Interactor obj = new InteractionImpl.InteractorImpl();
        assertNotNull(obj);
    }

    @Test
    void completeObjHasMethods() {
        assertTrue(impl.hasFirstInteractor());
        assertTrue(impl.hasGeneName());
        assertTrue(impl.hasNumberOfExperiments());
        assertTrue(impl.hasSecondInteractor());
        assertTrue(impl.hasUniProtAccession());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Interaction obj = InteractionBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
