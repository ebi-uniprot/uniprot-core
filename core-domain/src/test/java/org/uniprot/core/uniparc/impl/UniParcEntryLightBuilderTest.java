package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.sequenceFeatures;


class UniParcEntryLightBuilderTest {

    @Test
    void testUniParcId() {
        UniParcEntryLight entry =
                new UniParcEntryLightBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertNull(entry.getOldestCrossRefCreated());
        assertNull(entry.getMostRecentCrossRefUpdated());
    }

    @Test
    void testUniParcIdString() {
        UniParcEntryLight entry =
                new UniParcEntryLightBuilder()
                        .uniParcId("UPI0000083A08")
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertNull(entry.getOldestCrossRefCreated());
        assertNull(entry.getMostRecentCrossRefUpdated());
    }

    @Test
    void testUniParcCrossReferencesSet() {
        List<String> crossReferences = List.of("UPI0000000001-REFSEQ-12345-3", "UPI0000000002-EMBL-67890-1");
        UniParcEntryLight entry = new UniParcEntryLightBuilder().uniParcCrossReferencesSet(crossReferences).build();
        assertEquals(crossReferences, entry.getUniParcCrossReferences());
    }

    @Test
    void testUniParcCrossReferencesAdd() {
        String crossReference = "UPI0000000001-REFSEQ-12345-3";
        UniParcEntryLight entry = new UniParcEntryLightBuilder().uniParcCrossReferencesAdd(crossReference).build();
        assertTrue(entry.getUniParcCrossReferences().contains(crossReference));
    }

    @Test
    void testUniParcCrossReferencesAddNull() {
        UniParcEntryLight entry = new UniParcEntryLightBuilder().uniParcCrossReferencesSet(null).build();
        assertTrue(entry.getUniParcCrossReferences().isEmpty()); // Null should not be added
    }

    @Test
    void testUniParcCrossReferencesSetThenAdd() {
        List<String> crossReferences = List.of("UPI0000000001-REFSEQ-12345-3");
        String additionalCrossReference = "UPI0000000002-EMBL-67890-1";
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .uniParcCrossReferencesSet(crossReferences)
                .uniParcCrossReferencesAdd(additionalCrossReference)
                .build();
        assertEquals(2, entry.getUniParcCrossReferences().size());
        assertTrue(entry.getUniParcCrossReferences().containsAll(List.of("UPI0000000001-REFSEQ-12345-3", "UPI0000000002-EMBL-67890-1")));
    }

    @Test
    void testCommonTaxonsSet() {
        List<Pair<String, String>> commonTaxons = List.of(
                new PairImpl<>("Viruses", "Homo sapiens"),
                new PairImpl<>("unclassified", "Mus musculus")
        );
        UniParcEntryLight entry = new UniParcEntryLightBuilder().commonTaxonsSet(commonTaxons).build();
        assertEquals(commonTaxons, entry.getCommonTaxons());
    }

    @Test
    void testCommonTaxonsAdd() {
        Pair<String, String> commonTaxon = new PairImpl<>("Viruses", "Homo sapiens");
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
        List<Pair<String, String>> commonTaxons = List.of(
                new PairImpl<>("Viruses", "Homo sapiens")
        );
        Pair<String, String> additionalCommonTaxon = new PairImpl<>("Other type", "Mus musculus");
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .commonTaxonsSet(commonTaxons)
                .commonTaxonsAdd(additionalCommonTaxon)
                .build();
        assertEquals(2, entry.getCommonTaxons().size());
        assertTrue(entry.getCommonTaxons().containsAll(List.of(
                new PairImpl<>("Viruses", "Homo sapiens"),
                new PairImpl<>("Other type", "Mus musculus")
        )));
    }

    @Test
    void testUniProtKBAccessionsSet() {
        Set<String> accessions = Set.of("P12345", "Q67890");
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
        Set<String> accessions = Set.of("P12345");
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
        Pair<Integer, String> organism1 = new PairImpl<>(9606, "Homo sapiens");
        Pair<Integer, String> organism2 = new PairImpl<>(10090, "Mus musculus");
        List<Pair<Integer, String>> organisms = List.of(organism1, organism2);
        UniParcEntryLight entry = new UniParcEntryLightBuilder().organismsSet(organisms).build();
        assertEquals(organisms, entry.getOrganisms());
    }

    @Test
    void testOrganismsAdd() {
        Pair<Integer, String> organism = new PairImpl<>(9606, "Homo sapiens");
        UniParcEntryLight entry = new UniParcEntryLightBuilder().organismsAdd(organism).build();
        assertTrue(entry.getOrganisms().contains(organism));
    }

