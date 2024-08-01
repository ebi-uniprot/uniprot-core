package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.CommonOrganism;
import org.uniprot.core.uniparc.Proteome;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.sequenceFeatures;

class UniParcEntryLightImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniParcEntryLight lightObject = new UniParcEntryLightImpl();
        assertNotNull(lightObject);
        UniParcEntryLightBuilder.from(lightObject);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        String uniParcId = "UPI0000000001";
        List<String> uniParcCrossReferences = List.of("UPI0000000001-REFSEQ-12345-3", "UPI0000000002-EMBL-67890-1");
        List<CommonOrganism> commonTaxons = List.of(
                new CommonOrganismBuilder().topLevel("Viruses").commonTaxon("HIV").build(),
                new CommonOrganismBuilder().topLevel("unclassified").commonTaxon("Mus musculus").build());
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<SequenceFeature> sequenceFeatures = sequenceFeatures();
        LocalDate oldestCrossRefCreated = LocalDate.of(2022, 1, 1);
        LocalDate mostRecentCrossRefUpdated = LocalDate.of(2023, 1, 1);

        Organism organism1 = new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        Organism organism2 = new OrganismBuilder().taxonId(10090).scientificName("Mus musculus").build();
        LinkedHashSet<Organism> organisms = new LinkedHashSet<>(List.of(organism1, organism2));

        LinkedHashSet<String> proteinNames = new LinkedHashSet<>(List.of("Protein1", "Protein2"));
        LinkedHashSet<String> geneNames = new LinkedHashSet<>(List.of("Gene1", "Gene2"));
        LinkedHashSet<Proteome> proteomes = new LinkedHashSet<>(List.of(new ProteomeBuilder().id("UP000005640").component("C1").build(), new ProteomeBuilder().id("UP000002494").component("C2").build()));
        LinkedHashSet<String> uniProtKBAccessions = new LinkedHashSet<>(List.of("P21802", "P12345"));

        UniParcEntryLight impl = new UniParcEntryLightImpl(uniParcId, uniParcCrossReferences, commonTaxons, uniProtKBAccessions,
                sequence, sequenceFeatures, oldestCrossRefCreated, mostRecentCrossRefUpdated, organisms, proteinNames, geneNames, proteomes);

        UniParcEntryLight obj = UniParcEntryLightBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void defaultConstructorAndBuilderFrom() {
        UniParcEntryLight lightObject = new UniParcEntryLightImpl();
        assertNotNull(lightObject);
        Assertions.assertDoesNotThrow(() -> UniParcEntryLightBuilder.from(lightObject));
    }
}
