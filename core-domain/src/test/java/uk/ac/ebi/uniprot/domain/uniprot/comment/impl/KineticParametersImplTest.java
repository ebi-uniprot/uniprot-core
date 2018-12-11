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

import static org.junit.Assert.assertEquals;

class KineticParametersImplTest {

	@Test
	void testMaximumVelocity() {
		float velocity = 1.02f;
		String unit = "some unit";
		String enzyme = "some enzyme";

		MaximumVelocity maxVelocity = new MaximumVelocityImpl(velocity, unit, enzyme, createEvidences());
		assertEquals(velocity, maxVelocity.getVelocity(), Float.MIN_VALUE);
		assertEquals(unit, maxVelocity.getUnit());
		assertEquals(enzyme, maxVelocity.getEnzyme());
		assertEquals(2, maxVelocity.getEvidences().size());
		TestHelper.verifyJson(maxVelocity);
	}

	@Test
	void testMaximumVelocitNoEvidence() {
		float velocity = 1.02f;
		String unit = "some unit";
		String enzyme = "some enzyme";

		MaximumVelocity maxVelocity = new MaximumVelocityImpl(velocity, unit, enzyme, null);
		assertEquals(velocity, maxVelocity.getVelocity(), Float.MIN_VALUE);
		assertEquals(unit, maxVelocity.getUnit());
		assertEquals(enzyme, maxVelocity.getEnzyme());
		assertEquals(0, maxVelocity.getEvidences().size());
		TestHelper.verifyJson(maxVelocity);
	}

	@Test
	void testMichaelisConstantImpl() {
		float constant = 2.13f;
		MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
		String substrate = "some value";
		MichaelisConstant mconstant = new MichaelisConstantImpl(constant, unit, substrate, createEvidences());
		assertEquals(constant, mconstant.getConstant(), Float.MIN_VALUE);
		assertEquals(unit, mconstant.getUnit());
		assertEquals(substrate, mconstant.getSubstrate());
		assertEquals(2, mconstant.getEvidences().size());

		TestHelper.verifyJson(mconstant);
	}

	@Test
	void testMichaelisConstantImplNoEvidence() {
		float constant = 2.13f;
		MichaelisConstantUnit unit = MichaelisConstantUnit.MG_ML_2;
		String substrate = "some value";
		MichaelisConstant mconstant = new MichaelisConstantImpl(constant, unit, substrate, null);
		assertEquals(constant, mconstant.getConstant(), Float.MIN_VALUE);
		assertEquals(unit, mconstant.getUnit());
		assertEquals(substrate, mconstant.getSubstrate());
		assertEquals(0, mconstant.getEvidences().size());

		TestHelper.verifyJson(mconstant);
	}

	@Test
	void testKineticParameterImpl() {
		List<MaximumVelocity> velocities = createVelocities();

		List<MichaelisConstant> mConstants = createConstants();
		Note note = createNote();

		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		assertEquals(2, kp.getMaximumVelocities().size());
		assertEquals(1, kp.getMichaelisConstants().size());
		assertEquals(note, kp.getNote());
		TestHelper.verifyJson(kp);
	}

	@Test
	void testKineticParameterImplNoVelocity() {
		List<MaximumVelocity> velocities = null;

		List<MichaelisConstant> mConstants = createConstants();
		Note note = null;

		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		assertEquals(0, kp.getMaximumVelocities().size());
		assertEquals(1, kp.getMichaelisConstants().size());
		assertEquals(note, kp.getNote());
		TestHelper.verifyJson(kp);
	}

	@Test
	void testKineticParameterImpNoNote() {
		List<MaximumVelocity> velocities = createVelocities();

		List<MichaelisConstant> mConstants = createConstants();
		Note note = null;

		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		assertEquals(2, kp.getMaximumVelocities().size());
		assertEquals(1, kp.getMichaelisConstants().size());
		assertEquals(note, kp.getNote());
		TestHelper.verifyJson(kp);
	}

	@Test
	void testKineticParameterImpNoMichaelisConstant() {
		List<MaximumVelocity> velocities = createVelocities();

		List<MichaelisConstant> mConstants = null;
		Note note = createNote();

		KineticParameters kp = BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
		assertEquals(2, kp.getMaximumVelocities().size());
		assertEquals(0, kp.getMichaelisConstants().size());
		assertEquals(note, kp.getNote());
		TestHelper.verifyJson(kp);
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
}
