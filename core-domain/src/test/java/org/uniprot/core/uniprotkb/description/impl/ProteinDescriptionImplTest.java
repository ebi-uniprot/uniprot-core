package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.ProteinDescription;

class ProteinDescriptionImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteinDescription obj = new ProteinDescriptionImpl();
        assertNotNull(obj);
        assertTrue(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.getSubmissionNames().isEmpty());
        assertTrue(obj.getIncludes().isEmpty());
        assertTrue(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.getAlternativeNames().isEmpty());
        assertTrue(obj.getContains().isEmpty());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteinDescriptionImpl impl =
                new ProteinDescriptionImpl(
                        new ProteinRecNameImpl(),
                        Collections.emptyList(),
                        new NameImpl(),
                        new NameImpl(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        new FlagImpl(),
                        Collections.emptyList(),
                        Collections.emptyList());
        ProteinDescription obj = ProteinDescriptionBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
