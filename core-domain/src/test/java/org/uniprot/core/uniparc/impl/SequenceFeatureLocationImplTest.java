package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.SequenceFeatureLocation;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SequenceFeatureLocationImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SequenceFeatureLocation obj = new SequenceFeatureLocationImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SequenceFeatureLocationImpl impl =
                new SequenceFeatureLocationImpl(10,20, "component");
        SequenceFeatureLocation obj = SequenceFeatureLocationBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}