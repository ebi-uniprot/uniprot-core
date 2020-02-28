package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionDatabase;
import org.uniprot.core.uniprot.comment.builder.ReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

class ReactionImplTest {

    private List<DBCrossReference<ReactionDatabase>> references =
            Arrays.asList(
                    xref(ReactionDatabase.RHEA, "RHEA:123"),
                    xref(ReactionDatabase.RHEA, "RHEA:323"),
                    xref(ReactionDatabase.CHEBI, "ChEBI:3243"));
    private ECNumber ecNumber = new ECNumberImpl("1.2.4.5");

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        Reaction reaction =
                new ReactionBuilder()
                        .name(name)
                        .reactionReferencesSet(references)
                        .ecNumber(ecNumber)
                        .evidencesSet(evidences)
                        .build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(ecNumber, reaction.getEcNumber());
        assertEquals(references, reaction.getReactionReferences());
    }

    @Test
    void testNameAndEvidence() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        Reaction reaction = new ReactionBuilder().name(name).evidencesSet(evidences).build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(null, reaction.getEcNumber());
        assertTrue(reaction.getReactionReferences().isEmpty());
    }

    @Test
    void testNameAndEvidenceAndEC() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        Reaction reaction =
                new ReactionBuilder().name(name).ecNumber(ecNumber).evidencesSet(evidences).build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(ecNumber, reaction.getEcNumber());
        assertTrue(reaction.getReactionReferences().isEmpty());
    }

    @Test
    void testNameAndEvidenceAndReferences() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";

        Reaction reaction =
                new ReactionBuilder()
                        .name(name)
                        .reactionReferencesSet(references)
                        .evidencesSet(evidences)
                        .build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(null, reaction.getEcNumber());
        assertEquals(references, reaction.getReactionReferences());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Reaction obj = new ReactionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Reaction impl = new ReactionImpl("name", references, ecNumber, createEvidences());
        Reaction obj = ReactionBuilder.from(impl).build();

        assertTrue(impl.hasEvidences());
        assertTrue(impl.hasName());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toString_test() {
        Reaction impl = new ReactionImpl("name", references, ecNumber, createEvidences());
        assertEquals(
                "Reaction=name; Xref=Rhea:RHEA:123, Rhea:RHEA:323, ChEBI:ChEBI:3243; EC=1.2.4.5; Evidence={ECO:0000255|PROSITE-ProRule:PRU10028, ECO:0000256|PIRNR:PIRNR001361};",
                impl.toString());
    }

    private DBCrossReference<ReactionDatabase> xref(ReactionDatabase type, String id) {
        return new DBCrossReferenceBuilder<ReactionDatabase>().databaseType(type).id(id).build();
    }
}
