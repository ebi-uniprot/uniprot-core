package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APIsoformBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.IsoformNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;
import static uk.ac.ebi.uniprot.domain.uniprot.comment.impl.ImplTestHelper.createNote;
import static uk.ac.ebi.uniprot.domain.uniprot.comment.impl.ImplTestHelper.createSynonyms;

class APIsoformImplTest {
    @Test
    void testIsoformNameImpl() {
        String name = "some name";
        List<Evidence> evidences = createEvidences();
        IsoformName isoformName = new IsoformNameBuilder(name, evidences).build();
        assertEquals(name, isoformName.getValue());
        assertEquals(evidences, isoformName.getEvidences());
        TestHelper.verifyJson(isoformName);
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

        APIsoform apIsoform = new APIsoformBuilder()
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
        TestHelper.verifyJson(apIsoform);
    }

    private List<IsoformId> createIsoformIds() {
        List<IsoformId> ids = new ArrayList<>();
        ids.add(new APIsoformImpl.IsoformIdImpl("id 1"));
        ids.add(new APIsoformImpl.IsoformIdImpl("id 2"));
        return ids;
    }
}
