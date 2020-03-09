package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;

class SequenceFeatureImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SequenceFeature obj = new SequenceFeatureImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SequenceFeature impl =
                new SequenceFeatureImpl(
                        new InterProGroupImpl("id", "nane"),
                        SignatureDbType.PFAM,
                        "dbId",
                        Collections.emptyList());
        SequenceFeature obj = SequenceFeatureBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
