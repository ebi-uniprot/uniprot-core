package org.uniprot.core.cv.xdb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.xdb.CrossRefEntry;
import org.uniprot.core.impl.StatisticsBuilder;

class CrossRefEntryImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CrossRefEntry obj = new CrossRefEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Statistics statistics = new StatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(100)
                .build();
        CrossRefEntry impl =
                new CrossRefEntryImpl(
                        "name", "acc", "abb", "pub", "doild", "link", "server", "dburk", "cat", statistics);
        CrossRefEntry obj = CrossRefEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
