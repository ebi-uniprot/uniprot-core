package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APEvent;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformSynonym;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class APCommentBuilderTest {
    @Test
    public void testNewInstance() {
        APCommentBuilder builder1 = APCommentBuilder.newInstance();
        APCommentBuilder builder2 = APCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1== builder2);
    }

    
    @Test
    public void testSetEvents() {
        APCommentBuilder builder = APCommentBuilder.newInstance();
        List<APEvent> events = new ArrayList<>();
        events.add(APCommentBuilder.createEvent("event1"));
        events.add(APCommentBuilder.createEvent("event2"));
        AlternativeProductsComment comment = builder.setEvents(events).build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(0, comment.getIsoforms().size());
        assertNull(comment.getNote());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
    }

    @Test
    public void testSetEventIsoforms() {
        APCommentBuilder builder = APCommentBuilder.newInstance();
        List<APEvent> events = new ArrayList<>();
        events.add(APCommentBuilder.createEvent("event1"));
        events.add(APCommentBuilder.createEvent("event2"));
        List<APIsoform> apIsoforms =Arrays.asList(createAPIsoform());
        AlternativeProductsComment comment = builder.setEvents(events)
                .setIsoforms(apIsoforms)
                .build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(1, comment.getIsoforms().size());
        assertNull(comment.getNote());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
    }

    private APIsoform createAPIsoform(){
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformSynonym> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn1, evidences));
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn2, Collections.emptyList()));
        List<IsoformId> isoformIds = new ArrayList<>();
        isoformIds.add(APCommentBuilder.createIsoformId("isoID1"));
        isoformIds.add(APCommentBuilder.createIsoformId("isoID2"));
        isoformIds.add(APCommentBuilder.createIsoformId("isoID3"));

        APCommentBuilder.APIsoformBuilder isoformBuilder = APCommentBuilder.APIsoformBuilder.newInstance();
        return
                isoformBuilder.setIsoformName(APCommentBuilder.createIsoformName(name, evidences))
                        .setIsoformSynonyms(isoformSynonyms)
                        .setIsoformIds(isoformIds)
                        .setIsoformSequenceStatus(IsoformSequenceStatus.DISPLAYED)
                        .setSequenceIds(Arrays.asList("someSeqId"))
                        .build();
    }
    
    @Test
    public void testSetEventIsoformsNote() {
        APCommentBuilder builder = APCommentBuilder.newInstance();
        List<APEvent> events = new ArrayList<>();
        events.add(APCommentBuilder.createEvent("event1"));
        events.add(APCommentBuilder.createEvent("event2"));
        List<APIsoform> apIsoforms =Arrays.asList(createAPIsoform());
        List<EvidencedValue> texts = createEvidenceValues();
        APNote apNote = APCommentBuilder.createAPNote(texts);
        AlternativeProductsComment comment = builder.setEvents(events)
                .setIsoforms(apIsoforms)
                .setNote(apNote)
                .build();
        assertEquals(2, comment.getEvents().size());
        assertEquals(1, comment.getIsoforms().size());
        assertNotNull(comment.getNote());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
    }

    @Test
    public void testCreateEvent() {
        String val = "some event";
        APEvent event = APCommentBuilder.createEvent(val);
        assertEquals(val, event.getValue());
    }

    @Test
    public void testCreateAPNote() {
        List<EvidencedValue> texts = createEvidenceValues();
        APNote apNote = APCommentBuilder.createAPNote(texts);
        assertEquals(2, apNote.getTexts().size());
    }

    @Test
    public void testCreateIsoformSynonym() {
        List<Evidence> evidences = createEvidences();
        String name = "Some syno";
        IsoformSynonym isoformName = APCommentBuilder.createIsoformSynonym(name, evidences);
        assertEquals(name, isoformName.getValue());
        assertEquals(2, isoformName.getEvidences().size());
    }

    @Test
    public void testCreateIsoformName() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        IsoformName isoformName = APCommentBuilder.createIsoformName(name, evidences);
        assertEquals(name, isoformName.getValue());
        assertEquals(2, isoformName.getEvidences().size());
    }

    
    @Test
    public void testCreateIsoformIde() {
        String name = "Some Id";
        IsoformId isoformId = APCommentBuilder.createIsoformId(name);
        assertEquals(name, isoformId.getValue());
    }
    
    @Test
    public void testAPIsoformBuilderSetName() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        APCommentBuilder.APIsoformBuilder isoformBuilder = APCommentBuilder.APIsoformBuilder.newInstance();
        APIsoform apIsoform =
                isoformBuilder.setIsoformName(APCommentBuilder.createIsoformName(name, evidences))
                      
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(0, apIsoform.getSynonyms().size());
        assertEquals(0, apIsoform.getIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }
    @Test
    public void testAPIsoformBuilderSetNameSynonym() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformSynonym> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn1, evidences));
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn2, Collections.emptyList()));
        APCommentBuilder.APIsoformBuilder isoformBuilder = APCommentBuilder.APIsoformBuilder.newInstance();
        APIsoform apIsoform =
                isoformBuilder.setIsoformName(APCommentBuilder.createIsoformName(name, evidences))
                          .setIsoformSynonyms(isoformSynonyms)
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(2, apIsoform.getSynonyms().size());
        assertEquals(0, apIsoform.getIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }
    @Test
    public void testAPIsoformBuilderSetNameSynonymId() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformSynonym> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn1, evidences));
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn2, Collections.emptyList()));
        List<IsoformId> isoformIds = new ArrayList<>();
        isoformIds.add(APCommentBuilder.createIsoformId("isoID1"));
        isoformIds.add(APCommentBuilder.createIsoformId("isoID2"));
        isoformIds.add(APCommentBuilder.createIsoformId("isoID3"));

        APCommentBuilder.APIsoformBuilder isoformBuilder = APCommentBuilder.APIsoformBuilder.newInstance();
        APIsoform apIsoform =
                isoformBuilder.setIsoformName(APCommentBuilder.createIsoformName(name, evidences))
                          .setIsoformSynonyms(isoformSynonyms)
                          .setIsoformIds(isoformIds)
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(2, apIsoform.getSynonyms().size());
        assertEquals(3, apIsoform.getIds().size());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
        assertEquals(0, apIsoform.getSequenceIds().size());
    }


    @Test
    public void testAPIsoformBuilder() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformSynonym> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn1, evidences));
        isoformSynonyms.add(APCommentBuilder.createIsoformSynonym(syn2, Collections.emptyList()));
        List<IsoformId> isoformIds = new ArrayList<>();
        isoformIds.add(APCommentBuilder.createIsoformId("isoID1"));
        isoformIds.add(APCommentBuilder.createIsoformId("isoID2"));
        isoformIds.add(APCommentBuilder.createIsoformId("isoID3"));

        APCommentBuilder.APIsoformBuilder isoformBuilder = APCommentBuilder.APIsoformBuilder.newInstance();
        APIsoform apIsoform =
                isoformBuilder.setIsoformName(APCommentBuilder.createIsoformName(name, evidences))
                        .setIsoformSynonyms(isoformSynonyms)
                        .setIsoformIds(isoformIds)
                        .setIsoformSequenceStatus(IsoformSequenceStatus.DISPLAYED)
                        .setSequenceIds(Arrays.asList("someSeqId"))
                        .build();
        assertEquals(name, apIsoform.getName().getValue());
        assertEquals(2, apIsoform.getSynonyms().size());
        assertEquals(3, apIsoform.getIds().size());
        assertEquals(IsoformSequenceStatus.DISPLAYED, apIsoform.getIsoformSequenceStatus());
        assertEquals(1, apIsoform.getSequenceIds().size());
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(EvidenceFactory.from("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(EvidenceFactory.from("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
