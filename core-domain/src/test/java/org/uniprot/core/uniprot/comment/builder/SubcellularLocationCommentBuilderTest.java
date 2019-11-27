package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.evidence.Evidence;

class SubcellularLocationCommentBuilderTest {

    @Test
    void testSetMolecule() {
        String molecule = "some mol";
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        SubcellularLocationComment comment = builder.molecule(molecule).build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals(molecule, comment.getMolecule());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getSubcellularLocations().size());
    }

    @Test
    void testSetSubcellularLocations() {
        List<SubcellularLocation> sublocations = new ArrayList<>();
        String molecule = "some mol";
        String locationId = "someLocId";
        String locationVal = "some data";
        List<Evidence> evidences = createEvidences();
        String topId = "topId";
        String topologyVal = "some top";
        String orientId = "orientId";
        String orientationVal = "some orient";
        SubcellularLocation sublocation =
                new SubcellularLocationBuilder()
                        .location(
                                new SubcellularLocationValueBuilder()
                                        .id(locationId)
                                        .value(locationVal)
                                        .evidences(evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder()
                                        .id(topId)
                                        .value(topologyVal)
                                        .evidences(evidences)
                                        .build())
                        .orientation(
                                new SubcellularLocationValueBuilder()
                                        .id(orientId)
                                        .value(orientationVal)
                                        .evidences(evidences)
                                        .build())
                        .build();
        sublocations.add(sublocation);
        SubcellularLocation sublocation2 =
                new SubcellularLocationBuilder()
                        .location(
                                new SubcellularLocationValueBuilder()
                                        .id("id1")
                                        .value("val1")
                                        .evidences(evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder()
                                        .id("id2")
                                        .value("val2")
                                        .evidences(evidences)
                                        .build())
                        .build();
        sublocations.add(sublocation2);
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        SubcellularLocationComment comment =
                builder.molecule(molecule).subcellularLocations(sublocations).build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals(molecule, comment.getMolecule());
        assertFalse(comment.getNote() != null);
        assertEquals(2, comment.getSubcellularLocations().size());
        assertEquals(sublocation, comment.getSubcellularLocations().get(0));
        assertEquals(sublocation2, comment.getSubcellularLocations().get(1));
    }

    @Test
    void testSetNote() {
        List<SubcellularLocation> sublocations = new ArrayList<>();
        String molecule = "";
        String locationVal = "some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal = "some orient";
        SubcellularLocation sublocation =
                new SubcellularLocationBuilder()
                        .location(
                                new SubcellularLocationValueBuilder()
                                        .id("id1")
                                        .value(locationVal)
                                        .evidences(evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder()
                                        .id("id2")
                                        .value(topologyVal)
                                        .evidences(evidences)
                                        .build())
                        .orientation(
                                new SubcellularLocationValueBuilder()
                                        .id("id3")
                                        .value(orientationVal)
                                        .evidences(evidences)
                                        .build())
                        .build();

        sublocations.add(sublocation);
        SubcellularLocation sublocation2 =
                new SubcellularLocationBuilder()
                        .location(
                                new SubcellularLocationValueBuilder()
                                        .id("id4")
                                        .value("val1")
                                        .evidences(evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder()
                                        .id("id5")
                                        .value("val2")
                                        .evidences(evidences)
                                        .build())
                        .build();
        sublocations.add(sublocation2);

        Note note = new NoteBuilder(createEvidenceValuesWithoutEvidences()).build();

        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        SubcellularLocationComment comment =
                builder.molecule(molecule).subcellularLocations(sublocations).note(note).build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals("", comment.getMolecule());
        assertEquals(note, comment.getNote());
        assertEquals(2, comment.getSubcellularLocations().size());
        assertEquals(sublocation, comment.getSubcellularLocations().get(0));
        assertEquals(sublocation2, comment.getSubcellularLocations().get(1));
    }

    @Test
    void testCreateSubcellularLocationValue() {
        String value = "some data";
        List<Evidence> evidences = createEvidences();
        SubcellularLocationValue slv =
                new SubcellularLocationValueBuilder()
                        .id("id1")
                        .value(value)
                        .evidences(evidences)
                        .build();
        assertEquals(value, slv.getValue());
        assertEquals(evidences, slv.getEvidences());
    }

    @Test
    void testCreateSubcellularLocation() {
        String locationVal = "some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal = "some orient";
        SubcellularLocationValue location =
                new SubcellularLocationValueBuilder()
                        .id("id1")
                        .value(locationVal)
                        .evidences(evidences)
                        .build();
        SubcellularLocationValue topology =
                new SubcellularLocationValueBuilder()
                        .id("id2")
                        .value(topologyVal)
                        .evidences(evidences)
                        .build();
        SubcellularLocationValue orientation =
                new SubcellularLocationValueBuilder()
                        .id("id3")
                        .value(orientationVal)
                        .evidences(evidences)
                        .build();
        SubcellularLocation sublocation =
                new SubcellularLocationBuilder()
                        .location(location)
                        .topology(topology)
                        .orientation(orientation)
                        .build();
        assertEquals(location, sublocation.getLocation());
        assertEquals(topology, sublocation.getTopology());
        assertEquals(orientation, sublocation.getOrientation());

        sublocation =
                new SubcellularLocationBuilder().location(location).topology(topology).build();
        assertEquals(location, sublocation.getLocation());
        assertEquals(topology, sublocation.getTopology());
        assertFalse(sublocation.getOrientation() != null);
    }

    @Test
    void canCreateBuilderFromInstance() {
        SubcellularLocationComment obj = new SubcellularLocationCommentBuilder().build();
        SubcellularLocationCommentBuilder builder =
                new SubcellularLocationCommentBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        SubcellularLocationComment obj = new SubcellularLocationCommentBuilder().build();
        SubcellularLocationComment obj2 = new SubcellularLocationCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canAddSingleSubcellularLocation() {
        SubcellularLocationComment obj =
                new SubcellularLocationCommentBuilder()
                        .addSubcellularLocation(new SubcellularLocationBuilder().build())
                        .build();
        assertNotNull(obj.getSubcellularLocations());
        assertFalse(obj.getSubcellularLocations().isEmpty());
        assertTrue(obj.hasSubcellularLocations());
    }

    @Test
    void nullSubcellularLocation_willBeIgnore() {
        SubcellularLocationComment obj =
                new SubcellularLocationCommentBuilder().addSubcellularLocation(null).build();
        assertNotNull(obj.getSubcellularLocations());
        assertTrue(obj.getSubcellularLocations().isEmpty());
        assertFalse(obj.hasSubcellularLocations());
    }
}
