package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.impl.ECNumberBuilder;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;

class ReactionImplTest {

    private List<CrossReference<ReactionDatabase>> references =
            Arrays.asList(
                    xref(ReactionDatabase.RHEA, "RHEA:123"),
                    xref(ReactionDatabase.RHEA, "RHEA:323"),
                    xref(ReactionDatabase.CHEBI, "ChEBI:3243"));
    private ECNumber ecNumber = new ECNumberBuilder("1.2.4.5").build();

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        Reaction reaction =
                new ReactionBuilder()
                        .name(name)
                        .reactionCrossReferencesSet(references)
                        .ecNumber(ecNumber)
                        .evidencesSet(evidences)
                        .build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(ecNumber, reaction.getEcNumber());
        assertEquals(references, reaction.getReactionCrossReferences());
    }

    @Test
    void testNameAndEvidence() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        Reaction reaction = new ReactionBuilder().name(name).evidencesSet(evidences).build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(null, reaction.getEcNumber());
        assertTrue(reaction.getReactionCrossReferences().isEmpty());
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
        assertTrue(reaction.getReactionCrossReferences().isEmpty());
    }

    @Test
    void testNameAndEvidenceAndReferences() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";

        Reaction reaction =
                new ReactionBuilder()
                        .name(name)
                        .reactionCrossReferencesSet(references)
                        .evidencesSet(evidences)
                        .build();
        assertEquals(evidences, reaction.getEvidences());
        assertEquals(name, reaction.getName());
        assertEquals(null, reaction.getEcNumber());
        assertEquals(references, reaction.getReactionCrossReferences());
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
                "Reaction=name; Xref=Rhea:RHEA:123, Rhea:RHEA:323, ChEBI:ChEBI:3243; EC=1.2.4.5;"
                        + " Evidence={ECO:0000255|PROSITE-ProRule:PRU10028,"
                        + " ECO:0000256|PIRNR:PIRNR001361};",
                impl.toString());
    }

    private CrossReference<ReactionDatabase> xref(ReactionDatabase type, String id) {
        return new CrossReferenceBuilder<ReactionDatabase>().database(type).id(id).build();
    }
}
