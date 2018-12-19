package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BPCPCommentBuilderTest {
    @Test
    public void testNewInstance() {
        BPCPCommentBuilder builder1 = BPCPCommentBuilder.newInstance();
        BPCPCommentBuilder builder2 = BPCPCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    public void testSetAbsorption() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, note, createEvidences());
        BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
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
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(BPCPCommentBuilder
                               .createMichaelisConstant(2.1f, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = CommentFactory.INSTANCE.createNote(texts);
        Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
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

    @Test
    public void testSetPHDependence() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(BPCPCommentBuilder
                               .createMichaelisConstant(2.1f, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = CommentFactory.INSTANCE.createNote(texts);
        Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .pHDependence(BPCPCommentBuilder.createPHDependence(texts))
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
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(BPCPCommentBuilder
                               .createMichaelisConstant(2.1f, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = CommentFactory.INSTANCE.createNote(texts);
        Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .pHDependence(BPCPCommentBuilder.createPHDependence(texts))
                        .redoxPotential(BPCPCommentBuilder.createRedoxPotential(texts))
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
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(BPCPCommentBuilder
                               .createMichaelisConstant(2.1f, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = CommentFactory.INSTANCE.createNote(texts);
        Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .pHDependence(BPCPCommentBuilder.createPHDependence(texts))
                        .redoxPotential(BPCPCommentBuilder.createRedoxPotential(texts))
                        .temperatureDependence(BPCPCommentBuilder.createTemperatureDependence(texts))
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
        List<EvidencedValue> texts = createEvidenceValues();
        PhDependence phDependence = BPCPCommentBuilder.createPHDependence(texts);
        assertEquals(2, phDependence.getTexts().size());
        assertEquals("value1", phDependence.getTexts().get(0).getValue());
        assertEquals("value2", phDependence.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateRedoxPotential() {
        List<EvidencedValue> texts = createEvidenceValues();
        RedoxPotential redoxPotential = BPCPCommentBuilder.createRedoxPotential(texts);
        assertEquals(2, redoxPotential.getTexts().size());
        assertEquals("value1", redoxPotential.getTexts().get(0).getValue());
        assertEquals("value2", redoxPotential.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateTemperatureDependence() {
        List<EvidencedValue> texts = createEvidenceValues();
        TemperatureDependence temDependence = BPCPCommentBuilder.createTemperatureDependence(texts);
        assertEquals(2, temDependence.getTexts().size());
        assertEquals("value1", temDependence.getTexts().get(0).getValue());
        assertEquals("value2", temDependence.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateAbsorptionNote() {
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        assertEquals(2, note.getTexts().size());
        assertEquals("value1", note.getTexts().get(0).getValue());
        assertEquals("value2", note.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateKPNote() {

        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        assertEquals(2, note.getTexts().size());
        assertEquals("value1", note.getTexts().get(0).getValue());
        assertEquals("value2", note.getTexts().get(1).getValue());
    }

    @Test
    public void testCreateAbsorption1() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        Absorption absorption = BPCPCommentBuilder.createAbsorption(max, note, Collections.emptyList());
        assertEquals(max, absorption.getMax());
        assertFalse(absorption.isApproximate());
        assertNotNull(absorption.getNote());
        assertEquals(0, absorption.getEvidences().size());
    }

    @Test
    public void testCreateAbsorption2() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, note, createEvidences());
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

        MaximumVelocity maxVelocity = BPCPCommentBuilder
                .createMaximumVelocity(velocity, unit, enzyme, createEvidences());
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
        MichaelisConstant mconstant = BPCPCommentBuilder
                .createMichaelisConstant(constant, unit, substrate, createEvidences());

        assertEquals(constant, mconstant.getConstant(), Float.MIN_VALUE);
        assertEquals(unit, mconstant.getUnit());
        assertEquals(substrate, mconstant.getSubstrate());
        assertEquals(2, mconstant.getEvidences().size());
    }

    @Test
    public void testCreateKineticParametersWithNote() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        List<MichaelisConstant> mConstants = new ArrayList<>();
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
        assertEquals(0, kp.getMaximumVelocities().size());
        assertEquals(0, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    public void testCreateKineticParametersWithVelocityNote() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(0, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    public void testCreateKineticParametersWithVelocityConstantNote() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(BPCPCommentBuilder
                               .createMichaelisConstant(2.1f, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValues();
        Note note = CommentFactory.INSTANCE.createNote(texts);
        KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
