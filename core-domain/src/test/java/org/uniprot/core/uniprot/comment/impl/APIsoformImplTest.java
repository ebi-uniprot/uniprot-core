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
import org.uniprot.core.uniprot.comment.builder.APIsoformBuilder;
import org.uniprot.core.uniprot.comment.builder.IsoformNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

class APIsoformImplTest {
    @Test
    void testIsoformNameImpl() {
        String name = "some name";
        List<Evidence> evidences = createEvidences();
        IsoformName isoformName = new IsoformNameBuilder(name, evidences).build();
        assertEquals(name, isoformName.getValue());
        assertEquals(evidences, isoformName.getEvidences());
    }

    @Test
    void testAPIsoformFull() {
        String name = "some name";
        List<Evidence> evidences = createEvidences();
        IsoformName isoformName = new IsoformNameBuilder(name, evidences).build();
        List<IsoformName> synonyms = createSynonyms();
        Note note = createNote();
        List<IsoformId> isoformIds = createIsoformIds();
        List<String> sequenceIds = asList("seq 1", "seq 2");

        APIsoform apIsoform =
                new APIsoformBuilder()
                        .name(isoformName)
                        .synonyms(synonyms)
                        .note(note)
                        .ids(asList("id 1", "id 2"))
                        .sequenceIds(sequenceIds)
                        .sequenceStatus(IsoformSequenceStatus.DESCRIBED)
                        .build();

        assertEquals(isoformName, apIsoform.getName());
        assertEquals(synonyms, apIsoform.getSynonyms());
        assertEquals(note, apIsoform.getNote());
        assertEquals(isoformIds, apIsoform.getIsoformIds());
        assertEquals(sequenceIds, apIsoform.getSequenceIds());
        assertEquals(IsoformSequenceStatus.DESCRIBED, apIsoform.getIsoformSequenceStatus());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        APIsoform obj = new APIsoformImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_IsoformName() {
        IsoformName obj = new APIsoformImpl.IsoformNameImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_IsoformId() {
        IsoformId obj = new APIsoformImpl.IsoformIdImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        APIsoform impl =
                new APIsoformImpl(
                        new APIsoformImpl.IsoformNameImpl(),
                        Collections.emptyList(),
                        new NoteImpl(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        IsoformSequenceStatus.DESCRIBED);
        APIsoform obj = new APIsoformBuilder().from(impl).build();

        assertTrue(impl.hasName());
        assertTrue(impl.hasIsoformSequenceStatus());
        assertTrue(impl.hasNote());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    private List<IsoformId> createIsoformIds() {
        List<IsoformId> ids = new ArrayList<>();
        ids.add(new APIsoformImpl.IsoformIdImpl("id 1"));
        ids.add(new APIsoformImpl.IsoformIdImpl("id 2"));
        return ids;
    }
}
