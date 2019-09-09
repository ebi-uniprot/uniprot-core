package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.KineticParametersBuilder;
import org.uniprot.core.uniprot.comment.builder.MaximumVelocityBuilder;
import org.uniprot.core.uniprot.comment.builder.MichaelisConstantBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;
import static org.uniprot.core.uniprot.comment.impl.ImplTestHelper.createNote;

class KineticParametersImplTest {
    @Test
    void testMaximumVelocity() {
        double velocity = 1.02;
        String unit = "some unit";
        String enzyme = "some enzyme";

        MaximumVelocity maxVelocity = new MaximumVelocityBuilder()
                .velocity(velocity).unit(unit).enzyme(enzyme).evidences(createEvidences()).build();
        assertEquals(velocity, maxVelocity.getVelocity(), Float.MIN_VALUE);
        assertEquals(unit, maxVelocity.getUnit());
        assertEquals(enzyme, maxVelocity.getEnzyme());
        assertEquals(2, maxVelocity.getEvidences().size());
    }

    @Test
    void testMaximumVelocitNoEvidence() {
        double velocity = 1.02;
        String unit = "some unit";
        String enzyme = "some enzyme";

        MaximumVelocity maxVelocity = new MaximumVelocityBuilder()
                .velocity(velocity).unit(unit).enzyme(enzyme).build();
        assertEquals(velocity, maxVelocity.getVelocity(), Float.MIN_VALUE);
        assertEquals(unit, maxVelocity.getUnit());
        assertEquals(enzyme, maxVelocity.getEnzyme());
        assertEquals(0, maxVelocity.getEvidences().size());
    }

    @Test
    void testMichaelisConstantImpl() {
        double constant = 2.13;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        MichaelisConstant mconstant = new MichaelisConstantBuilder().evidences(createEvidences()).constant(constant).unit(unit).substrate(substrate).build();
        assertEquals(constant, mconstant.getConstant(), Float.MIN_VALUE);
        assertEquals(unit, mconstant.getUnit());
        assertEquals(substrate, mconstant.getSubstrate());
        assertEquals(2, mconstant.getEvidences().size());
    }

    @Test
    void testMichaelisConstantImplNoEvidence() {
        float constant = 2.13f;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        MichaelisConstant mconstant = new MichaelisConstantBuilder().constant(constant).unit(unit).substrate(substrate).build();
        assertEquals(constant, mconstant.getConstant(), Float.MIN_VALUE);
        assertEquals(unit, mconstant.getUnit());
        assertEquals(substrate, mconstant.getSubstrate());
        assertEquals(0, mconstant.getEvidences().size());
    }

    @Test
    void testKineticParameterImpl() {
        List<MaximumVelocity> velocities = createVelocities();

        List<MichaelisConstant> mConstants = createConstants();
        Note note = createNote();

        KineticParameters kp = new KineticParametersBuilder()
                .maximumVelocities(velocities).michaelisConstants(mConstants).note(note).build();
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void testKineticParameterImplNoVelocity() {
        List<MaximumVelocity> velocities = null;

        List<MichaelisConstant> mConstants = createConstants();
        Note note = null;

        KineticParameters kp = new KineticParametersBuilder()
                .maximumVelocities(velocities).michaelisConstants(mConstants).note(note).build();
        assertEquals(0, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void testKineticParameterImpNoNote() {
        List<MaximumVelocity> velocities = createVelocities();

        List<MichaelisConstant> mConstants = createConstants();
        Note note = null;

        KineticParameters kp =new KineticParametersBuilder()
                .maximumVelocities(velocities).michaelisConstants(mConstants).note(note).build();
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void testKineticParameterImpNoMichaelisConstant() {
        List<MaximumVelocity> velocities = createVelocities();

        List<MichaelisConstant> mConstants = null;
        Note note = createNote();

        KineticParameters kp = new KineticParametersBuilder()
                .maximumVelocities(velocities).michaelisConstants(mConstants).note(note).build();
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(0, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    private List<MaximumVelocity> createVelocities() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(new MaximumVelocityBuilder()
                               .velocity(1.0).unit("unit1").enzyme("enzyme1").evidences(createEvidences()).build());
        velocities.add(new MaximumVelocityBuilder()
                               .velocity(1.321).unit("unit2").enzyme("enzyme2").evidences(createEvidences()).build());
        return velocities;
    }

    private List<MichaelisConstant> createConstants() {
        double constant = 2.13;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        return singletonList(new MichaelisConstantBuilder().constant(constant).unit(unit).substrate(substrate).build());
    }
}
