package org.uniprot.core.parser.tsv.uniparc;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniParcEntryLightValueMapperTest {

    @Test
    void testGetDataOrganism() {
        UniParcEntryLight entry = create();
        List<String> fields = Arrays.asList("upi", "organism", "organism_id", "proteome", "common_taxons");

        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entry, fields);

        assertEquals(fields.size(), result.size());
        verify("UPI0000083A08", "upi", result);
        verify("Homo sapiens; MOUSE", "organism", result);
        verify("9606; 10090", "organism_id", result);
        verify("UP000005640:C1; UP000002494:C2", "proteome", result);
        verify("Bacteroides; Enterococcus", "common_taxons", result);
    }

    @Test
    void testGetDataOrganism_withEmptyCommonTaxons() {
        UniParcEntryLight entry = create();
        UniParcEntryLight entryWithEmptyCommonTaxons = UniParcEntryLightBuilder.from(entry).commonTaxonsSet(List.of()).build();
        List<String> fields = Arrays.asList("upi", "organism", "organism_id", "proteome", "common_taxons");

        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entryWithEmptyCommonTaxons, fields);

        assertEquals(fields.size(), result.size());
        verify("UPI0000083A08", "upi", result);
        verify("Homo sapiens; MOUSE", "organism", result);
        verify("9606; 10090", "organism_id", result);
        verify("UP000005640:C1; UP000002494:C2", "proteome", result);
        verify("", "common_taxons", result);
    }

    @Test
    void testGetDataProteinGene() {
        UniParcEntryLight entry = create();
        List<String> fields = Arrays.asList("upi", "protein", "gene", "accession");
        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entry, fields);
        assertEquals(4, result.size());
        verify("UPI0000083A08", "upi", result);
        verify("protein1; protein2", "protein", result);
        verify("gene1; gene2", "gene", result);
        verify("P52346; P12345", "accession", result);
    }

    @Test
    void testGetDate() {
        UniParcEntryLight entry = create();
        List<String> fields = Arrays.asList("upi", "first_seen", "last_seen");
        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entry, fields);
        assertEquals(3, result.size());
        verify("UPI0000083A08", "upi", result);
        verify("2017-02-12", "first_seen", result);
        verify("2020-10-25", "last_seen", result);
    }

    @Test
    void testGetSequence() {
        UniParcEntryLight entry = create();
        List<String> fields = Arrays.asList("upi", "checksum", "length", "sequence");
        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entry, fields);
        assertEquals(fields.size(), result.size());
        verify("UPI0000083A08", "upi", result);
        verify("AA7812161AF4EB5E", "checksum", result);
        verify("30", "length", result);
        verify("MVSWGRFICLVVVTMATLSLARPSFSLVED", "sequence", result);
    }

    @Test
    void testGetSequenceFeature() {
        UniParcEntryLight entry = create();
        List<String> fields = Arrays.asList("upi", "HAMAP", "InterPro", "Pfam", "PROSITE");
        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entry, fields);
        assertEquals(14, result.size());
        verify("UPI0000083A08", "upi", result);
        verify("", "HAMAP", result);
        verify("id1", "InterPro", result);
        verify("sigId2", "Pfam", result);
        verify("sigId2", "PROSITE", result);
    }

    private void verify(String expected, String field, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }

    private UniParcEntryLight create() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<SequenceFeature> seqFeatures = getSeqFeatures();

        Organism organism1 = new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        Organism organism2 = new OrganismBuilder().taxonId(10090).scientificName("MOUSE").build();
        LinkedHashSet<Organism> organisms = new LinkedHashSet<>(List.of(organism1, organism2));

        LinkedHashSet<String> proteinNames = new LinkedHashSet<>(List.of("protein1", "protein2"));
        LinkedHashSet<String> geneNames = new LinkedHashSet<>(List.of("gene1", "gene2"));
        List<CommonOrganism> commonTaxons = List.of(new CommonOrganismBuilder().commonTaxon("Bacteroides").build(),new CommonOrganismBuilder().commonTaxon("Enterococcus").build());
        LinkedHashSet<Proteome> proteomes = new LinkedHashSet<>(List.of(new ProteomeBuilder().id("UP000005640").component("C1").build(), new ProteomeBuilder().id("UP000002494").component("C2").build()));

        return new UniParcEntryLightBuilder()
                .uniParcId("UPI0000083A08")
                .uniProtKBAccessionsSet(new LinkedHashSet<>(List.of("P52346", "P12345")))
                .organismsSet(organisms)
                .proteomesSet(proteomes)
                .geneNamesSet(geneNames)
                .commonTaxonsSet(commonTaxons)
                .proteinNamesSet(proteinNames)
                .mostRecentCrossRefUpdated(LocalDate.of(2020, 10, 25))
                .oldestCrossRefCreated(LocalDate.of(2017, 2, 12))
                .sequence(sequence)
                .sequenceFeaturesSet(seqFeatures)
                .build();
    }

    private List<SequenceFeature> getSeqFeatures() {
        SequenceFeatureLocationBuilder sflBuilder = new SequenceFeatureLocationBuilder();
        SequenceFeatureLocation sfl1 = sflBuilder.range(12, 13).alignment("M55").build();
        SequenceFeatureLocation sfl2 = sflBuilder.range(45, 89).alignment("Alignment").build();
        List<SequenceFeatureLocation> sfls = List.of(sfl1, sfl2);
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locationsSet(sfls)
                        .build();
        SequenceFeature sf3 =
                SequenceFeatureBuilder.from(sf).signatureDbType(SignatureDbType.PROSITE).build();
        return Arrays.asList(sf, sf3);
    }
}