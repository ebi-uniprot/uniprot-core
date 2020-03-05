package org.uniprot.core.uniparc.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;

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
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferencesSet(xrefs)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getDbXReferences());
    }

    @Test
    void testSequence() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferencesSet(xrefs)
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
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferencesSet(xrefs)
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
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        List<SequenceFeature> seqFeatures = sequenceFeatures();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .sequenceFeaturesSet(seqFeatures)
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
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        List<SequenceFeature> seqFeatures = sequenceFeatures();
        List<Taxonomy> taxonomies = taxonomies();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .sequenceFeaturesSet(seqFeatures)
                        .taxonomiesSet(taxonomies)
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
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        List<SequenceFeature> seqFeatures = sequenceFeatures();
        List<Taxonomy> taxonomies = taxonomies();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .databaseCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .sequenceFeaturesSet(seqFeatures)
                        .taxonomiesSet(taxonomies)
                        .build();

        UniParcEntry entry2 = UniParcEntryBuilder.from(entry).build();
        assertEquals(entry, entry2);
        UniParcEntry entry3 =
                UniParcEntryBuilder.from(entry)
                        .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                        .build();

        assertEquals("UPI0000083A09", entry3.getUniParcId().getValue());
        assertEquals(xrefs, entry3.getDbXReferences());
        assertEquals(sequence, entry3.getSequence());
        assertEquals(seqFeatures, entry3.getSequenceFeatures());
        assertEquals(taxonomies, entry3.getTaxonomies());
    }

    @Test
    void canAddIdFromString() {
        UniParcEntry entry = new UniParcEntryBuilder().uniParcId("abc").build();
        assertEquals("abc", entry.getUniParcId().getValue());
    }

    @Test
    void canAddSingleDatabaseCrossReference() {
        UniParcEntry obj =
                new UniParcEntryBuilder()
                        .databaseCrossReferencesAdd(uniParcDBCrossReferences().get(0))
                        .build();
        assertNotNull(obj.getDbXReferences());
        assertFalse(obj.getDbXReferences().isEmpty());
    }

    @Test
    void nullDatabaseCrossReference_willBeIgnore() {
        UniParcEntry obj = new UniParcEntryBuilder().databaseCrossReferencesAdd(null).build();
        assertNotNull(obj.getDbXReferences());
        assertTrue(obj.getDbXReferences().isEmpty());
    }

    @Test
    void canAddSingleSequenceFeature() {
        UniParcEntry obj =
                new UniParcEntryBuilder().sequenceFeaturesAdd(sequenceFeatures().get(0)).build();
        assertNotNull(obj.getSequenceFeatures());
        assertFalse(obj.getSequenceFeatures().isEmpty());
    }

    @Test
    void nullSequenceFeature_willBeIgnore() {
        UniParcEntry obj = new UniParcEntryBuilder().sequenceFeaturesAdd(null).build();
        assertNotNull(obj.getSequenceFeatures());
        assertTrue(obj.getSequenceFeatures().isEmpty());
    }

    @Test
    void canAddSingleTaxonomy() {
        UniParcEntry obj = new UniParcEntryBuilder().taxonomiesAdd(taxonomies().get(0)).build();
        assertNotNull(obj.getTaxonomies());
        assertFalse(obj.getTaxonomies().isEmpty());
    }

    @Test
    void nullTaxonomy_willBeIgnore() {
        UniParcEntry obj = new UniParcEntryBuilder().taxonomiesAdd(null).build();
        assertNotNull(obj.getTaxonomies());
        assertTrue(obj.getTaxonomies().isEmpty());
    }
}
