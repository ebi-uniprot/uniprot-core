package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

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
                                new SubcellularLocationValueBuilder(
                                                locationId, locationVal, evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder(topId, topologyVal, evidences)
                                        .build())
                        .orientation(
                                new SubcellularLocationValueBuilder(
                                                orientId, orientationVal, evidences)
                                        .build())
                        .build();
        sublocations.add(sublocation);
        SubcellularLocation sublocation2 =
                new SubcellularLocationBuilder()
                        .location(
                                new SubcellularLocationValueBuilder("id1", "val1", evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder("id2", "val2", evidences)
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
                                new SubcellularLocationValueBuilder("id1", locationVal, evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder("id2", topologyVal, evidences)
                                        .build())
                        .orientation(
                                new SubcellularLocationValueBuilder(
                                                "id3", orientationVal, evidences)
                                        .build())
                        .build();

        sublocations.add(sublocation);
        SubcellularLocation sublocation2 =
                new SubcellularLocationBuilder()
                        .location(
                                new SubcellularLocationValueBuilder("id4", "val1", evidences)
                                        .build())
                        .topology(
                                new SubcellularLocationValueBuilder("id5", "val2", evidences)
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
                new SubcellularLocationValueBuilder("id1", value, evidences).build();
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
                new SubcellularLocationValueBuilder("id1", locationVal, evidences).build();
        SubcellularLocationValue topology =
                new SubcellularLocationValueBuilder("id2", topologyVal, evidences).build();
        SubcellularLocationValue orientation =
                new SubcellularLocationValueBuilder("id3", orientationVal, evidences).build();
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
}
