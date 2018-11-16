package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

class CatalyticActivityCommentImplTest {

	@Test
	void testAll() {
		Reaction reaction = createReaction();
		List<PhysiologicalReaction> phyReactions = createPhyReaction();
		CatalyticActivityComment comment =new CatalyticActivityCommentImpl(reaction, phyReactions);
		assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
		assertEquals(reaction, comment.getReaction());
		assertEquals(phyReactions, comment.getPhysiologicalReactions());
		TestHelper.verifyJson(comment);
	}
	
	@Test
	void testOnlyReaction() {
		Reaction reaction = createReaction();

		CatalyticActivityComment comment =new CatalyticActivityCommentImpl(reaction, Collections.emptyList());
		assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
		assertEquals(reaction, comment.getReaction());
		assertTrue(comment.getPhysiologicalReactions().isEmpty());
		TestHelper.verifyJson(comment);
	}

	private List<PhysiologicalReaction> createPhyReaction(){
		List<PhysiologicalReaction> phyReactions = new ArrayList<>();
		List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
		phyReactions.add(new PhysiologicalReactionImpl(PhysiologicalDirectionType.LEFT_TO_RIGHT,
				new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:123"),
				evidences
				));
		phyReactions.add(new PhysiologicalReactionImpl(PhysiologicalDirectionType.RIGHT_TO_LEFT,
				new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:313"),
				evidences
				));
		return phyReactions;
	}
	private Reaction createReaction() {
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
        return new ReactionImpl( name,  references,  ecNumber,
				evidences);
	}
}
