package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.*;

class BPCPCommentImplTest {
    MaximumVelocity mv = new MaximumVelocityImpl(3.4, "unit", "enzyme", createEvidences());
    MichaelisConstant mc =
            new MichaelisConstantImpl(
                    6.7, MichaelisConstantUnit.MICRO_MOL, "substrate", createEvidences());
    BPCPComment impl =
            new BPCPCommentImpl(
                    new AbsorptionImpl(),
                    new KineticParametersImpl(
                            Collections.singletonList(mv), Collections.singletonList(mc), null),
                    new BPCPCommentImpl.PhDependenceImpl(),
                    new BPCPCommentImpl.RedoxPotentialImpl(),
                    new BPCPCommentImpl.TemperatureDependenceImpl());

    @Test
    void testBPCPCommentImpl() {
        Absorption absorption = createAbsorption();
        KineticParameters kineticParameters = createKineticParameters();
        PhDependence phDependence =
                new PhDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        RedoxPotential redoxPotential =
                new RedoxPotentialBuilder(createEvidenceValuesWithoutEvidences()).build();
        TemperatureDependence temperatureDependence =
                new TemperatureDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        BPCPComment comment =
                new BPCPCommentBuilder()
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
        TemperatureDependence temperatureDependence =
                new TemperatureDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        BPCPComment comment =
                new BPCPCommentBuilder()
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
        BPCPComment comment =
                new BPCPCommentBuilder()
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
        PhDependence phDependence =
                new PhDependenceBuilder(createEvidenceValuesWithoutEvidences()).build();
        TemperatureDependence temperatureDependence = null;
        RedoxPotential redoxPotential = null;
        BPCPComment comment =
                new BPCPCommentBuilder()
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
    void needDefaultConstructorForJsonDeserialization() {
        BPCPComment obj = new BPCPCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_RedoxPotential() {
        RedoxPotential obj = new BPCPCommentImpl.RedoxPotentialImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_PhDependence() {
        PhDependence obj = new BPCPCommentImpl.PhDependenceImpl();
        assertNotNull(obj);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization_TemperatureDependence() {
        TemperatureDependence obj = new BPCPCommentImpl.TemperatureDependenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        BPCPComment obj = new BPCPCommentBuilder().from(impl).build();

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void completeObjectWillHaveEveryThing() {
        assertTrue(impl.hasAbsorption());
        assertTrue(impl.hasKineticParameters());
        assertTrue(impl.hasPhDependence());
        assertTrue(impl.hasRedoxPotential());
        assertTrue(impl.hasTemperatureDependence());
    }

    @Test
    void completeObjectToString() {
        // todo change below expected once TRM-23041 is completed
        String expected =
                "CC       Kinetic parameters:\n"
                        + "CC         KM=6.7 uM for substrate;\n"
                        + "CC         Vmax=3.4 unit enzyme;";
        // assertEquals(expected, impl.toString());
        assertTrue(impl.toString().contains(expected));
    }

    @Test
    void defaultToString() {
        String expected = "\nCC   -!- BIOPHYSICOCHEMICAL PROPERTIES:";
        BPCPComment obj = new BPCPCommentImpl();
        assertEquals(expected, obj.toString());
    }
}
