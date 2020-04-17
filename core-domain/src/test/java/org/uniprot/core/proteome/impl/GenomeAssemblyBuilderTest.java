package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.GenomeAssembly;
import org.uniprot.core.proteome.GenomeAssemblyLevel;
import org.uniprot.core.proteome.GenomeAssemblySource;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
class GenomeAssemblyBuilderTest {

    @Test
    void testAssemblyId() {
        String assemblyId = "id Value";
        GenomeAssembly genomeAssembly = new GenomeAssemblyBuilder().assemblyId(assemblyId).build();
        assertEquals(assemblyId, genomeAssembly.getAssemblyId());
    }

    @Test
    void testGenomeAssemblyUrl() {
        String genomeAssemblyUrl = "url Value";
        GenomeAssembly genomeAssembly =
                new GenomeAssemblyBuilder().genomeAssemblyUrl(genomeAssemblyUrl).build();
        assertEquals(genomeAssemblyUrl, genomeAssembly.getGenomeAssemblyUrl());
    }

    @Test
    void testGenomeAssemblyLevel() {
        GenomeAssemblyLevel level = GenomeAssemblyLevel.PARTIAL;
        GenomeAssembly genomeAssembly = new GenomeAssemblyBuilder().level(level).build();
        assertEquals(level, genomeAssembly.getLevel());
    }

    @Test
    void testGenomeAssemblySource() {
        GenomeAssemblySource source = GenomeAssemblySource.ENSEMBLPLANTS;
        GenomeAssembly genomeAssembly = new GenomeAssemblyBuilder().source(source).build();
        assertEquals(source, genomeAssembly.getSource());
    }

    @Test
    void testFrom() {
        GenomeAssembly report = ObjectsForTests.createGenomeAssembly();
        GenomeAssembly newReport = GenomeAssemblyBuilder.from(report).build();
        assertEquals(report, newReport);
    }
}
