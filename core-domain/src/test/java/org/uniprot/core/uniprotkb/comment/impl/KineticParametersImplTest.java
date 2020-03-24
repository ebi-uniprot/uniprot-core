package org.uniprot.core.uniprotkb.comment.impl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.createNote;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.*;

class KineticParametersImplTest {
    @Test
    void testMaximumVelocity() {
        double velocity = 1.02;
        String unit = "some unit";
        String enzyme = "some enzyme";

        MaximumVelocity maxVelocity =
                new MaximumVelocityBuilder()
                        .velocity(velocity)
                        .unit(unit)
                        .enzyme(enzyme)
                        .evidencesSet(createEvidences())
                        .build();
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

        MaximumVelocity maxVelocity =
                new MaximumVelocityBuilder().velocity(velocity).unit(unit).enzyme(enzyme).build();
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
        MichaelisConstant mconstant =
                new MichaelisConstantBuilder()
                        .evidencesSet(createEvidences())
                        .constant(constant)
                        .unit(unit)
                        .substrate(substrate)
                        .build();
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
        MichaelisConstant mconstant =
                new MichaelisConstantBuilder()
                        .constant(constant)
                        .unit(unit)
                        .substrate(substrate)
                        .build();
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

        KineticParameters kp =
                new KineticParametersBuilder()
                        .maximumVelocitiesSet(velocities)
                        .michaelisConstantsSet(mConstants)
                        .note(note)
                        .build();
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
        String toString ="KM=2.13 MG_ML_2 for some value;\n" + 
            "Vmax=1.0 unit1 enzyme1;\n" + 
            "Vmax=1.321 unit2 enzyme2;\n" + 
            "note=value 1. value2;";
        assertEquals(toString, kp.toString());
    }

    @Test
    void testKineticParameterImplNoVelocity() {
        List<MaximumVelocity> velocities = null;

        List<MichaelisConstant> mConstants = createConstants();
        Note note = null;

        KineticParameters kp =
                new KineticParametersBuilder()
                        .maximumVelocitiesSet(velocities)
                        .michaelisConstantsSet(mConstants)
                        .note(note)
                        .build();
        assertEquals(0, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void testKineticParameterImpNoNote() {
        List<MaximumVelocity> velocities = createVelocities();

        List<MichaelisConstant> mConstants = createConstants();
        Note note = null;

        KineticParameters kp =
                new KineticParametersBuilder()
                        .maximumVelocitiesSet(velocities)
                        .michaelisConstantsSet(mConstants)
                        .note(note)
                        .build();
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void testKineticParameterImpNoMichaelisConstant() {
        List<MaximumVelocity> velocities = createVelocities();

        List<MichaelisConstant> mConstants = null;
        Note note = createNote();

        KineticParameters kp =
                new KineticParametersBuilder()
                        .maximumVelocitiesSet(velocities)
                        .michaelisConstantsSet(mConstants)
                        .note(note)
                        .build();
        assertEquals(2, kp.getMaximumVelocities().size());
        assertEquals(0, kp.getMichaelisConstants().size());
        assertEquals(note, kp.getNote());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        KineticParameters obj = new KineticParametersImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        KineticParameters impl =
                new KineticParametersImpl(createVelocities(), createConstants(), createNote());
        KineticParameters obj = KineticParametersBuilder.from(impl).build();

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
        assertTrue(obj.hasNote());
    }

    private List<MaximumVelocity> createVelocities() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(
                new MaximumVelocityBuilder()
                        .velocity(1.0)
                        .unit("unit1")
                        .enzyme("enzyme1")
                        .evidencesSet(createEvidences())
                        .build());
        velocities.add(
                new MaximumVelocityBuilder()
                        .velocity(1.321)
                        .unit("unit2")
                        .enzyme("enzyme2")
                        .evidencesSet(createEvidences())
                        .build());
        return velocities;
    }

    private List<MichaelisConstant> createConstants() {
        double constant = 2.13;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        return singletonList(
                new MichaelisConstantBuilder()
                        .constant(constant)
                        .unit(unit)
                        .substrate(substrate)
                        .build());
    }
}
