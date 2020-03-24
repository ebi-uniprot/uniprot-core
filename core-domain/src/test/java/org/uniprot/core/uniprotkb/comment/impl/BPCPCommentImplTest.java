package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.*;

class BPCPCommentImplTest {
    MaximumVelocity mv = new MaximumVelocityImpl(3.4, "unit", "enzyme", createEvidences());
    MichaelisConstant mc =
            new MichaelisConstantImpl(
                    6.7, MichaelisConstantUnit.MICRO_MOL, "substrate", createEvidences());
    BPCPComment impl =
            new BPCPCommentImpl(
                    "",
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
        String toString ="BIOPHYSICOCHEMICAL PROPERTIES:\n" + 
            "Absorption:\n" + 
            "Abs(max)=32 nm;note=value 1. value2;\n" + 
            "Kinetic parameters:\n" + 
            "KM=2.13 MG_ML_2 for some value;\n" + 
            "Vmax=1.0 unit1 enzyme1;\n" + 
            "Vmax=1.32 unit2 enzyme2;\n" + 
            "note=value 1. value2;\n" + 
            "pH dependence:\n" + 
            "value1. value2;\n" + 
            "Redox potential:\n" + 
            "value1. value2;\n" + 
            "Temperature dependence:\n" + 
            "value1. value2;";
        assertEquals(toString, comment.toString());
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
        BPCPComment obj = BPCPCommentBuilder.from(impl).build();

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

   
}
