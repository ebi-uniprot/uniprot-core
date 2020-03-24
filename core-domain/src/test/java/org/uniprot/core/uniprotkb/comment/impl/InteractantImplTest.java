package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * @author lgonzales
 * @since 2020-03-20
 */
class InteractantImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Interactant obj = new InteractantImpl();
        assertNotNull(obj);
    }

    @Test
    void completeObjConstructor() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        Interactant interactant = new InteractantImpl(accession, "gene1", "P_1234", "EBI-1223708");

        assertEquals(accession, interactant.getUniProtKBAccession());
        assertEquals("P_1234", interactant.getChainId());
        assertEquals("gene1", interactant.getGeneName());
        assertEquals("EBI-1223708", interactant.getIntActId());
    }

    @Test
    void completeObjHasMethods() {
        Interactant interactant =
                new InteractantImpl(
                        new UniProtKBAccessionBuilder("P12345").build(),
                        "gene1",
                        "P_1234",
                        "EBI-1223708");
        assertTrue(interactant.hasGeneName());
        assertTrue(interactant.hasUniProtKBAccession());
        assertTrue(interactant.hasIntActId());
        assertTrue(interactant.hasChainId());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Interactant interactant =
                new InteractantImpl(
                        new UniProtKBAccessionBuilder("P12345").build(),
                        "gene1",
                        "P_1234",
                        "EBI-1223708");
        Interactant obj = InteractantBuilder.from(interactant).build();
        assertEquals(interactant, obj);
        assertEquals(interactant.hashCode(), obj.hashCode());
    }
}
