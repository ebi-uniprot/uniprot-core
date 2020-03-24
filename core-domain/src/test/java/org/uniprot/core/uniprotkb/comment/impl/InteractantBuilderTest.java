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
class InteractantBuilderTest {

    @Test
    void testNewInstance() {
        InteractantBuilder builder1 = new InteractantBuilder();
        InteractantBuilder builder2 = new InteractantBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetUniProtKBAccessionString() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant interactant = builder.uniProtKBAccession("P12345").build();

        assertEquals("P12345", interactant.getUniProtKBAccession().getValue());
        assertNull(interactant.getIntActId());
        assertNull(interactant.getGeneName());
        assertNull(interactant.getChainId());
    }

    @Test
    void testSetUniProtKBAccession() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        InteractantBuilder builder = new InteractantBuilder();
        Interactant interactant = builder.uniProtKBAccession(accession).build();

        assertEquals(accession, interactant.getUniProtKBAccession());
        assertNull(interactant.getIntActId());
        assertNull(interactant.getGeneName());
        assertNull(interactant.getChainId());
    }

    @Test
    void testSetIntActId() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant interactant = builder.intActId("intAct id").build();

        assertEquals("intAct id", interactant.getIntActId());
        assertNull(interactant.getUniProtKBAccession());
        assertNull(interactant.getGeneName());
        assertNull(interactant.getChainId());
    }

    @Test
    void testSetGeneName() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant interactant = builder.geneName("gene name").build();

        assertNull(interactant.getIntActId());
        assertNull(interactant.getUniProtKBAccession());
        assertEquals("gene name", interactant.getGeneName());
        assertNull(interactant.getChainId());
    }

    @Test
    void testSetChainId() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant interactant = builder.chainId("chain id").build();

        assertNull(interactant.getIntActId());
        assertNull(interactant.getUniProtKBAccession());
        assertNull(interactant.getGeneName());
        assertEquals("chain id", interactant.getChainId());
    }

    @Test
    void canCreateBuilderFromInstance() {
        Interactant interactant =
                new InteractantBuilder()
                        .uniProtKBAccession("P12345")
                        .geneName("gene name")
                        .chainId("chain id")
                        .intActId("intAct id")
                        .build();
        InteractantBuilder builder = InteractantBuilder.from(interactant);
        assertNotNull(builder);
        assertEquals(interactant, builder.build());
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Interactant obj = new InteractantBuilder().build();
        Interactant obj2 = new InteractantBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
