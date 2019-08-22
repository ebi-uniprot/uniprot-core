package org.uniprot.core.uniprot.comment.builder;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.AbsorptionBuilder;
import org.uniprot.core.uniprot.comment.builder.BPCPCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.KineticParametersBuilder;
import org.uniprot.core.uniprot.comment.builder.MaximumVelocityBuilder;
import org.uniprot.core.uniprot.comment.builder.MichaelisConstantBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.PhDependenceBuilder;
import org.uniprot.core.uniprot.comment.builder.RedoxPotentialBuilder;
import org.uniprot.core.uniprot.comment.builder.TemperatureDependenceBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

public class BPCPCommentBuilderTest {
    @Test
    public void testNewInstance() {
        BPCPCommentBuilder builder1 = new BPCPCommentBuilder();
        BPCPCommentBuilder builder2 = new BPCPCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetAbsorption() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        Absorption absorption = new AbsorptionBuilder()
                .max(max)
                .approximate(true)
                .note(note)
                .evidences(createEvidences()).build();
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment =
                builder.absorption(absorption)
                        .build();

        assertEquals(absorption, comment.getAbsorption());
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertTrue(comment.getKineticParameters() == null);
        assertFalse(comment.getPhDependence() != null);
        assertFalse(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetKineticParameters() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
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
                        .build();
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertNotNull(comment.getKineticParameters());
        assertFalse(comment.getPhDependence() != null);
        assertFalse(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
        TestHelper.verifyJson(comment);
    }

    private Absorption createAbsorption(int max, boolean b, Note note, List<Evidence> evidences) {
        return new AbsorptionBuilder()
                .approximate(b)
                .note(note)
                .evidences(evidences)
                .max(max)
                .build();
    }

    private MaximumVelocity createMaximumVelocity(double v, String unit, String enzyme, List<Evidence> evidences) {
        return new MaximumVelocityBuilder()
                .velocity(v)
                .unit(unit)
                .enzyme(enzyme)
                .evidences(evidences)
                .build();
    }

    @Test
    public void testSetPHDependence() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
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
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetRedoxPotential() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
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
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetTemperatureDependence() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
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
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testCreatePHDependence() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        PhDependence phDependence = new PhDependenceBuilder(texts).build();
        assertEquals(2, phDependence.getTexts().size());
        assertEquals("value1", phDependence.getTexts().get(0).getValue());
        assertEquals("value2", phDependence.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateRedoxPotential() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        RedoxPotential redoxPotential = new RedoxPotentialBuilder(texts).build();
        assertEquals(2, redoxPotential.getTexts().size());
        assertEquals("value1", redoxPotential.getTexts().get(0).getValue());
        assertEquals("value2", redoxPotential.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateTemperatureDependence() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        TemperatureDependence temDependence = new TemperatureDependenceBuilder(texts).build();
        assertEquals(2, temDependence.getTexts().size());
        assertEquals("value1", temDependence.getTexts().get(0).getValue());
        assertEquals("value2", temDependence.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateAbsorptionNote() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        assertEquals(2, note.getTexts().size());
        assertEquals("value1", note.getTexts().get(0).getValue());
        assertEquals("value2", note.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateKPNote() {

        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        assertEquals(2, note.getTexts().size());
        assertEquals("value1", note.getTexts().get(0).getValue());
        assertEquals("value2", note.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateAbsorption1() {
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
    public void testCreateAbsorption2() {
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
    public void testCreateMaximumVelocity() {
        float velocity = 1.02f;
        String unit = "some unit";
        String enzyme = "some enzyme";

        MaximumVelocity maxVelocity = createMaximumVelocity(velocity, unit, enzyme, createEvidences());
        assertEquals(velocity, maxVelocity.getVelocity(), Float.MIN_VALUE);
        assertEquals(unit, maxVelocity.getUnit());
        assertEquals(enzyme, maxVelocity.getEnzyme());
        assertEquals(2, maxVelocity.getEvidences().size());
    }

    @Test
    public void testCreateMichaelisConstant() {
        float constant = 2.13f;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        MichaelisConstant mconstant = createMichaelisConstant(constant, unit, substrate, createEvidences());

        assertEquals(constant, mconstant.getConstant(), Float.MIN_VALUE);
        assertEquals(unit, mconstant.getUnit());
        assertEquals(substrate, mconstant.getSubstrate());
        assertEquals(2, mconstant.getEvidences().size());
    }

    @Test
    public void testCreateKineticParametersWithNote() {
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
    public void testCreateKineticParametersWithVelocityNote() {
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
    public void testCreateKineticParametersWithVelocityConstantNote() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(createMichaelisConstant(2.1f, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = new NoteBuilder(texts).build();
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    private MichaelisConstant createMichaelisConstant(double v, MichaelisConstantUnit microMol, String sub, List<Evidence> evidences) {
        return new MichaelisConstantBuilder()
                .constant(v)
                .evidences(evidences)
                .unit(microMol)
                .substrate(sub)
                .build();
    }

    private KineticParameters createKineticParameters(List<MaximumVelocity> velocities, List<MichaelisConstant> mConstants, Note note) {
        return new KineticParametersBuilder()
                .maximumVelocities(velocities)
                .michaelisConstants(mConstants)
                .note(note)
                .build();
    }
}