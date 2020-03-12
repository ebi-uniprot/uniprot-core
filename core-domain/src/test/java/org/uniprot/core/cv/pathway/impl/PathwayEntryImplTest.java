package org.uniprot.core.cv.pathway.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.pathway.PathwayEntry;

class PathwayEntryImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        PathwayEntry obj = new PathwayEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        PathwayEntry impl =
                new PathwayEntryImpl(
                        "accession",
                        "id",
                        "pathway",
                        "definition",
                        Arrays.asList("1", "2"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        PathwayEntry obj = PathwayEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
