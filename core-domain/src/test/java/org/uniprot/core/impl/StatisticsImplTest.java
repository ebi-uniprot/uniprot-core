package org.uniprot.core.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;

class StatisticsImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Statistics obj = new StatisticsImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Statistics impl = new StatisticsImpl(986L, 3654L);
        Statistics obj = StatisticsBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
