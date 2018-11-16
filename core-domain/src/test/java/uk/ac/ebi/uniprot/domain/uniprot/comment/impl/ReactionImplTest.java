package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

class ReactionImplTest {

	@Test
	void testFull() {
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        String name ="some reaction";
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:123"));
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:323"));
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.CHEBI, "ChEBI:3243"));
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
		Reaction reaction =new ReactionImpl( name,  references,  ecNumber,
				evidences);
		assertEquals(evidences, reaction.getEvidences());
		assertEquals(name, reaction.getName());
		assertEquals(ecNumber, reaction.getEcNumber());
		assertEquals(references, reaction.getReactionReferences());
		TestHelper.verifyJson(reaction);
	}
	
	@Test
	void testNameAndEvidence() {
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        String name ="some reaction";
//        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
//        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:123"));
//        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:323"));
//        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.CHEBI, "ChEBI:3243"));
//        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
		Reaction reaction =new ReactionImpl( name,  null,  null,
				evidences);
		assertEquals(evidences, reaction.getEvidences());
		assertEquals(name, reaction.getName());
		assertEquals(null, reaction.getEcNumber());
		assertTrue( reaction.getReactionReferences().isEmpty());
		TestHelper.verifyJson(reaction);
	}
	@Test
	void testNameAndEvidenceAndEC() {
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        String name ="some reaction";
//        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
//        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:123"));
//        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:323"));
//        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.CHEBI, "ChEBI:3243"));
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
		Reaction reaction =new ReactionImpl( name,  null,  ecNumber,
				evidences);
		assertEquals(evidences, reaction.getEvidences());
		assertEquals(name, reaction.getName());
		assertEquals(ecNumber, reaction.getEcNumber());
		assertTrue( reaction.getReactionReferences().isEmpty());
		TestHelper.verifyJson(reaction);
	}
	@Test
	void testNameAndEvidenceAndReferences() {
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        String name ="some reaction";
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:123"));
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:323"));
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.CHEBI, "ChEBI:3243"));
    
		Reaction reaction =new ReactionImpl( name,  references,  null,
				evidences);
		assertEquals(evidences, reaction.getEvidences());
		assertEquals(name, reaction.getName());
		assertEquals(null, reaction.getEcNumber());
		assertEquals(references,  reaction.getReactionReferences());
		TestHelper.verifyJson(reaction);
	}
}
