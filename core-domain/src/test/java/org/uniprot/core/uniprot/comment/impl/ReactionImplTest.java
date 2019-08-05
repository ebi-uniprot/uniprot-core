package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.TestHelper;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.builder.ReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

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
