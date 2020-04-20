package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.GenomeAssembly;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
class GenomeAssemblyImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        GenomeAssembly obj = new GenomeAssemblyImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        GenomeAssembly genomeAssembly = ObjectsForTests.createGenomeAssembly();
        GenomeAssembly genomeAssemblyFrom = GenomeAssemblyBuilder.from(genomeAssembly).build();
        assertTrue(
                genomeAssembly.equals(genomeAssemblyFrom)
                        && genomeAssemblyFrom.equals(genomeAssembly));
        assertEquals(genomeAssembly.hashCode(), genomeAssemblyFrom.hashCode());
    }
}
