package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

class BPCPCommentBuilderTest {
    @Test
    void testNewInstance() {
        BPCPCommentBuilder builder1 = new BPCPCommentBuilder();
        BPCPCommentBuilder builder2 = new BPCPCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetAbsorption() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        Absorption absorption =
                new AbsorptionBuilder()
                        .max(max)
                        .approximate(true)
                        .note(note)
                        .evidencesSet(createEvidences())
                        .build();
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment = builder.absorption(absorption).build();

        assertEquals(absorption, comment.getAbsorption());
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertTrue(comment.getKineticParameters() == null);
        assertFalse(comment.getPhDependence() != null);
        assertFalse(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
    }

    @Test
    void testSetKineticParameters() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = new NoteBuilder(texts).build();
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment = builder.absorption(absorption).kineticParameters(kp).build();
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertNotNull(comment.getKineticParameters());
        assertFalse(comment.getPhDependence() != null);
        assertFalse(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
    }

    private Absorption createAbsorption(int max, boolean b, Note note, List<Evidence> evidences) {
        return new AbsorptionBuilder()
                .approximate(b)
                .note(note)
                .evidencesSet(evidences)
                .max(max)
                .build();
    }

    private MaximumVelocity createMaximumVelocity(
            double v, String unit, String enzyme, List<Evidence> evidences) {
        return new MaximumVelocityBuilder()
                .velocity(v)
                .unit(unit)
                .enzyme(enzyme)
                .evidencesSet(evidences)
                .build();
    }

    @Test
    void testSetPHDependence() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = new NoteBuilder(texts).build();
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .phDependence(new PhDependenceBuilder(texts).build())
                        .build();
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertTrue(comment.getKineticParameters() != null);
        assertTrue(comment.getPhDependence() != null);
        assertFalse(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
    }

    @Test
    void testSetRedoxPotential() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = new NoteBuilder(texts).build();
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .phDependence(new PhDependenceBuilder(texts).build())
                        .redoxPotential(new RedoxPotentialBuilder(texts).build())
                        .build();
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertTrue(comment.getKineticParameters() != null);
        assertTrue(comment.getPhDependence() != null);
        assertTrue(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
    }

    @Test
    void testSetTemperatureDependence() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = new NoteBuilder(texts).build();
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .phDependence(new PhDependenceBuilder(texts).build())
                        .redoxPotential(new RedoxPotentialBuilder(texts).build())
                        .temperatureDependence(new TemperatureDependenceBuilder(texts).build())
                        .build();
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertNotNull(comment.getKineticParameters());
        assertNotNull(comment.getPhDependence());
        assertNotNull(comment.getRedoxPotential());
        assertNotNull(comment.getTemperatureDependence());
    }

    @Test
    void testCreatePHDependence() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        PhDependence phDependence = new PhDependenceBuilder(texts).build();
        assertEquals(2, phDependence.getTexts().size());
        assertEquals("value1", phDependence.getTexts().get(0).getValue());
        assertEquals("value2", phDependence.getTexts().get(1).getValue());
    }

    @Test
    void testCreateRedoxPotential() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        RedoxPotential redoxPotential = new RedoxPotentialBuilder(texts).build();
        assertEquals(2, redoxPotential.getTexts().size());
        assertEquals("value1", redoxPotential.getTexts().get(0).getValue());
        assertEquals("value2", redoxPotential.getTexts().get(1).getValue());
    }

    @Test
    void testCreateTemperatureDependence() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        TemperatureDependence temDependence = new TemperatureDependenceBuilder(texts).build();
        assertEquals(2, temDependence.getTexts().size());
        assertEquals("value1", temDependence.getTexts().get(0).getValue());
        assertEquals("value2", temDependence.getTexts().get(1).getValue());
    }

    @Test
    void testCreateAbsorptionNote() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        assertEquals(2, note.getTexts().size());
        assertEquals("value1", note.getTexts().get(0).getValue());
        assertEquals("value2", note.getTexts().get(1).getValue());
    }

    @Test
    void testCreateKPNote() {

        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        assertEquals(2, note.getTexts().size());
        assertEquals("value1", note.getTexts().get(0).getValue());
        assertEquals("value2", note.getTexts().get(1).getValue());
    }

    @Test
    void testCreateAbsorption1() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        Absorption absorption = createAbsorption(max, false, note, Collections.emptyList());
        assertEquals(max, absorption.getMax());
        assertFalse(absorption.isApproximate());
        assertNotNull(absorption.getNote());
        assertEquals(0, absorption.getEvidences().size());
    }

    @Test
    void testCreateAbsorption2() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        Absorption absorption = createAbsorption(max, true, note, createEvidences());
        assertEquals(max, absorption.getMax());
        assertTrue(absorption.isApproximate());
        assertNotNull(absorption.getNote());
        assertEquals(2, absorption.getEvidences().size());
    }

    @Test
    void testCreateMaximumVelocity() {
        float velocity = 1.02f;
        String unit = "some unit";
        String enzyme = "some enzyme";

        MaximumVelocity maxVelocity =
                createMaximumVelocity(velocity, unit, enzyme, createEvidences());
        assertEquals(velocity, maxVelocity.getVelocity(), Float.MIN_VALUE);
        assertEquals(unit, maxVelocity.getUnit());
        assertEquals(enzyme, maxVelocity.getEnzyme());
        assertEquals(2, maxVelocity.getEvidences().size());
    }

    @Test
    void testCreateMichaelisConstant() {
        float constant = 2.13f;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        MichaelisConstant mconstant =
                createMichaelisConstant(constant, unit, substrate, createEvidences());

        assertEquals(constant, mconstant.getConstant(), Float.MIN_VALUE);
        assertEquals(unit, mconstant.getUnit());
        assertEquals(substrate, mconstant.getSubstrate());
        assertEquals(2, mconstant.getEvidences().size());
    }

    @Test
    void testCreateKineticParametersWithNote() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        List<MichaelisConstant> mConstants = new ArrayList<>();
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        assertEquals(0, kp.getMaximumVelocities().size());
        assertEquals(0, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void testCreateKineticParametersWithVelocityNote() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(0, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void testCreateKineticParametersWithVelocityConstantNote() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1f, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    private MichaelisConstant createMichaelisConstant(
            double v, MichaelisConstantUnit microMol, String sub, List<Evidence> evidences) {
        return new MichaelisConstantBuilder()
                .constant(v)
                .evidencesSet(evidences)
                .unit(microMol)
                .substrate(sub)
                .build();
    }

    private KineticParameters createKineticParameters(
            List<MaximumVelocity> velocities, List<MichaelisConstant> mConstants, Note note) {
        return new KineticParametersBuilder()
                .maximumVelocitiesSet(velocities)
                .michaelisConstantsSet(mConstants)
                .note(note)
                .build();
    }
}
