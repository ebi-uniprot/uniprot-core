package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.builder.UniParcDBCrossReferenceBuilder;

class UniParcDBCrossReferenceImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniParcDBCrossReference obj = new UniParcDBCrossReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniParcDBCrossReference impl =
                new UniParcDBCrossReferenceImpl(
                        UniParcDatabase.SWISSPROT,
                        "id",
                        Collections.emptyList(),
                        2,
                        -3,
                        true,
                        LocalDate.now(),
                        LocalDate.now());
        UniParcDBCrossReference obj = UniParcDBCrossReferenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
