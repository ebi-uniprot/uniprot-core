package org.uniprot.core.uniparc.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.Property;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

/**
 * @author jluo
 * @date: 23 May 2019
 */
class UniParcEntryBuilderTest {

    @Test
    void testUniParcId() {
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
    }

    @Test
    void testDatabaseCrossReferences() {
        List<UniParcDBCrossReference> xrefs = getXrefs();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferences(xrefs)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getDbXReferences());
    }

    @Test
    void testSequence() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcDBCrossReference> xrefs = getXrefs();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferences(xrefs)
                        .sequence(sequence)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getDbXReferences());
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void testUniprotExclusionReason() {
        String uniprotExclusionReason = "Small sequence";
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcDBCrossReference> xrefs = getXrefs();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferences(xrefs)
                        .sequence(sequence)
                        .uniprotExclusionReason(uniprotExclusionReason)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getDbXReferences());
        assertEquals(sequence, entry.getSequence());
        assertEquals(uniprotExclusionReason, entry.getUniProtExclusionReason());
    }

    @Test
    void testSequenceFeatures() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcDBCrossReference> xrefs = getXrefs();
        List<SequenceFeature> seqFeatures = getSeqFeatures();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferences(xrefs)
                        .sequence(sequence)
                        .sequenceFeatures(seqFeatures)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getDbXReferences());
        assertEquals(sequence, entry.getSequence());
        assertEquals(seqFeatures, entry.getSequenceFeatures());
    }

    @Test
    void testTaxonomies() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcDBCrossReference> xrefs = getXrefs();
        List<SequenceFeature> seqFeatures = getSeqFeatures();
        List<Taxonomy> taxonomies = getTaxonomies();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferences(xrefs)
                        .sequence(sequence)
                        .sequenceFeatures(seqFeatures)
                        .taxonomies(taxonomies)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getDbXReferences());
        assertEquals(sequence, entry.getSequence());
        assertEquals(seqFeatures, entry.getSequenceFeatures());
        assertEquals(taxonomies, entry.getTaxonomies());
    }

    @Test
    void testFrom() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcDBCrossReference> xrefs = getXrefs();
        List<SequenceFeature> seqFeatures = getSeqFeatures();
        List<Taxonomy> taxonomies = getTaxonomies();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferences(xrefs)
                        .sequence(sequence)
                        .sequenceFeatures(seqFeatures)
                        .taxonomies(taxonomies)
                        .build();

        UniParcEntry entry2 = new UniParcEntryBuilder().from(entry).build();
        assertEquals(entry, entry2);
        UniParcEntry entry3 =
                new UniParcEntryBuilder()
                        .from(entry)
                        .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                        .build();

        assertEquals("UPI0000083A09", entry3.getUniParcId().getValue());
        assertEquals(xrefs, entry3.getDbXReferences());
        assertEquals(sequence, entry3.getSequence());
        assertEquals(seqFeatures, entry3.getSequenceFeatures());
        assertEquals(taxonomies, entry3.getTaxonomies());
    }

    private List<Taxonomy> getTaxonomies() {
        Taxonomy taxonomy =
                TaxonomyBuilder.newInstance().taxonId(9606).scientificName("Homo sapiens").build();
        Taxonomy taxonomy2 =
                TaxonomyBuilder.newInstance().taxonId(10090).scientificName("MOUSE").build();
        return Arrays.asList(taxonomy, taxonomy2);
    }

    private List<SequenceFeature> getSeqFeatures() {
        List<Location> locations = Arrays.asList(new Location(12, 23), new Location(45, 89));
        InterproGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locations(locations)
                        .build();
        SequenceFeature sf3 =
                new SequenceFeatureBuilder()
                        .from(sf)
                        .signatureDbType(SignatureDbType.PROSITE)
                        .build();
        return Arrays.asList(sf, sf3);
    }

    private List<UniParcDBCrossReference> getXrefs() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "some gname"));
        UniParcDBCrossReference xref =
                new UniParcDBCrossReferenceBuilder()
                        .versionI(3)
                        .databaseType(UniParcDatabaseType.SWISSPROT)
                        .id("P12345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 5, 17))
                        .lastUpdated(LocalDate.of(2017, 2, 27))
                        .properties(properties)
                        .build();

        List<Property> properties2 = new ArrayList<>();
        properties2.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "some pname"));
        properties2.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));

        UniParcDBCrossReference xref2 =
                new UniParcDBCrossReferenceBuilder()
                        .versionI(1)
                        .databaseType(UniParcDatabaseType.TREMBL)
                        .id("P52345")
                        .version(7)
                        .active(true)
                        .created(LocalDate.of(2017, 2, 12))
                        .lastUpdated(LocalDate.of(2017, 4, 23))
                        .properties(properties2)
                        .build();

        return Arrays.asList(xref, xref2);
    }
}
