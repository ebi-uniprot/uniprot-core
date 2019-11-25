package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.builder.CanonicalProteinBuilder;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

class CanonicalProteinImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CanonicalProtein obj = new CanonicalProteinImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CanonicalProtein impl =
                new CanonicalProteinImpl(
                        new ProteinImpl(
                                new UniProtAccessionImpl("acc"),
                                UniProtEntryType.SWISSPROT,
                                78L,
                                "gene",
                                GeneNameType.OLN),
                        Collections.emptyList());
        CanonicalProtein obj = new CanonicalProteinBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
