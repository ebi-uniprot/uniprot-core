package org.uniprot.core.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.feature.AlternativeSequence;

class AlternativeSequenceImplTest {

    @Test
    void testFull() {

        AlternativeSequence as = new AlternativeSequenceImpl("AB", Arrays.asList("DC", "SDGASS"));

        assertEquals("AB", as.getOriginalSequence());
        assertEquals(Arrays.asList("DC", "SDGASS"), as.getAlternativeSequences());
    }

    @Test
    void testMissing() {

        AlternativeSequence as = new AlternativeSequenceImpl("AB", Collections.emptyList());

        assertEquals("AB", as.getOriginalSequence());
        assertTrue(as.getAlternativeSequences().isEmpty());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        AlternativeSequence obj = new AlternativeSequenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        AlternativeSequenceImpl impl =
                new AlternativeSequenceImpl("seq", Collections.singletonList("alter"));
        AlternativeSequence obj = AlternativeSequenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
