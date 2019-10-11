package org.uniprot.core.uniprot.comment.builder;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

class APCommentBuilderTest {
    @Test
    void testNewInstance() {
        APCommentBuilder builder1 = new APCommentBuilder();
        APCommentBuilder builder2 = new APCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertNotSame(builder1, builder2);
    }

    @Test
    void testSetEvents() {
        APCommentBuilder builder = new APCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_INITIATION);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        AlternativeProductsComment comment = builder.events(events).build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(0, comment.getIsoforms().size());
        //   assertFalse(comment.getNote().isPresent());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
    }

    @Test
    void testSetEventIsoforms() {
        APCommentBuilder builder = new APCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_INITIATION);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> apIsoforms = singletonList(createAPIsoform());
        AlternativeProductsComment comment = builder.events(events).isoforms(apIsoforms).build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(1, comment.getIsoforms().size());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
    }

    @Test
    void testSetEventIsoformsNote() {
        APCommentBuilder builder = new APCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_INITIATION);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> apIsoforms = singletonList(createAPIsoform());
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note apNote = new NoteBuilder(texts).build();
        AlternativeProductsComment comment =
                builder.events(events).isoforms(apIsoforms).note(apNote).build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(1, comment.getIsoforms().size());
        assertNotNull(comment.getNote());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
    }

    @Test
    void testCreateAPNote() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note apNote = new NoteBuilder(texts).build();
        assertEquals(2, apNote.getTexts().size());
    }

    @Test
    void testCreateIsoformName() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        IsoformName isoformName = createIsoformName(name, evidences);
        assertEquals(name, isoformName.getValue());
        assertEquals(2, isoformName.getEvidences().size());
    }

    @Test
    void testAPIsoformBuilderSetName() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform = isoformBuilder.name(createIsoformName(name, evidences)).build();

        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(0, apIsoform.getSynonyms().size());
        assertEquals(0, apIsoform.getIsoformIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }

    @Test
    void testAPIsoformBuilderSetNameSynonym() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));
        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform =
                isoformBuilder
                        .name(createIsoformName(name, evidences))
                        .synonyms(isoformSynonyms)
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(2, apIsoform.getSynonyms().size());
        assertEquals(0, apIsoform.getIsoformIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }

    @Test
    void testAPIsoformBuilderSetNameSynonymId() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform =
                isoformBuilder
                        .name(createIsoformName(name, evidences))
                        .synonyms(isoformSynonyms)
                        .ids(asList("isoID1", "isoID2", "isoID3"))
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(2, apIsoform.getSynonyms().size());
        assertEquals(3, apIsoform.getIsoformIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }

    @Test
    void testAPIsoformBuilder() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform =
                isoformBuilder
                        .name(createIsoformName(name, evidences))
                        .synonyms(isoformSynonyms)
                        .ids(asList("isoID1", "isoID2", "isoID3"))
                        .sequenceStatus(IsoformSequenceStatus.DISPLAYED)
                        .sequenceIds(singletonList("someSeqId"))
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(2, apIsoform.getSynonyms().size());
        assertEquals(3, apIsoform.getIsoformIds().size());
        assertEquals(IsoformSequenceStatus.DISPLAYED, apIsoform.getIsoformSequenceStatus());
        assertEquals(1, apIsoform.getSequenceIds().size());
    }

    @Test
    void canAddSingleIsoform() {
        AlternativeProductsComment obj =
                new APCommentBuilder().addIsoform(createAPIsoform()).build();
        assertNotNull(obj.getIsoforms());
        assertFalse(obj.getIsoforms().isEmpty());
        assertTrue(obj.hasIsoforms());
    }

    @Test
    void nullIsoform_willBeIgnore() {
        AlternativeProductsComment obj = new APCommentBuilder().addIsoform(null).build();
        assertNotNull(obj.getIsoforms());
        assertTrue(obj.getIsoforms().isEmpty());
        assertFalse(obj.hasIsoforms());
    }

    @Test
    void canAddSingleEvent() {
        AlternativeProductsComment obj =
                new APCommentBuilder().addEvent(APEventType.ALTERNATIVE_INITIATION).build();
        assertNotNull(obj.getEvents());
        assertFalse(obj.getEvents().isEmpty());
        assertTrue(obj.hasEvents());
    }

    @Test
    void nullEvent_willBeIgnore() {
        AlternativeProductsComment obj = new APCommentBuilder().addEvent(null).build();
        assertNotNull(obj.getEvents());
        assertTrue(obj.getEvents().isEmpty());
        assertFalse(obj.hasEvents());
    }

    @Test
    void canCreateBuilderFromInstance() {
        APIsoform obj = new APIsoformBuilder().build();
        APIsoformBuilder builder = new APIsoformBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        APIsoform obj = new APIsoformBuilder().build();
        APIsoform obj2 = new APIsoformBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    private APIsoform createAPIsoform() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        return isoformBuilder
                .name(createIsoformName(name, evidences))
                .synonyms(isoformSynonyms)
                .ids(asList("isoID1", "isoID2", "isoID3"))
                .sequenceStatus(IsoformSequenceStatus.DISPLAYED)
                .sequenceIds(singletonList("someSeqId"))
                .build();
    }

    private static IsoformName createIsoformName(String name, List<Evidence> evidences) {
        return new IsoformNameBuilder(name, evidences).build();
    }
}
