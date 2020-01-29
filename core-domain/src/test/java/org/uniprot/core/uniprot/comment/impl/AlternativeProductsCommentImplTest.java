package org.uniprot.core.uniprot.comment.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.createNote;
import static org.uniprot.core.ObjectsForTests.createSynonyms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.APCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.APIsoformBuilder;
import org.uniprot.core.uniprot.comment.builder.IsoformNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

class AlternativeProductsCommentImplTest {
    List<APIsoform> createIsoforms() {
        List<APIsoform> isoforms = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        IsoformName isoformName1 = new IsoformNameBuilder("Name 1", evidences).build();
        APIsoform apIsoform1 =
                new APIsoformBuilder()
                        .name(isoformName1)
                        .sequenceStatus(IsoformSequenceStatus.DISPLAYED)
                        .build();
        isoforms.add(apIsoform1);

        String name = "Name 2";

        IsoformName isoformName = new IsoformNameBuilder(name, evidences).build();
        List<IsoformName> synonyms = createSynonyms();
        Note note = createNote();
        List<String> sequenceIds = asList("seq 1", "seq 2");

        APIsoform apIsoform2 =
                new APIsoformBuilder()
                        .name(isoformName)
                        .sequenceStatus(IsoformSequenceStatus.DESCRIBED)
                        .synonyms(synonyms)
                        .note(note)
                        .ids(asList("id 1", "id 2"))
                        .sequenceIds(sequenceIds)
                        .build();

        isoforms.add(apIsoform2);
        return isoforms;
    }

    @Test
    void testConstructor() {
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_PROMOTER_USAGE);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> isoforms = createIsoforms();
        Note note = createNote();
        AlternativeProductsComment comment =
                new APCommentBuilder().events(events).isoforms(isoforms).note(note).build();
        assertEquals(events, comment.getEvents());
        assertEquals(isoforms, comment.getIsoforms());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment.getCommentType());
    }

    @Test
    void testConstructorNoNote() {
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> isoforms = createIsoforms();
        Note note = null;
        AlternativeProductsComment comment =
                new APCommentBuilder().events(events).isoforms(isoforms).note(note).build();
        assertEquals(events, comment.getEvents());
        assertEquals(isoforms, comment.getIsoforms());
        assertEquals(note, comment.getNote());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        AlternativeProductsComment obj = new AlternativeProductsCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        AlternativeProductsComment impl =
                new AlternativeProductsCommentImpl(
                        Collections.emptyList(), Collections.emptyList(), new NoteImpl());
        AlternativeProductsComment obj = APCommentBuilder.from(impl).build();

        assertTrue(impl.hasNote());
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
