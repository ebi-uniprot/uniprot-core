package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * @author jluo
 * @date: 18 Mar 2020
 */
class InteractorBuilderTest {

    @Test
    void testNewInstance() {
        InteractantBuilder builder1 = new InteractantBuilder();
        InteractantBuilder builder2 = new InteractantBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testFrom() {
        Interactant obj = new InteractantBuilder().build();
        InteractantBuilder builder = InteractantBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void testGeneName() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant obj = builder.geneName("gene1").build();
        assertEquals("gene1", obj.getGeneName());
        assertNull(obj.getUniProtKBAccession());
        assertNull(obj.getChainId());
        assertNull(obj.getIntActId());
    }

    @Test
    void testUniProtAccessionUniProtKBAccession() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant obj =
                builder.uniProtKBAccession(new UniProtKBAccessionBuilder("P12345").build()).build();
        assertNull(obj.getGeneName());
        assertNotNull(obj.getUniProtKBAccession());
        assertEquals("P12345", obj.getUniProtKBAccession().getValue());
        assertNull(obj.getChainId());
        assertNull(obj.getIntActId());
    }

    @Test
    void testUniProtAccessionString() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant obj = builder.uniProtKBAccession("P12345").build();
        assertNull(obj.getGeneName());
        assertNotNull(obj.getUniProtKBAccession());
        assertEquals("P12345", obj.getUniProtKBAccession().getValue());
        assertNull(obj.getChainId());
        assertNull(obj.getIntActId());
    }

    @Test
    void testChainId() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant obj = builder.chainId("P_1234").build();
        assertEquals("P_1234", obj.getChainId());
        assertNull(obj.getUniProtKBAccession());
        assertNull(obj.getGeneName());
        assertNull(obj.getIntActId());
    }

    @Test
    void testIntActId() {
        InteractantBuilder builder = new InteractantBuilder();
        Interactant obj = builder.intActId("EBI-1036653").build();
        assertEquals("EBI-1036653", obj.getIntActId());
        assertNull(obj.getUniProtKBAccession());
        assertNull(obj.getChainId());
        assertNull(obj.getGeneName());
    }
}
