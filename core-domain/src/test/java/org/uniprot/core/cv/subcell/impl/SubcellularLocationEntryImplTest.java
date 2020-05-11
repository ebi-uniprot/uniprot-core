package org.uniprot.core.cv.subcell.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.impl.StatisticsBuilder;

class SubcellularLocationEntryImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SubcellularLocationEntry obj = new SubcellularLocationEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SubcellularLocationEntry impl =
                new SubcellularLocationEntryImpl(
                        "id",
                        "accession",
                        "definition",
                        "content",
                        new KeywordIdBuilder().name("kid").build(),
                        "note",
                        new StatisticsBuilder().build(),
                        SubcellLocationCategory.ORIENTATION,
                        Collections.emptyList(),
                        Arrays.asList("1", "2", "3"),
                        Arrays.asList("ref1", "ref2", "ref3"),
                        Arrays.asList("link1", "l2", "l3"),
                        Collections.emptyList(),
                        Collections.emptyList());
        SubcellularLocationEntry obj = SubcellularLocationEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
