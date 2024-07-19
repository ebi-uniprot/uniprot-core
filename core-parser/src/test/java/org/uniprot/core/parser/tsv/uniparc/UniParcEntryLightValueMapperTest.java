package org.uniprot.core.parser.tsv.uniparc;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.util.PairImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UniParcEntryLightValueMapperTest {

    @Test
    void testGetDataOrganism() {
        UniParcEntryLight entry = create();
        List<String> fields = Arrays.asList("upi", "organism", "organism_id", "proteome");

        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entry, fields);

        assertEquals(fields.size(), result.size());
        verify("UPI0000083A08", "upi", result);
        verify("Homo sapiens; MOUSE", "organism", result);
        verify("9606; 10090", "organism_id", result);
        verify("proteome1; proteome2", "proteome", result);
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

    @Test
    void testGetDates() {
        UniParcEntryLight entry = create();
        List<String> fields = Arrays.asList("oldestCrossRefCreated", "mostRecentCrossRefUpdated");
        Map<String, String> result = new UniParcEntryLightValueMapper().mapEntity(entry, fields);
        assertEquals(2, result.size());
        verify("2017-02-12", "oldestCrossRefCreated", result);
        verify("2020-10-25", "mostRecentCrossRefUpdated", result);
    }

    private void verify(String expected, String field, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }

    private UniParcEntryLight create() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<SequenceFeature> seqFeatures = getSeqFeatures();
        return new UniParcEntryLightBuilder()
                .uniParcId("UPI0000083A08")
                .uniProtKBAccessionsSet(Set.of("P12345", "P52346"))
                .organismsSet(List.of(new PairImpl<>(9606, "Homo sapiens"), new PairImpl<>(10090, "MOUSE")))
                .proteomeIdsSet(List.of("proteome1", "proteome2"))
                .geneNamesSet(List.of("gene1", "gene2"))
                .proteinNamesSet(List.of("protein1", "protein2"))
                .mostRecentCrossRefUpdated(LocalDate.of(2020, 10, 25))
                .oldestCrossRefCreated(LocalDate.of(2017, 2, 12))
                .sequence(sequence)
                .sequenceFeaturesSet(seqFeatures)
                .build();
    }

    private List<SequenceFeature> getSeqFeatures() {
        List<Location> locations = Arrays.asList(new Location(12, 23), new Location(45, 89));
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locationsSet(locations)
                        .build();
        SequenceFeature sf3 =
                SequenceFeatureBuilder.from(sf).signatureDbType(SignatureDbType.PROSITE).build();
        return Arrays.asList(sf, sf3);
    }
}