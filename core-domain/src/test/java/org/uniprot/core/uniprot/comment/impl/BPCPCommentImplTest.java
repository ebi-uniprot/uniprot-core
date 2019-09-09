package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.*;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;
import static org.uniprot.core.uniprot.comment.impl.ImplTestHelper.createNote;

class BPCPCommentImplTest {
    @Test
    void testBPCPCommentImpl() {
        Absorption absorption = createAbsorption();
        KineticParameters kineticParameters = createKineticParameters();
        PhDependence phDependence = new PhDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        RedoxPotential redoxPotential = new RedoxPotentialBuilder(createEvidenceValuesWithoutEvidences()).build();
        TemperatureDependence temperatureDependence = new TemperatureDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        BPCPComment comment = new BPCPCommentBuilder()
                .absorption(absorption)
                .kineticParameters(kineticParameters)
                .phDependence(phDependence)
                .temperatureDependence(temperatureDependence)
                .redoxPotential(redoxPotential)
                .build();
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
    }

    @Test
    void testBPCPCommentImplOnlyAbsorption() {
        Absorption absorption = createAbsorption();
        KineticParameters kineticParameters = null;
        PhDependence phDependence = null;
        RedoxPotential redoxPotential = null;
        TemperatureDependence temperatureDependence = new TemperatureDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        BPCPComment comment = new BPCPCommentBuilder()
                .absorption(absorption)
                .kineticParameters(kineticParameters)
                .phDependence(phDependence)
                .temperatureDependence(temperatureDependence)
                .redoxPotential(redoxPotential)
                .build();
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
    }

    @Test
    void testBPCPCommentImplOnlyKinetic() {
        Absorption absorption = null;
        KineticParameters kineticParameters = createKineticParameters();
        PhDependence phDependence = null;
        RedoxPotential redoxPotential = null;
        TemperatureDependence temperatureDependence = null;
        BPCPComment comment = new BPCPCommentBuilder()
                .absorption(absorption)
                .kineticParameters(kineticParameters)
                .phDependence(phDependence)
                .temperatureDependence(temperatureDependence)
                .redoxPotential(redoxPotential)
                .build();
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
    }


    @Test
    void testBPCPCommentImplOnlyKPhDependence() {
        Absorption absorption = null;
        KineticParameters kineticParameters = null;
        PhDependence phDependence = new PhDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        TemperatureDependence temperatureDependence = null;
        RedoxPotential redoxPotential = null;
        BPCPComment comment = new BPCPCommentBuilder()
                .absorption(absorption)
                .kineticParameters(kineticParameters)
                .phDependence(phDependence)
                .temperatureDependence(temperatureDependence)
                .redoxPotential(redoxPotential)
                .build();
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
    }

    private Absorption createAbsorption() {
        Note note = createNote();
        List<Evidence> evidences = createEvidences();
        return new AbsorptionBuilder()
                .evidences(evidences)
                .note(note)
                .max(32)
                .build();
    }

    private KineticParameters createKineticParameters() {
        List<MaximumVelocity> velocities = createVelocities();

        List<MichaelisConstant> mConstants = createConstants();
        Note note = createNote();

        return new KineticParametersBuilder().maximumVelocities(velocities).michaelisConstants(mConstants).note(note)
                .build();
    }

    private List<MaximumVelocity> createVelocities() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(new MaximumVelocityBuilder()
                               .velocity(1.0)
                               .unit("unit1")
                               .enzyme("enzyme1")
                               .evidences(createEvidences())
                               .build());
        velocities.add(new MaximumVelocityBuilder()
                               .velocity(1.32)
                               .unit("unit2")
                               .enzyme("enzyme2")
                               .evidences(createEvidences())
                               .build());
        return velocities;
    }

    private List<MichaelisConstant> createConstants() {
        List<MichaelisConstant> mConstants = new ArrayList<>();
        double constant = 2.13;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        MichaelisConstant mconstant = new MichaelisConstantBuilder()
                .constant(constant)
                .unit(unit)
                .substrate(substrate)
                .evidences(createEvidences())
                .build();
        mConstants.add(mconstant);
        return mConstants;
    }
}
