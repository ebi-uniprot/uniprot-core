package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;

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
                        .uniParcCrossReferencesSet(xrefs)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getUniParcCrossReferences());
    }

    @Test
    void testSequence() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getUniParcCrossReferences());
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
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .uniprotExclusionReason(uniprotExclusionReason)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getUniParcCrossReferences());
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
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .sequenceFeaturesSet(seqFeatures)
                        .build();
        assertEquals("UPI0000083A08", entry.getUniParcId().getValue());
        assertEquals(xrefs, entry.getUniParcCrossReferences());
        assertEquals(sequence, entry.getSequence());
        assertEquals(seqFeatures, entry.getSequenceFeatures());
    }

    @Test
    void testFrom() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        List<UniParcCrossReference> xrefs = uniParcDBCrossReferences();
        List<SequenceFeature> seqFeatures = sequenceFeatures();
        UniParcEntry entry =
                new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(sequence)
                        .sequenceFeaturesSet(seqFeatures)
                        .build();

        UniParcEntry entry2 = UniParcEntryBuilder.from(entry).build();
        assertEquals(entry, entry2);
        UniParcEntry entry3 =
                UniParcEntryBuilder.from(entry)
                        .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                        .build();

        assertEquals("UPI0000083A09", entry3.getUniParcId().getValue());
        assertEquals(xrefs, entry3.getUniParcCrossReferences());
        assertEquals(sequence, entry3.getSequence());
        assertEquals(seqFeatures, entry3.getSequenceFeatures());
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
                        .uniParcCrossReferencesAdd(uniParcDBCrossReferences().get(0))
                        .build();
        assertNotNull(obj.getUniParcCrossReferences());
        assertFalse(obj.getUniParcCrossReferences().isEmpty());
    }

    @Test
    void nullDatabaseCrossReference_willBeIgnore() {
        UniParcEntry obj = new UniParcEntryBuilder().uniParcCrossReferencesAdd(null).build();
        assertNotNull(obj.getUniParcCrossReferences());
        assertTrue(obj.getUniParcCrossReferences().isEmpty());
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
}
