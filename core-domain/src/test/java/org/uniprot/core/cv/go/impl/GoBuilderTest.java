package org.uniprot.core.cv.go.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.Go;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.GoEvidenceType;

/**
 * @author jluo
 * @date: 8 Apr 2021
 */
class GoBuilderTest {
    @Test
    void canSetId() {
        String id = "id";
        Go go = new GoBuilder().id(id).build();
        assertEquals(id, go.getId());
        assertNull(go.getName());
        assertNull(go.getAspect());
        assertNull(go.getGoEvidenceType());
        assertNull(go.getGoEvidenceSource());
    }

    @Test
    void canSetName() {
        String name = "some";
        Go go = new GoBuilder().name(name).build();
        assertEquals(name, go.getName());
        assertNull(go.getId());
        assertNull(go.getAspect());
        assertNull(go.getGoEvidenceType());
        assertNull(go.getGoEvidenceSource());
    }

    @Test
    void canSetAspect() {
        GoAspect aspect = GoAspect.COMPONENT;
        Go go = new GoBuilder().aspect(aspect).build();
        assertEquals(aspect, go.getAspect());
        assertNull(go.getId());
        assertNull(go.getName());
        assertNull(go.getGoEvidenceType());
        assertNull(go.getGoEvidenceSource());
    }

    @Test
    void canSetGoEvidenceType() {
        GoEvidenceType goEvidenceType = GoEvidenceType.HGI;
        Go go = new GoBuilder().goEvidenceType(goEvidenceType).build();
        assertEquals(goEvidenceType, go.getGoEvidenceType());
        assertNull(go.getId());
        assertNull(go.getName());
        assertNull(go.getAspect());
        assertNull(go.getGoEvidenceSource());
    }

    @Test
    void canSetGoEvidenceSource() {

        String goEvidenceSource = "UniProt KB";
        Go go = new GoBuilder().goEvidenceSource(goEvidenceSource).build();
        assertEquals(goEvidenceSource, go.getGoEvidenceSource());
        assertNull(go.getId());
        assertNull(go.getName());
        assertNull(go.getAspect());
        assertNull(go.getGoEvidenceType());
    }

    @Test
    void canFullBuild() {
        String id = "id";
        String name = "some";
        GoAspect aspect = GoAspect.PROCESS;
        GoEvidenceType goEvidenceType = GoEvidenceType.EXP;
        String goEvidenceSource = "UniProt KB";
        Go go =
                new GoBuilder()
                        .id(id)
                        .name(name)
                        .aspect(aspect)
                        .goEvidenceType(goEvidenceType)
                        .goEvidenceSource(goEvidenceSource)
                        .build();

        assertEquals(id, go.getId());
        assertEquals(name, go.getName());
        assertEquals(aspect, go.getAspect());
        assertEquals(goEvidenceType, go.getGoEvidenceType());
        assertEquals(goEvidenceSource, go.getGoEvidenceSource());
    }

    @Test
    void canBuildFromInstance() {
        String id = "id";
        String name = "some";
        GoAspect aspect = GoAspect.PROCESS;
        GoEvidenceType goEvidenceType = GoEvidenceType.EXP;
        String goEvidenceSource = "UniProt KB";
        Go go =
                new GoBuilder()
                        .id(id)
                        .name(name)
                        .aspect(aspect)
                        .goEvidenceType(goEvidenceType)
                        .goEvidenceSource(goEvidenceSource)
                        .build();
        GoBuilder builder = GoBuilder.from(go);
        assertNotNull(builder);
        assertEquals(go, builder.build());
    }
}
