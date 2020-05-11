package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniparc.impl.UniParcEntryBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcEntryValueMapperTest {

    @Test
    void testGetDataOrganism() {
        UniParcEntry entry = create();
        List<String> fields = Arrays.asList("upi", "organism", "organism_id");

        Map<String, String> result = new UniParcEntryValueMapper().mapEntity(entry, fields);

        assertEquals(fields.size(), result.size());
        verify("UPI0000083A08", "upi", result);
        verify("Homo sapiens; MOUSE", "organism", result);
        verify("9606; 10090", "organism_id", result);
    }

    @Test
    void testGetDataProteinGene() {
        UniParcEntry entry = create();
        List<String> fields = Arrays.asList("upi", "protein", "gene", "accession");
        Map<String, String> result = new UniParcEntryValueMapper().mapEntity(entry, fields);
        assertEquals(9, result.size());
        verify("UPI0000083A08", "upi", result);
        verify("some pname;some pname", "protein", result);
        verify("some gname;", "gene", result);
        verify("P12345; P52346", "accession", result);
    }

    @Test
    void testGetDate() {
        UniParcEntry entry = create();
        List<String> fields = Arrays.asList("upi", "first_seen", "last_seen");
        Map<String, String> result = new UniParcEntryValueMapper().mapEntity(entry, fields);
        assertEquals(9, result.size());
        verify("UPI0000083A08", "upi", result);
        verify("2017-02-12", "first_seen", result);
        verify("2017-04-23", "last_seen", result);
    }

    @Test
    void testGetSequence() {
        UniParcEntry entry = create();
        List<String> fields = Arrays.asList("upi", "checksum", "length", "sequence");
        Map<String, String> result = new UniParcEntryValueMapper().mapEntity(entry, fields);
        assertEquals(fields.size(), result.size());
        verify("UPI0000083A08", "upi", result);
        verify("AA7812161AF4EB5E", "checksum", result);
        verify("30", "length", result);
        verify("MVSWGRFICLVVVTMATLSLARPSFSLVED", "sequence", result);
    }

    @Test
    void testGetSequenceFeature() {
        UniParcEntry entry = create();
        List<String> fields = Arrays.asList("upi", "HAMAP", "InterPro", "Pfam", "PROSITE");
        Map<String, String> result = new UniParcEntryValueMapper().mapEntity(entry, fields);
        assertEquals(15, result.size());
        verify("UPI0000083A08", "upi", result);
        verify("", "HAMAP", result);
        verify("id1", "InterPro", result);
        verify("sigId2", "Pfam", result);
        verify("sigId2", "PROSITE", result);
    }

    private void verify(String expected, String field, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }

    private UniParcEntry create() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcCrossReference> xrefs = getXrefs();
        List<SequenceFeature> seqFeatures = getSeqFeatures();
        List<Taxonomy> taxonomies = getTaxonomies();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .sequenceFeaturesSet(seqFeatures)
                        .taxonomiesSet(taxonomies)
                        .build();
        return entry;
    }

    private List<Taxonomy> getTaxonomies() {
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        Taxonomy taxonomy2 = new TaxonomyBuilder().taxonId(10090).scientificName("MOUSE").build();
        return Arrays.asList(taxonomy, taxonomy2);
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

    private List<UniParcCrossReference> getXrefs() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_GENE_NAME, "some gname"));
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .versionI(3)
                        .database(UniParcDatabase.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 5, 17))
                        .lastUpdated(LocalDate.of(2017, 2, 27))
                        .propertiesSet(properties)
                        .build();

        List<Property> properties2 = new ArrayList<>();
        properties2.add(new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties2.add(new Property(UniParcCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));

        UniParcCrossReference xref2 =
                new UniParcCrossReferenceBuilder()
                        .versionI(1)
                        .database(UniParcDatabase.TREMBL)
                        .id("P52346")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 2, 12))
                        .lastUpdated(LocalDate.of(2017, 4, 23))
                        .propertiesSet(properties2)
                        .build();

        return Arrays.asList(xref, xref2);
    }
}
