package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.KineticParameters;

class KineticParametersBuilderTest {

    @Test
    void canAddSingleMaximumVelocitie() {
        KineticParameters obj =
                new KineticParametersBuilder()
                        .maximumVelocitiesAdd(new MaximumVelocityBuilder().build())
                        .build();
        assertNotNull(obj.getMaximumVelocities());
        assertFalse(obj.getMaximumVelocities().isEmpty());
        assertTrue(obj.hasMaximumVelocities());
    }

    @Test
    void nullMaximumVelocitie_willBeIgnore() {
        KineticParameters obj = new KineticParametersBuilder().maximumVelocitiesAdd(null).build();
        assertNotNull(obj.getMaximumVelocities());
        assertTrue(obj.getMaximumVelocities().isEmpty());
        assertFalse(obj.hasMaximumVelocities());
    }

    @Test
    void canAddSingleMichaelisConstant() {
        KineticParameters obj =
                new KineticParametersBuilder()
                        .michaelisConstantsAdd(new MichaelisConstantBuilder().build())
                        .build();
        assertNotNull(obj.getMichaelisConstants());
        assertFalse(obj.getMichaelisConstants().isEmpty());
        assertTrue(obj.hasMichaelisConstants());
    }

    @Test
    void nullMichaelisConstant_willBeIgnore() {
        KineticParameters obj = new KineticParametersBuilder().michaelisConstantsAdd(null).build();
        assertNotNull(obj.getMichaelisConstants());
        assertTrue(obj.getMichaelisConstants().isEmpty());
        assertFalse(obj.hasMichaelisConstants());
    }

    @Test
    void canCreateBuilderFromInstance() {
        KineticParameters obj = new KineticParametersBuilder().build();
        KineticParametersBuilder builder = KineticParametersBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        KineticParameters obj = new KineticParametersBuilder().build();
        KineticParameters obj2 = new KineticParametersBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
