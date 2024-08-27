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


class UniParcEntryLightBuilderTest {

    @Test
    void testUniParcIdString() {
        UniParcEntryLight entry =
                new UniParcEntryLightBuilder()
                        .uniParcId("UPI0000083A08")
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId());
        assertNull(entry.getOldestCrossRefCreated());
        assertNull(entry.getMostRecentCrossRefUpdated());
    }

    @Test
    void testCrossReferenceCount() {
        int crossReferenceCount = 20;
        UniParcEntryLight entry = new UniParcEntryLightBuilder().crossReferenceCount(crossReferenceCount).build();
        assertEquals(crossReferenceCount, entry.getCrossReferenceCount());
    }

    @Test
    void testCommonTaxonsSet() {
        List<CommonOrganism> commonTaxons = List.of(
                new CommonOrganismBuilder().topLevel("Viruses").commonTaxon("HIV").build(),
                new CommonOrganismBuilder().topLevel("unclassified").commonTaxon("Mus musculus").build()
        );
        UniParcEntryLight entry = new UniParcEntryLightBuilder().commonTaxonsSet(commonTaxons).build();
        assertEquals(commonTaxons, entry.getCommonTaxons());
    }

    @Test
    void testCommonTaxonsAdd() {
        CommonOrganism commonTaxon = new CommonOrganismBuilder().topLevel("Viruses").commonTaxon("Homo sapiens").build();
        UniParcEntryLight entry = new UniParcEntryLightBuilder().commonTaxonsAdd(commonTaxon).build();
        assertTrue(entry.getCommonTaxons().contains(commonTaxon));
    }

    @Test
    void testCommonTaxonsAddNull() {
        UniParcEntryLight entry = new UniParcEntryLightBuilder().commonTaxonsAdd(null).build();
        assertTrue(entry.getCommonTaxons().isEmpty());
    }

    @Test
    void testCommonTaxonsSetThenAdd() {
        List<CommonOrganism> commonTaxons = List.of(
                new CommonOrganismBuilder().topLevel("Viruses").commonTaxon("HIV").build());
        CommonOrganism additionalCommonTaxon = new CommonOrganismBuilder()
                .topLevel("Other type")
                .commonTaxon("Mus musculus")
                .build();
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .commonTaxonsSet(commonTaxons)
                .commonTaxonsAdd(additionalCommonTaxon)
                .build();
        assertEquals(2, entry.getCommonTaxons().size());
        assertTrue(entry.getCommonTaxons().containsAll(List.of(
                new CommonOrganismBuilder().topLevel("Viruses").commonTaxon("HIV").build(),
                new CommonOrganismBuilder().topLevel("Other type").commonTaxon("Mus musculus").build()
        )));
    }

    @Test
    void testUniProtKBAccessionsSet() {
        LinkedHashSet<String> accessions = new LinkedHashSet<>(List.of("P12345", "Q67890"));
        UniParcEntryLight entry = new UniParcEntryLightBuilder().uniProtKBAccessionsSet(accessions).build();
        assertEquals(accessions, entry.getUniProtKBAccessions());
    }

    @Test
    void testUniProtKBAccessionsAdd() {
        String accession = "P12345";
        UniParcEntryLight entry = new UniParcEntryLightBuilder().uniProtKBAccessionsAdd(accession).build();
        assertTrue(entry.getUniProtKBAccessions().contains(accession));
    }

    @Test
    void testUniProtKBAccessionsAddNull() {
        UniParcEntryLight entry = new UniParcEntryLightBuilder().uniProtKBAccessionsAdd(null).build();
        assertTrue(entry.getUniProtKBAccessions().isEmpty());
    }

    @Test
    void testUniProtKBAccessionsSetThenAdd() {
        LinkedHashSet<String> accessions = new LinkedHashSet<>(List.of("P12345"));
        String additionalAccession = "Q67890";
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .uniProtKBAccessionsSet(accessions)
                .uniProtKBAccessionsAdd(additionalAccession)
                .build();
        assertEquals(2, entry.getUniProtKBAccessions().size());
        assertTrue(entry.getUniProtKBAccessions().containsAll(List.of("P12345", "Q67890")));
    }

    @Test
    void testSequence() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        UniParcEntryLight entry = new UniParcEntryLightBuilder().sequence(sequence).build();
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void testSequenceFeaturesSet() {
        List<SequenceFeature> sequenceFeatures = sequenceFeatures();
        UniParcEntryLight entry = new UniParcEntryLightBuilder().sequenceFeaturesSet(sequenceFeatures).build();
        assertEquals(sequenceFeatures, entry.getSequenceFeatures());
    }

    @Test
    void testSequenceFeaturesAdd() {
        SequenceFeature sequenceFeature = sequenceFeatures().get(0);
        UniParcEntryLight entry = new UniParcEntryLightBuilder().sequenceFeaturesAdd(sequenceFeature).build();
        assertTrue(entry.getSequenceFeatures().contains(sequenceFeature));
    }

    @Test
    void testSequenceFeaturesAddNull() {
        UniParcEntryLight entry = new UniParcEntryLightBuilder().sequenceFeaturesAdd(null).build();
        assertTrue(entry.getSequenceFeatures().isEmpty());
    }

    @Test
    void testSequenceFeaturesSetThenAdd() {
        List<SequenceFeature> sequenceFeatures = sequenceFeatures();
        SequenceFeature feature2 = sequenceFeatures().get(0);
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .sequenceFeaturesSet(sequenceFeatures)
                .sequenceFeaturesAdd(feature2)
                .build();
        assertEquals(sequenceFeatures.size() + 1, entry.getSequenceFeatures().size());
    }

    @Test
    void testOldestCrossRefCreated() {
        LocalDate date = LocalDate.of(2022, 1, 1);
        UniParcEntryLight entry = new UniParcEntryLightBuilder().oldestCrossRefCreated(date).build();
        assertEquals(date, entry.getOldestCrossRefCreated());
    }

    @Test
    void testMostRecentCrossRefUpdated() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        UniParcEntryLight entry = new UniParcEntryLightBuilder().mostRecentCrossRefUpdated(date).build();
        assertEquals(date, entry.getMostRecentCrossRefUpdated());
    }

    @Test
    void testOrganismsSet() {
        Organism organism1 = new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        Organism organism2 = new OrganismBuilder().taxonId(10090).scientificName("Mus musculus").build();
        LinkedHashSet<Organism> organisms = new LinkedHashSet<>(List.of(organism1, organism2));
        UniParcEntryLight entry = new UniParcEntryLightBuilder().organismsSet(organisms).build();
        assertEquals(organisms, entry.getOrganisms());
    }

    @Test
    void testOrganismsAdd() {
        Organism organism = new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        UniParcEntryLight entry = new UniParcEntryLightBuilder().organismsAdd(organism).build();
        assertTrue(entry.getOrganisms().contains(organism));
    }

    @Test
    void testProteinNamesSet() {
        LinkedHashSet<String> proteinNames = new LinkedHashSet<>(List.of("Protein1", "Protein2"));
        UniParcEntryLight entry = new UniParcEntryLightBuilder().proteinNamesSet(proteinNames).build();
        assertEquals(proteinNames, entry.getProteinNames());
    }

    @Test
    void testProteinNamesAdd() {
        String proteinName = "Protein1";
        UniParcEntryLight entry = new UniParcEntryLightBuilder().proteinNamesAdd(proteinName).build();
        assertTrue(entry.getProteinNames().contains(proteinName));
    }

    @Test
    void testGeneNamesSet() {
        LinkedHashSet<String> geneNames = new LinkedHashSet<>(List.of("Gene1", "Gene2"));
        UniParcEntryLight entry = new UniParcEntryLightBuilder().geneNamesSet(geneNames).build();
        assertEquals(geneNames, entry.getGeneNames());
    }

    @Test
    void testGeneNamesAdd() {
        String geneName = "Gene1";
        UniParcEntryLight entry = new UniParcEntryLightBuilder().geneNamesAdd(geneName).build();
        assertTrue(entry.getGeneNames().contains(geneName));
    }

    @Test
    void testProteomeIdsSet() {
        LinkedHashSet<Proteome> proteomes = new LinkedHashSet<>(List.of(new ProteomeBuilder().id("UP000005640").component("C1").build(), new ProteomeBuilder().id("UP000002494").component("C2").build()));
        UniParcEntryLight entry = new UniParcEntryLightBuilder().proteomesSet(proteomes).build();
        assertEquals(proteomes, entry.getProteomes());
    }

    @Test
    void testProteomeIdsAdd() {
        Proteome proteomeId = new ProteomeBuilder().id("UP000005640").component("C1").build();
        UniParcEntryLight entry = new UniParcEntryLightBuilder().proteomesAdd(proteomeId).build();
        assertTrue(entry.getProteomes().contains(proteomeId));
    }

    @Test
    void testOrganismsSetThenAdd() {
        Organism organism1 = new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        Organism organism2 = new OrganismBuilder().taxonId(10090).scientificName("Mus musculus").build();
        LinkedHashSet<Organism> organisms = new LinkedHashSet<>(List.of(organism1));
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .organismsSet(organisms)
                .organismsAdd(organism2)
                .build();
        assertEquals(2, entry.getOrganisms().size());
        assertTrue(entry.getOrganisms().containsAll(List.of(organism1, organism2)));
    }

    @Test
    void testProteinNamesSetThenAdd() {
        LinkedHashSet<String> proteinNames = new LinkedHashSet<>(List.of("Protein1"));
        String proteinName = "Protein2";
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .proteinNamesSet(proteinNames)
                .proteinNamesAdd(proteinName)
                .build();
        assertEquals(2, entry.getProteinNames().size());
        assertTrue(entry.getProteinNames().containsAll(List.of("Protein1", "Protein2")));
    }

    @Test
    void testGeneNamesSetThenAdd() {
        LinkedHashSet<String> geneNames = new LinkedHashSet<>(List.of("Gene1"));
        String geneName = "Gene2";
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .geneNamesSet(geneNames)
                .geneNamesAdd(geneName)
                .build();
        assertEquals(2, entry.getGeneNames().size());
        assertTrue(entry.getGeneNames().containsAll(List.of("Gene1", "Gene2")));
    }

    @Test
    void testProteomeIdsSetThenAdd() {
        Proteome proteome1 = new ProteomeBuilder().id("UP000005640").component("chromosome").build();
        LinkedHashSet<Proteome> proteomes = new LinkedHashSet<>(List.of(proteome1));
        Proteome proteome2 = new ProteomeBuilder().id("UP000002494").component("chromosome").build();

        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .proteomesSet(proteomes)
                .proteomesAdd(proteome2)
                .build();
        assertEquals(2, entry.getProteomes().size());
        assertTrue(entry.getProteomes().containsAll(List.of(proteome1, proteome2)));
    }


    @Test
    void testFrom() {
        String uniParcId = "UPI0000000001";
        int crossReferenceCount = 18;
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

        LinkedHashSet<String> proteinNames = new LinkedHashSet(List.of("Protein1", "Protein2"));
        LinkedHashSet<String> geneNames = new LinkedHashSet(List.of("Gene1", "Gene2"));
        LinkedHashSet<Proteome> proteomes = new LinkedHashSet<>(List.of(new ProteomeBuilder().id("UP000005640").component("C1").build(), new ProteomeBuilder().id("UP000002494").component("C2").build()));

        UniParcEntryLight originalEntry = new UniParcEntryLightBuilder()
                .uniParcId(uniParcId)
                .crossReferenceCount(crossReferenceCount)
                .commonTaxonsSet(commonTaxons)
                .sequence(sequence)
                .sequenceFeaturesSet(sequenceFeatures)
                .oldestCrossRefCreated(oldestCrossRefCreated)
                .mostRecentCrossRefUpdated(mostRecentCrossRefUpdated)
                .organismsSet(organisms)
                .proteinNamesSet(proteinNames)
                .geneNamesSet(geneNames)
                .proteomesSet(proteomes)
                .build();

        UniParcEntryLight newEntry = UniParcEntryLightBuilder.from(originalEntry).build();

        assertEquals(originalEntry.getUniParcId(), newEntry.getUniParcId());
        assertEquals(originalEntry.getCrossReferenceCount(), newEntry.getCrossReferenceCount());
        assertEquals(originalEntry.getCommonTaxons(), newEntry.getCommonTaxons());
        assertEquals(originalEntry.getSequence(), newEntry.getSequence());
        assertEquals(originalEntry.getSequenceFeatures(), newEntry.getSequenceFeatures());
        assertEquals(originalEntry.getOldestCrossRefCreated(), newEntry.getOldestCrossRefCreated());
        assertEquals(originalEntry.getMostRecentCrossRefUpdated(), newEntry.getMostRecentCrossRefUpdated());
        assertEquals(originalEntry.getOrganisms(), newEntry.getOrganisms());
        assertEquals(originalEntry.getProteinNames(), newEntry.getProteinNames());
        assertEquals(originalEntry.getGeneNames(), newEntry.getGeneNames());
        assertEquals(originalEntry.getProteomes(), newEntry.getProteomes());
        assertEquals(originalEntry, newEntry);
    }

    @Test
    void testFromWithEmptyAccessions(){
        UniParcEntryLight entry =
                new UniParcEntryLightBuilder()
                        .uniParcId("UPI0000083A08")
                        .uniProtKBAccessionsSet(null)
                        .build();
        Assertions.assertDoesNotThrow(() -> UniParcEntryLightBuilder.from(entry));
        UniParcEntryLightBuilder builder = UniParcEntryLightBuilder.from(entry);
        builder.uniProtKBAccessionsAdd(null);
        Assertions.assertDoesNotThrow(() -> UniParcEntryLightBuilder.from(builder.build()));
    }

}
