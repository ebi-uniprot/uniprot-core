package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SubcellularLocationCommentBuilderTest {

    @Test
    public void testSetMolecule() {
        String molecule = "some mol";
        SubcellularLocationCommentBuilder builder = SubcellularLocationCommentBuilder.newInstance();
        SubcellularLocationComment comment = builder.molecule(molecule)
                .build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals(molecule, comment.getMolecule());
        assertFalse(comment.getNote() != null);
        assertEquals(0, comment.getSubcellularLocations().size());
    }

    @Test
    public void testSetSubcellularLocations() {
        List<SubcellularLocation> sublocations = new ArrayList<>();
        String molecule = "some mol";
        String locationVal = "some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal = "some orient";
        SubcellularLocation sublocation = SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(locationVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(topologyVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(orientationVal, evidences));

        sublocations.add(sublocation);
        SubcellularLocation sublocation2 = SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val1", evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val2", evidences),
                null);
        sublocations.add(sublocation2);
        SubcellularLocationCommentBuilder builder = SubcellularLocationCommentBuilder.newInstance();
        SubcellularLocationComment comment = builder.molecule(molecule)
                .subcellularLocations(sublocations)
                .build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals(molecule, comment.getMolecule());
        assertFalse(comment.getNote() != null);
        assertEquals(2, comment.getSubcellularLocations().size());
        assertEquals(sublocation, comment.getSubcellularLocations().get(0));
        assertEquals(sublocation2, comment.getSubcellularLocations().get(1));
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetNote() {
        List<SubcellularLocation> sublocations = new ArrayList<>();
        String molecule = "";
        String locationVal = "some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal = "some orient";
        SubcellularLocation sublocation = SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(locationVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(topologyVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(orientationVal, evidences));

        sublocations.add(sublocation);
        SubcellularLocation sublocation2 = SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val1", evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val2", evidences),
                null);
        sublocations.add(sublocation2);

        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());

        SubcellularLocationCommentBuilder builder = SubcellularLocationCommentBuilder.newInstance();
        SubcellularLocationComment comment = builder.molecule(molecule)
                .subcellularLocations(sublocations)
                .note(note)
                .build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals("", comment.getMolecule());
        assertEquals(note, comment.getNote());
        assertEquals(2, comment.getSubcellularLocations().size());
        assertEquals(sublocation, comment.getSubcellularLocations().get(0));
        assertEquals(sublocation2, comment.getSubcellularLocations().get(1));
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testCreateSubcellularLocationValue() {
        String value = "some data";
        List<Evidence> evidences = createEvidences();
        SubcellularLocationValue slv = SubcellularLocationCommentBuilder
                .createSubcellularLocationValue(value, evidences);
        assertEquals(value, slv.getValue());
        assertEquals(evidences, slv.getEvidences());
        TestHelper.verifyJson(slv);
    }

    @Test
    public void testCreateSubcellularLocation() {
        String locationVal = "some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal = "some orient";
        SubcellularLocationValue location = SubcellularLocationCommentBuilder
                .createSubcellularLocationValue(locationVal, evidences);
        SubcellularLocationValue topology = SubcellularLocationCommentBuilder
                .createSubcellularLocationValue(topologyVal, evidences);
        SubcellularLocationValue orientation = SubcellularLocationCommentBuilder
                .createSubcellularLocationValue(orientationVal, evidences);
        SubcellularLocation sublocation = SubcellularLocationCommentBuilder
                .createSubcellularLocation(location, topology, orientation);
        assertEquals(location, sublocation.getLocation());
        assertEquals(topology, sublocation.getTopology());
        assertEquals(orientation, sublocation.getOrientation());
        TestHelper.verifyJson(sublocation);

        sublocation = SubcellularLocationCommentBuilder.createSubcellularLocation(location, topology, null);
        assertEquals(location, sublocation.getLocation());
        assertEquals(topology, sublocation.getTopology());
        assertFalse(sublocation.getOrientation() != null);
        TestHelper.verifyJson(sublocation);


    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
