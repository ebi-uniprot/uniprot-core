package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.comment.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.List;

class BPCPConverterTest {

    @Test
    void testSetKineticParameters() {

        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = createNote(texts);
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = createNote(texts);
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment = builder.absorption(absorption).kineticParameters(kp).build();
        BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        BPCPComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testSetPHDependence() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = createNote(texts);
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = createNote(texts);
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .phDependence(createPhDependence(texts))
                        .build();
        BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        BPCPComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testSetRedoxPotential() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = createNote(texts);
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = createNote(texts);
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .phDependence(createPhDependence(texts))
                        .redoxPotential(createRedoxPotential(texts))
                        .build();
        BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        BPCPComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testSetTemperatureDependence() {
        List<MaximumVelocity> velocities = new ArrayList<>();
        velocities.add(createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
        velocities.add(createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
        List<MichaelisConstant> mConstants = new ArrayList<>();
        mConstants.add(
                createMichaelisConstant(
                        2.1, MichaelisConstantUnit.MICRO_MOL, "sub1", createEvidences()));
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = createNote(texts);
        KineticParameters kp = createKineticParameters(velocities, mConstants, note);
        int max = 21;

        Note abNote = createNote(texts);
        Absorption absorption = createAbsorption(max, true, abNote, createEvidences());
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment =
                builder.absorption(absorption)
                        .kineticParameters(kp)
                        .phDependence(createPhDependence(texts))
                        .redoxPotential(createRedoxPotential(texts))
                        .temperatureDependence(createTemperatureDependence(texts))
                        .build();
        BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        BPCPComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testAbsorption() {
        int max = 21;
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = createNote(texts);
        Absorption absorption = createAbsorption(max, true, note, createEvidences());
        BPCPAbsorptionConverter absorptionConverter =
                new BPCPAbsorptionConverter(new EvidenceIndexMapper());

        CommentType.Absorption xmlAbsorption = absorptionConverter.toXml(absorption);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlAbsorption, CommentType.Absorption.class, "absorption"));
        Absorption absorptionConverted = absorptionConverter.fromXml(xmlAbsorption);
        assertEquals(absorption, absorptionConverted);
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment = builder.absorption(absorption).build();
        BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        BPCPComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testAbsorptionMolecule() {
        int max = 21;
        String molecule = "isoform 1";
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        Note note = createNote(texts);
        Absorption absorption = createAbsorption(max, true, note, createEvidences());
        BPCPAbsorptionConverter absorptionConverter =
                new BPCPAbsorptionConverter(new EvidenceIndexMapper());

        CommentType.Absorption xmlAbsorption = absorptionConverter.toXml(absorption);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        xmlAbsorption, CommentType.Absorption.class, "absorption"));
        Absorption absorptionConverted = absorptionConverter.fromXml(xmlAbsorption);
        assertEquals(absorption, absorptionConverted);
        BPCPCommentBuilder builder = new BPCPCommentBuilder();
        BPCPComment comment = builder.absorption(absorption).molecule(molecule).build();
        BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        BPCPComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    private TemperatureDependence createTemperatureDependence(List<EvidencedValue> texts) {
        return new TemperatureDependenceBuilder(texts).build();
    }

    private RedoxPotential createRedoxPotential(List<EvidencedValue> texts) {
        return new RedoxPotentialBuilder(texts).build();
    }

    private Absorption createAbsorption(
            int max, boolean approximate, Note note, List<Evidence> evidences) {
        return new AbsorptionBuilder()
                .approximate(approximate)
                .max(max)
                .note(note)
                .evidencesSet(evidences)
                .build();
    }

    private Note createNote(List<EvidencedValue> texts) {
        return new NoteBuilder(texts).build();
    }

    private MichaelisConstant createMichaelisConstant(
            double constant,
            MichaelisConstantUnit unit,
            String substrate,
            List<Evidence> evidences) {
        return new MichaelisConstantBuilder()
                .constant(constant)
                .unit(unit)
                .substrate(substrate)
                .evidencesSet(evidences)
                .build();
    }

    private MaximumVelocity createMaximumVelocity(
            double velocity, String unit, String enzyme, List<Evidence> evidences) {
        return new MaximumVelocityBuilder()
                .velocity(velocity)
                .unit(unit)
                .enzyme(enzyme)
                .evidencesSet(evidences)
                .build();
    }

    private PhDependence createPhDependence(List<EvidencedValue> texts) {
        return new PhDependenceBuilder(texts).build();
    }

    private KineticParameters createKineticParameters(
            List<MaximumVelocity> velocities, List<MichaelisConstant> mConstants, Note note) {
        return new KineticParametersBuilder()
                .maximumVelocitiesSet(velocities)
                .michaelisConstantsSet(mConstants)
                .note(note)
                .build();
    }
}
