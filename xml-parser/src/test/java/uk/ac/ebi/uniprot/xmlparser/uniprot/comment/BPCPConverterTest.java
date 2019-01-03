package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.BPCPComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class BPCPConverterTest {

	@Test
	void testAbsorption() {
		int max = 21;
		List<EvidencedValue> texts = createEvidenceValues();
		Note note = CommentFactory.INSTANCE.createNote(texts);
		Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, note, createEvidences());
		BPCPAbsorptionConverter absorptionConverter = new BPCPAbsorptionConverter(new EvidenceIndexMapper());

		CommentType.Absorption xmlAbsorption = absorptionConverter.toXml(absorption);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlAbsorption, CommentType.Absorption.class, "absorption"));
		Absorption absorptionConverted = absorptionConverter.fromXml(xmlAbsorption);
		assertEquals(absorption, absorptionConverted);
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		BPCPComment comment = builder.absorption(absorption).build();
		BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		BPCPComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

	@Test
	public void testSetKineticParameters() {

		List<MaximumVelocity> velocities = new ArrayList<>();
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
		List<MichaelisConstant> mConstants = new ArrayList<>();
		mConstants.add(BPCPCommentBuilder.createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1",
				createEvidences()));
		List<EvidencedValue> texts = createEvidenceValues();
		Note note = CommentFactory.INSTANCE.createNote(texts);
		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		int max = 21;

		Note abNote = CommentFactory.INSTANCE.createNote(texts);
		Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		BPCPComment comment = builder.absorption(absorption).kineticParameters(kp).build();
		BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		BPCPComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

	@Test
	public void testSetPHDependence() {
		List<MaximumVelocity> velocities = new ArrayList<>();
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
		List<MichaelisConstant> mConstants = new ArrayList<>();
		mConstants.add(BPCPCommentBuilder.createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1",
				createEvidences()));
		List<EvidencedValue> texts = createEvidenceValues();
		Note note = CommentFactory.INSTANCE.createNote(texts);
		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		int max = 21;

		Note abNote = CommentFactory.INSTANCE.createNote(texts);
		Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		BPCPComment comment = builder.absorption(absorption).kineticParameters(kp)
				.pHDependence(BPCPCommentBuilder.createPHDependence(texts)).build();
		BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		BPCPComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

	@Test
	public void testSetRedoxPotential() {
		List<MaximumVelocity> velocities = new ArrayList<>();
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
		List<MichaelisConstant> mConstants = new ArrayList<>();
		mConstants.add(BPCPCommentBuilder.createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1",
				createEvidences()));
		List<EvidencedValue> texts = createEvidenceValues();
		Note note = CommentFactory.INSTANCE.createNote(texts);
		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		int max = 21;

		Note abNote = CommentFactory.INSTANCE.createNote(texts);
		Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		BPCPComment comment = builder.absorption(absorption).kineticParameters(kp)
				.pHDependence(BPCPCommentBuilder.createPHDependence(texts))
				.redoxPotential(BPCPCommentBuilder.createRedoxPotential(texts)).build();
		BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		BPCPComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

	@Test
	public void testSetTemperatureDependence() {
		List<MaximumVelocity> velocities = new ArrayList<>();
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.0, "unit1", "enzyme1", createEvidences()));
		velocities.add(BPCPCommentBuilder.createMaximumVelocity(1.321, "unit2", "enzyme2", createEvidences()));
		List<MichaelisConstant> mConstants = new ArrayList<>();
		mConstants.add(BPCPCommentBuilder.createMichaelisConstant(2.1, MichaelisConstantUnit.MICRO_MOL, "sub1",
				createEvidences()));
		List<EvidencedValue> texts = createEvidenceValues();
		Note note = CommentFactory.INSTANCE.createNote(texts);
		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		int max = 21;

		Note abNote = CommentFactory.INSTANCE.createNote(texts);
		Absorption absorption = BPCPCommentBuilder.createAbsorption(max, true, abNote, createEvidences());
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		BPCPComment comment = builder.absorption(absorption).kineticParameters(kp)
				.pHDependence(BPCPCommentBuilder.createPHDependence(texts))
				.redoxPotential(BPCPCommentBuilder.createRedoxPotential(texts))
				.temperatureDependence(BPCPCommentBuilder.createTemperatureDependence(texts)).build();
		BPCPCommentConverter converter = new BPCPCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		BPCPComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
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
