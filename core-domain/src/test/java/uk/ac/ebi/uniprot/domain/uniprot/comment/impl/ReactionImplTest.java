package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.ReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

class ReactionImplTest {
    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(xref(ReactionReferenceType.RHEA, "RHEA:123"));
        references.add(xref(ReactionReferenceType.RHEA, "RHEA:323"));
        references.add(xref(ReactionReferenceType.CHEBI, "ChEBI:3243"));
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
        Reaction reaction = new ReactionBuilder()
                .name(name).references(references).ecNumber(ecNumber).evidences(evidences).build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(ecNumber, reaction.getEcNumber());
        assertEquals(references, reaction.getReactionReferences());
        TestHelper.verifyJson(reaction);
    }

    @Test
    void testNameAndEvidence() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        Reaction reaction = new ReactionBuilder()
                .name(name).evidences(evidences).build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(null, reaction.getEcNumber());
        assertTrue(reaction.getReactionReferences().isEmpty());
        TestHelper.verifyJson(reaction);
    }

    @Test
    void testNameAndEvidenceAndEC() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
        Reaction reaction = new ReactionBuilder()
                .name(name).ecNumber(ecNumber).evidences(evidences).build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(ecNumber, reaction.getEcNumber());
        assertTrue(reaction.getReactionReferences().isEmpty());
        TestHelper.verifyJson(reaction);
    }

    @Test
    void testNameAndEvidenceAndReferences() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(xref(ReactionReferenceType.RHEA, "RHEA:123"));
        references.add(xref(ReactionReferenceType.RHEA, "RHEA:323"));
        references.add(xref(ReactionReferenceType.CHEBI, "ChEBI:3243"));

        Reaction reaction = new ReactionBuilder()
                .name(name)
                .references(references)
                .evidences(evidences)
                .build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(null, reaction.getEcNumber());
        assertEquals(references, reaction.getReactionReferences());
        TestHelper.verifyJson(reaction);
    }

    private DBCrossReference<ReactionReferenceType> xref(ReactionReferenceType type, String id) {
        return new DBCrossReferenceBuilder<ReactionReferenceType>()
                .databaseType(type)
                .id(id)
                .build();
    }
}
