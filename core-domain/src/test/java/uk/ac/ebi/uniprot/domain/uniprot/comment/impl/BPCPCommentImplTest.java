package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BPCPCommentImplTest {

    @Test
    void testCreatePHDependence() {
        PhDependence obj = BPCPCommentImpl.createPHDependence(createEvidenceValues("ph dep 1", "ph dep2"));
        TestHelper.verifyJson(obj);

    }

    @Test
    void testCreateRedoxPotential() {
        RedoxPotential obj = BPCPCommentImpl
                .createRedoxPotential(createEvidenceValues("red potential 1", "red potential 2"));
        TestHelper.verifyJson(obj);

    }

    @Test
    void testCreateTemperatureDependence() {
        TemperatureDependence obj = BPCPCommentImpl
                .createTemperatureDependence(createEvidenceValues("temp 1", "temp2"));
        TestHelper.verifyJson(obj);
    }

    @Test
    void testBPCPCommentImpl() {
        Absorption absorption = createAbsorption();
        KineticParameters kineticParameters = createKineticParameters();
        PhDependence phDependence = BPCPCommentImpl.createPHDependence(createEvidenceValues("ph dep 1", "ph dep2"));
        RedoxPotential redoxPotential = BPCPCommentImpl
                .createRedoxPotential(createEvidenceValues("red potential 1", "red potential 2"));
        TemperatureDependence temperatureDependence = BPCPCommentImpl
                .createTemperatureDependence(createEvidenceValues("temp 1", "temp2"));
        BPCPComment comment =
                new BPCPCommentImpl(absorption, kineticParameters, phDependence,
                                    redoxPotential, temperatureDependence);
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
        TestHelper.verifyJson(comment);
    }

    @Test
    void testBPCPCommentImplOnlyAbsorption() {
        Absorption absorption = createAbsorption();
        KineticParameters kineticParameters = null;
        PhDependence phDependence = null;
        RedoxPotential redoxPotential = null;
        TemperatureDependence temperatureDependence = BPCPCommentImpl
                .createTemperatureDependence(createEvidenceValues("temp 1", "temp2"));
        BPCPComment comment =
                new BPCPCommentImpl(absorption, kineticParameters, phDependence,
                                    redoxPotential, temperatureDependence);
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
        TestHelper.verifyJson(comment);
    }

    @Test
    void testBPCPCommentImplOnlyKinetic() {
        Absorption absorption = null;
        KineticParameters kineticParameters = createKineticParameters();
        PhDependence phDependence = null;
        RedoxPotential redoxPotential = null;
        TemperatureDependence temperatureDependence = null;
        BPCPComment comment =
                new BPCPCommentImpl(absorption, kineticParameters, phDependence,
                                    redoxPotential, temperatureDependence);
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
        TestHelper.verifyJson(comment);
    }


    @Test
    void testBPCPCommentImplOnlyKPhDependence() {
        Absorption absorption = null;
        KineticParameters kineticParameters = null;
        PhDependence phDependence = BPCPCommentImpl.createPHDependence(createEvidenceValues("ph dep 1", "ph dep2"));
        TemperatureDependence temperatureDependence = null;
        ;
        RedoxPotential redoxPotential = null;
        BPCPComment comment =
                new BPCPCommentImpl(absorption, kineticParameters, phDependence,
                                    redoxPotential, temperatureDependence);
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment.getCommentType());
        assertEquals(absorption, comment.getAbsorption());
        assertEquals(kineticParameters, comment.getKineticParameters());
        assertEquals(phDependence, comment.getPhDependence());
        assertEquals(redoxPotential, comment.getRedoxPotential());
        assertEquals(temperatureDependence, comment.getTemperatureDependence());
        TestHelper.verifyJson(comment);
    }


    private Absorption createAbsorption() {
        Note note = createNote();
        List<Evidence> evidences = createEvidences();
        return new AbsorptionImpl(32, note, evidences);
    }

    private KineticParameters createKineticParameters() {
        List<MaximumVelocity> velocities = createVelocities();

        List<MichaelisConstant> mConstants = createConstants();
        Note note = createNote();

        return BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);

    }

    private List<MaximumVelocity> createVelocities() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0f, "unit1", "enzyme1", createEvidences()));
        velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321f, "unit2", "enzyme2", createEvidences()));
        return velocities;
    }

    private List<MichaelisConstant> createConstants() {
        List<MichaelisConstant> mConstants = new ArrayList<>();
        float constant = 2.13f;
        MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
        String substrate = "some value";
        MichaelisConstant mconstant = new MichaelisConstantImpl(constant, unit, substrate, createEvidences());
        mConstants.add(mconstant);
        return mConstants;
    }

    private Note createNote() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", createEvidences()));
        Note note = CommentFactory.INSTANCE.createNote(evidencedValues);
        return note;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));
        return evidences;
    }

    private List<EvidencedValue> createEvidenceValues(String val1, String val2) {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));
        evidences.add(new EvidenceImpl(EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"));

        List<Evidence> evidences2 = new ArrayList<>();
        evidences2.add(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));

        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", evidences));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", evidences2));
        return evidencedValues;
    }


}
