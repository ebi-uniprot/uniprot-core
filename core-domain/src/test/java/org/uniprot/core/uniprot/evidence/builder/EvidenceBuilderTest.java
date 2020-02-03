package org.uniprot.core.uniprot.evidence.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.Evidence;

class EvidenceBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        Evidence obj = new EvidenceBuilder().build();
        EvidenceBuilder builder = EvidenceBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Evidence obj = new EvidenceBuilder().build();
        Evidence obj2 = new EvidenceBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
