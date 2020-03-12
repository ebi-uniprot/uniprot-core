package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.uniprotkb.UniProtkbEntryType;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;

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
                                new UniProtkbAccessionBuilder("acc").build(),
                                UniProtkbEntryType.SWISSPROT,
                                78L,
                                "gene",
                                GeneNameType.OLN),
                        Collections.emptyList());
        CanonicalProtein obj = CanonicalProteinBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
