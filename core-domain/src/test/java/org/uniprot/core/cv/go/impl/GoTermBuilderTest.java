package org.uniprot.core.cv.go.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GoTerm;

class GoTermBuilderTest {

    @Test
    void canSetId() {
        String id = "id";
        GoTerm term = new GoTermBuilder().id(id).build();
        assertEquals(id, term.getId());
        assertNull(term.getName());
    }

    @Test
    void canSetName() {
        String name = "name";
        GoTerm term = new GoTermBuilder().name(name).build();
        assertEquals(name, term.getName());
        assertNull(term.getId());
    }

    @Test
    void canFullBuild() {
        String id = "id";
        String name = "name";
        GoTerm term = new GoTermBuilder().id(id).name(name).build();
        assertEquals(id, term.getId());
        assertEquals(name, term.getName());
    }

    @Test
    void canCreateBuilderFromInstance() {
        GoTerm obj = new GoTermBuilder().build();
        GoTermBuilder builder = GoTermBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        GoTerm obj = new GoTermBuilder().build();
        GoTerm obj2 = new GoTermBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