    @Test
    void testProteinNamesSet() {
        List<String> proteinNames = List.of("Protein1", "Protein2");
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
        List<String> geneNames = List.of("Gene1", "Gene2");
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
        List<String> proteomeIds = List.of("UP000005640", "UP000002494");
        UniParcEntryLight entry = new UniParcEntryLightBuilder().proteomeIdsSet(proteomeIds).build();
        assertEquals(proteomeIds, entry.getProteomeIds());
    }

    @Test
    void testProteomeIdsAdd() {
        String proteomeId = "UP000005640";
        UniParcEntryLight entry = new UniParcEntryLightBuilder().proteomeIdsAdd(proteomeId).build();
        assertTrue(entry.getProteomeIds().contains(proteomeId));
    }

    @Test
    void testOrganismsSetThenAdd() {
        Pair<Integer, String> organism1 = new PairImpl<>(9606, "Homo sapiens");
        Pair<Integer, String> organism2 = new PairImpl<>(10090, "Mus musculus");
        List<Pair<Integer, String>> organisms = List.of(organism1);
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .organismsSet(organisms)
                .organismsAdd(organism2)
                .build();
        assertEquals(2, entry.getOrganisms().size());
        assertTrue(entry.getOrganisms().containsAll(List.of(organism1, organism2)));
    }

    @Test
    void testProteinNamesSetThenAdd() {
        List<String> proteinNames = List.of("Protein1");
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
        List<String> geneNames = List.of("Gene1");
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
        List<String> proteomeIds = List.of("UP000005640");
        String proteomeId = "UP000002494";
        UniParcEntryLight entry = new UniParcEntryLightBuilder()
                .proteomeIdsSet(proteomeIds)
                .proteomeIdsAdd(proteomeId)
                .build();
        assertEquals(2, entry.getProteomeIds().size());
        assertTrue(entry.getProteomeIds().containsAll(List.of("UP000005640", "UP000002494")));
    }


    @Test
    void testFrom() {
        String uniParcId = "UPI0000000001";
        List<String> uniParcCrossReferences = List.of("UPI0000000001-REFSEQ-12345-3", "UPI0000000002-EMBL-67890-1");
        List<Pair<String, String>> commonTaxons = List.of(new PairImpl<>("Viruses", "Homo sapiens"), new PairImpl<>("unclassified", "Mus musculus"));
        String uniProtExclusionReason = "Reason";
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<SequenceFeature> sequenceFeatures = sequenceFeatures();
        LocalDate oldestCrossRefCreated = LocalDate.of(2022, 1, 1);
        LocalDate mostRecentCrossRefUpdated = LocalDate.of(2023, 1, 1);
        List<Pair<Integer, String>> organisms = List.of(new PairImpl<>(9606, "Homo sapiens"), new PairImpl<>(10090, "Mus musculus"));
        List<String> proteinNames = List.of("Protein1", "Protein2");
        List<String> geneNames = List.of("Gene1", "Gene2");
        List<String> proteomeIds = List.of("UP000005640", "UP000002494");

        UniParcEntryLight originalEntry = new UniParcEntryLightBuilder()
                .uniParcId(uniParcId)
                .uniParcCrossReferencesSet(uniParcCrossReferences)
                .commonTaxonsSet(commonTaxons)
                .sequence(sequence)
                .sequenceFeaturesSet(sequenceFeatures)
                .oldestCrossRefCreated(oldestCrossRefCreated)
                .mostRecentCrossRefUpdated(mostRecentCrossRefUpdated)
                .organismsSet(organisms)
                .proteinNamesSet(proteinNames)
                .geneNamesSet(geneNames)
                .proteomeIdsSet(proteomeIds)
                .build();

        UniParcEntryLight newEntry = UniParcEntryLightBuilder.from(originalEntry).build();

        assertEquals(originalEntry.getUniParcId(), newEntry.getUniParcId());
        assertEquals(originalEntry.getUniParcCrossReferences(), newEntry.getUniParcCrossReferences());
        assertEquals(originalEntry.getCommonTaxons(), newEntry.getCommonTaxons());
        assertEquals(originalEntry.getSequence(), newEntry.getSequence());
        assertEquals(originalEntry.getSequenceFeatures(), newEntry.getSequenceFeatures());
        assertEquals(originalEntry.getOldestCrossRefCreated(), newEntry.getOldestCrossRefCreated());
        assertEquals(originalEntry.getMostRecentCrossRefUpdated(), newEntry.getMostRecentCrossRefUpdated());
        assertEquals(originalEntry.getOrganisms(), newEntry.getOrganisms());
        assertEquals(originalEntry.getProteinNames(), newEntry.getProteinNames());
        assertEquals(originalEntry.getGeneNames(), newEntry.getGeneNames());
        assertEquals(originalEntry.getProteomeIds(), newEntry.getProteomeIds());
        assertEquals(originalEntry, newEntry);
    }

}
