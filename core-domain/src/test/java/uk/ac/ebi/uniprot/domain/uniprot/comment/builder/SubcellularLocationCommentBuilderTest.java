package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubcellularLocationCommentBuilderTest {

    @Test
    public void testSetMolecule() {
        String molecule ="some mol";
        SubcellularLocationCommentBuilder builder =SubcellularLocationCommentBuilder.newInstance();
        SubcellularLocationComment comment =builder.molecule(molecule)
                .build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals(molecule, comment.getMolecule().get());
        assertFalse( comment.getNote().isPresent());
        assertEquals(0, comment.getSubcellularLocations().size());
    }

    @Test
    public void testSetSubcellularLocations() {
        List<SubcellularLocation> sublocations =new ArrayList<>();
        String molecule ="some mol";
        String locationVal ="some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal ="some orient";
          SubcellularLocation sublocation= SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(locationVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(topologyVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(orientationVal, evidences));
        
        sublocations.add(sublocation);
        SubcellularLocation sublocation2= SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val1", evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val2", evidences),
                null);
        sublocations.add(sublocation2);
        SubcellularLocationCommentBuilder builder =SubcellularLocationCommentBuilder.newInstance();
        SubcellularLocationComment comment =builder.molecule(molecule)
                .subcellularLocations(sublocations)
                .build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertEquals(molecule, comment.getMolecule().get());
        assertFalse(comment.getNote().isPresent());
        assertEquals(2, comment.getSubcellularLocations().size());
        assertEquals(sublocation, comment.getSubcellularLocations().get(0));
        assertEquals(sublocation2, comment.getSubcellularLocations().get(1));
    }

    @Test
    public void testSetNote() {
        List<SubcellularLocation> sublocations =new ArrayList<>();
        String molecule ="";
        String locationVal ="some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal ="some orient";
          SubcellularLocation sublocation= SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(locationVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(topologyVal, evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue(orientationVal, evidences));
        
        sublocations.add(sublocation);
        SubcellularLocation sublocation2= SubcellularLocationCommentBuilder.createSubcellularLocation(
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val1", evidences),
                SubcellularLocationCommentBuilder.createSubcellularLocationValue("val2", evidences),
                null);
        sublocations.add(sublocation2);
        
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        
        SubcellularLocationCommentBuilder builder =SubcellularLocationCommentBuilder.newInstance();
        SubcellularLocationComment comment =builder.molecule(molecule)
                .subcellularLocations(sublocations)
                .note(note)
                .build();
        assertEquals(CommentType.SUBCELLULAR_LOCATION, comment.getCommentType());
        assertFalse( comment.getMolecule().isPresent());
        assertEquals(note, comment.getNote().get());
        assertEquals(2, comment.getSubcellularLocations().size());
        assertEquals(sublocation, comment.getSubcellularLocations().get(0));
        assertEquals(sublocation2, comment.getSubcellularLocations().get(1));
    }

    @Test
    public void testCreateSubcellularLocationValue() {
        String value ="some data";
        List<Evidence> evidences = createEvidences();
        SubcellularLocationValue slv = SubcellularLocationCommentBuilder.createSubcellularLocationValue(value, evidences);
        assertEquals(value, slv.getValue());
        assertEquals(evidences, slv.getEvidences());
    }

    @Test
    public void testCreateSubcellularLocation() {
        String locationVal ="some data";
        List<Evidence> evidences = createEvidences();
        String topologyVal = "some top";
        String orientationVal ="some orient";
        SubcellularLocationValue location = SubcellularLocationCommentBuilder.createSubcellularLocationValue(locationVal, evidences);
        SubcellularLocationValue topology = SubcellularLocationCommentBuilder.createSubcellularLocationValue(topologyVal, evidences);
        SubcellularLocationValue orientation = SubcellularLocationCommentBuilder.createSubcellularLocationValue(orientationVal, evidences);
        SubcellularLocation sublocation= SubcellularLocationCommentBuilder.createSubcellularLocation(location, topology, orientation);
        assertEquals(location, sublocation.getLocation());
        assertEquals(topology, sublocation.getTopology().get());
        assertEquals(orientation, sublocation.getOrientation().get());
        
         sublocation= SubcellularLocationCommentBuilder.createSubcellularLocation(location, topology, null);
        assertEquals(location, sublocation.getLocation());
        assertEquals(topology, sublocation.getTopology().get());
        assertFalse( sublocation.getOrientation().isPresent());
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
