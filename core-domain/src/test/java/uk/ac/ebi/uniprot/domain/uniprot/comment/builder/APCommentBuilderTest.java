package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

public class APCommentBuilderTest {
    @Test
    public void testNewInstance() {
        APCommentBuilder builder1 = new APCommentBuilder();
        APCommentBuilder builder2 = new APCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertNotSame(builder1, builder2);
    }

    @Test
    public void testSetEvents() {
        APCommentBuilder builder = new APCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_INITIATION);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        AlternativeProductsComment comment = builder.events(events).build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(0, comment.getIsoforms().size());
        //   assertFalse(comment.getNote().isPresent());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetEventIsoforms() {
        APCommentBuilder builder = new APCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_INITIATION);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> apIsoforms = singletonList(createAPIsoform());
        AlternativeProductsComment comment = builder.events(events)
                .isoforms(apIsoforms)
                .build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(1, comment.getIsoforms().size());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetEventIsoformsNote() {
        APCommentBuilder builder = new APCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_INITIATION);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> apIsoforms = singletonList(createAPIsoform());
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note apNote = new NoteBuilder(texts).build();
        AlternativeProductsComment comment = builder.events(events)
                .isoforms(apIsoforms)
                .note(apNote)
                .build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(1, comment.getIsoforms().size());
        assertNotNull(comment.getNote());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testCreateAPNote() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note apNote = new NoteBuilder(texts).build();
        assertEquals(2, apNote.getTexts().size());
    }

    @Test
    public void testCreateIsoformName() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        IsoformName isoformName = createIsoformName(name, evidences);
        assertEquals(name, isoformName.getValue());
        assertEquals(2, isoformName.getEvidences().size());
    }

    @Test
    public void testAPIsoformBuilderSetName() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform =
                isoformBuilder.name(createIsoformName(name, evidences))

                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(0, apIsoform.getSynonyms().size());
        assertEquals(0, apIsoform.getIsoformIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }

    @Test
    public void testAPIsoformBuilderSetNameSynonym() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));
        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform =
                isoformBuilder.name(createIsoformName(name, evidences))
                        .synonyms(isoformSynonyms)
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(2, apIsoform.getSynonyms().size());
        assertEquals(0, apIsoform.getIsoformIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }

    @Test
    public void testAPIsoformBuilderSetNameSynonymId() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform =
                isoformBuilder.name(createIsoformName(name, evidences))
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
    public void testAPIsoformBuilder() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        APIsoform apIsoform =
                isoformBuilder.name(createIsoformName(name, evidences))
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

    private APIsoform createAPIsoform() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(createIsoformName(syn1, evidences));
        isoformSynonyms.add(createIsoformName(syn2, emptyList()));

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        return
                isoformBuilder.name(createIsoformName(name, evidences))
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
