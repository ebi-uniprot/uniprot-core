package org.uniprot.core.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.UniProtRelease;
import org.uniprot.core.builder.UniProtReleaseBuilder;

class UniProtReleaseImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtRelease obj = new UniProtReleaseImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtRelease impl =
                new UniProtReleaseImpl("1.0", LocalDate.now(), "2.00.87", LocalDate.now());
        UniProtRelease obj = UniProtReleaseBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
