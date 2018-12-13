package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.cv.disease.impl.DiseaseServiceImpl;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;

public class CCCatalyticActivityParserTest {
	private final CcLineConverter converter = new CcLineConverter(new DiseaseServiceImpl(""));
	UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
	@Test
	public void testAllWithoutPD() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=cytidine(32)/guanosine(34) in tRNA + 2 S-adenosyl-L-\n"
				+ "CC         methionine = 2'-O-methylcytidine(32)/2'-O-methylguanosine(34) in\n"
				+ "CC         tRNA + 2 H(+) + 2 S-adenosyl-L-homocysteine; Xref=Rhea:RHEA:42396,\n"
				+ "CC         Rhea:RHEA-COMP:10246, ChEBI:CHEBI:74269, ChEBI:CHEBI:82748,\n"
				+ "CC         ChEBI:CHEBI:59789, Rhea:RHEA-COMP:10247, ChEBI:CHEBI:74445,\n"
				+ "CC         ChEBI:CHEBI:74495, ChEBI:CHEBI:15378, ChEBI:CHEBI:57856;\n"
				+ "CC         EC=2.1.1.205; Evidence={ECO:0000255|HAMAP-Rule:MF_03162};\n";
		// "CC PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n" +
		// "CC Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n";
		CcLineObject obj = parser.parse(ccLine);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertEquals(CcLineObject.CCTopicEnum.CATALYTIC_ACTIVITY, cc.topic);
		assertTrue(cc.object instanceof CcLineObject.CatalyticActivity);
		CcLineObject.CatalyticActivity msObj = (CcLineObject.CatalyticActivity) cc.object;
		CcLineObject.CAReaction reaction = msObj.reaction;
		assertNotNull(reaction);
		String reactionName = "cytidine(32)/guanosine(34) in tRNA + 2 S-adenosyl-L-methionine = 2'-O-methylcytidine(32)/2'-O-methylguanosine(34) in"
				+ " tRNA + 2 H(+) + 2 S-adenosyl-L-homocysteine";
		assertEquals(reactionName, reaction.name);
		String xref = "Rhea:RHEA:42396, Rhea:RHEA-COMP:10246, ChEBI:CHEBI:74269, ChEBI:CHEBI:82748, "
				+ "ChEBI:CHEBI:59789, Rhea:RHEA-COMP:10247, ChEBI:CHEBI:74445, "
				+ "ChEBI:CHEBI:74495, ChEBI:CHEBI:15378, ChEBI:CHEBI:57856";
		assertEquals(xref, reaction.xref);
		String ec = "2.1.1.205";
		assertEquals(ec, reaction.ec);

		List<String> evs = new ArrayList<>();
		evs.add("ECO:0000255|HAMAP-Rule:MF_03162");
		checkEvidences(reaction, obj.getEvidenceInfo(), evs);
		List<CcLineObject.CAPhysioDirection> pds = msObj.physiologicalDirections;
		assertEquals(0, pds.size());

	}

	void checkEvidences(Object obj, EvidenceInfo evInfo, List<String> evs){
		
		 List<String> evidences =evInfo.evidences.get(obj);
		 if((evs ==null)||(evs.size()==0)){
			 assertNull(evidences);
			 return;
		 }
		 assertEquals(evs.size(), evidences.size());
		 Collections.sort(evidences);
		 Collections.sort(evs);
		 for(int i=0; i<evs.size(); i++){
			 assertEquals(evs.get(i), evidences.get(i));
		 }
	}
	
	@Test
	public void testAllWithSinglePD() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
				+ "CC         EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
				+ "CC         ECO:0000269|PubMed:9473059};\n"
				+ "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n";
		CcLineObject obj = parser.parse(ccLine);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertEquals(CcLineObject.CCTopicEnum.CATALYTIC_ACTIVITY, cc.topic);
		assertTrue(cc.object instanceof CcLineObject.CatalyticActivity);
		CcLineObject.CatalyticActivity msObj = (CcLineObject.CatalyticActivity) cc.object;
		CcLineObject.CAReaction reaction = msObj.reaction;
		assertNotNull(reaction);
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";
		assertEquals(reactionName, reaction.name);
		String xref = "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783";
		assertEquals(xref, reaction.xref);
		String ec = "1.1.1.271";
		assertEquals(ec, reaction.ec);

		List<String> evs = new ArrayList<>();
		evs.add("ECO:0000255|HAMAP-Rule:MF_00956");
		evs.add("ECO:0000269|PubMed:10480878");
		evs.add("ECO:0000269|PubMed:11021971");
		evs.add("ECO:0000269|PubMed:9473059");
		checkEvidences(reaction, obj.getEvidenceInfo(), evs);
		List<CcLineObject.CAPhysioDirection> pds = msObj.physiologicalDirections;
		assertEquals(1, pds.size());
		CcLineObject.CAPhysioDirection pd = pds.get(0);
		assertEquals("left-to-right", pd.name);
		assertEquals("Rhea:RHEA:18886", pd.xref);
		List<String> evs2 = new ArrayList<>();
		evs2.add("ECO:0000255|HAMAP-Rule:MF_00956");
		checkEvidences(pd, obj.getEvidenceInfo(), evs2);
	}

	@Test
	public void testParseAllWithSinglePD() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
				+ "CC         EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
				+ "CC         ECO:0000269|PubMed:9473059};\n"
				+ "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n";
		CcLineObject obj = parser.parse(ccLine);
		List<Comment> comments = converter.convert(obj);
		assertEquals(1, comments.size());
		Comment com = comments.get(0);
		assertTrue(com instanceof CatalyticActivityComment);
		CatalyticActivityComment comment = (CatalyticActivityComment) com;
		Reaction reaction = comment.getReaction();
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";

		assertEquals(reactionName, reaction.getName());
		assertEquals("1.1.1.271", reaction.getEcNumber().getValue());
		List<DBCrossReference<ReactionReferenceType>> xrefs = reaction.getReactionReferences();
		assertEquals(5, xrefs.size());
		verify(xrefs.get(0), ReactionReferenceType.RHEA, "RHEA:18885");
		verify(xrefs.get(1), ReactionReferenceType.CHEBI, "CHEBI:57273");
		List<Evidence> eviIds = reaction.getEvidences();
		assertEquals(4, eviIds.size());
		Evidence eviId = eviIds.get(2);
		assertEquals("PubMed", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000269|PubMed:11021971", eviId.getValue());

		List<PhysiologicalReaction> directions = comment.getPhysiologicalReactions();
		assertEquals(1, directions.size());
		PhysiologicalReaction direction = directions.get(0);
		assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, direction.getDirectionType());
		verify(direction.getReactionReference(), ReactionReferenceType.RHEA, "RHEA:18886");

		eviIds = direction.getEvidences();
		assertEquals(1, eviIds.size());
		eviId = eviIds.get(0);
		assertEquals("HAMAP-Rule", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00956", eviId.getValue());

	}

	private void verify(DBCrossReference<ReactionReferenceType> xref, ReactionReferenceType type, String id) {
		assertEquals(type, xref.getDatabaseType());
		assertEquals(id, xref.getId());
	}

	@Test
	public void testAllWithSinglePDNoEC() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
				+ "CC         ECO:0000269|PubMed:9473059};\n"
				+ "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n";
		CcLineObject obj = parser.parse(ccLine);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertEquals(CcLineObject.CCTopicEnum.CATALYTIC_ACTIVITY, cc.topic);
		assertTrue(cc.object instanceof CcLineObject.CatalyticActivity);
		CcLineObject.CatalyticActivity msObj = (CcLineObject.CatalyticActivity) cc.object;
		CcLineObject.CAReaction reaction = msObj.reaction;
		assertNotNull(reaction);
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";
		assertEquals(reactionName, reaction.name);
		String xref = "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783";
		assertEquals(xref, reaction.xref);
		String ec = null;
		assertEquals(ec, reaction.ec);

		List<String> evs = new ArrayList<>();
		evs.add("ECO:0000255|HAMAP-Rule:MF_00956");
		evs.add("ECO:0000269|PubMed:10480878");
		evs.add("ECO:0000269|PubMed:11021971");
		evs.add("ECO:0000269|PubMed:9473059");
		checkEvidences(reaction, obj.getEvidenceInfo(), evs);
		List<CcLineObject.CAPhysioDirection> pds = msObj.physiologicalDirections;
		assertEquals(1, pds.size());
		CcLineObject.CAPhysioDirection pd = pds.get(0);
		assertEquals("left-to-right", pd.name);
		assertEquals("Rhea:RHEA:18886", pd.xref);
		List<String> evs2 = new ArrayList<>();
		evs2.add("ECO:0000255|HAMAP-Rule:MF_00956");
		checkEvidences(pd, obj.getEvidenceInfo(), evs2);
	}

	@Test
	public void testParseAllWithSinglePDNoEC() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57783;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971};\n"
				+ "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n";
		CcLineObject obj = parser.parse(ccLine);
		List<Comment> comments = converter.convert(obj);
		assertEquals(1, comments.size());
		Comment com = comments.get(0);
		assertTrue(com instanceof CatalyticActivityComment);
		CatalyticActivityComment comment = (CatalyticActivityComment) com;
		Reaction reaction = comment.getReaction();
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";

		assertEquals(reactionName, reaction.getName());
		assertEquals(null, reaction.getEcNumber());
		List<DBCrossReference<ReactionReferenceType>> xrefs = reaction.getReactionReferences();
		assertEquals(4, xrefs.size());
		verify(xrefs.get(0), ReactionReferenceType.RHEA, "RHEA:18885");
		verify(xrefs.get(2), ReactionReferenceType.CHEBI, "CHEBI:58349");
		List<Evidence> eviIds = reaction.getEvidences();
		assertEquals(3, eviIds.size());
		Evidence eviId = eviIds.get(1);
		assertEquals("PubMed", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000269|PubMed:10480878", eviId.getValue());

		List<PhysiologicalReaction> directions = comment.getPhysiologicalReactions();
		assertEquals(1, directions.size());
		PhysiologicalReaction direction = directions.get(0);
		assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, direction.getDirectionType());
		verify(direction.getReactionReference(), ReactionReferenceType.RHEA, "RHEA:18886");

		eviIds = direction.getEvidences();
		assertEquals(1, eviIds.size());
		eviId = eviIds.get(0);
		assertEquals("HAMAP-Rule", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00956", eviId.getValue());

	}

	@Test
	public void testAllWithMultiPD() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
				+ "CC         EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
				+ "CC         ECO:0000269|PubMed:9473059};\n"
				+ "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n"
				+ "CC       PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n";
		CcLineObject obj = parser.parse(ccLine);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertEquals(CcLineObject.CCTopicEnum.CATALYTIC_ACTIVITY, cc.topic);
		assertTrue(cc.object instanceof CcLineObject.CatalyticActivity);
		CcLineObject.CatalyticActivity msObj = (CcLineObject.CatalyticActivity) cc.object;
		CcLineObject.CAReaction reaction = msObj.reaction;
		assertNotNull(reaction);
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";
		assertEquals(reactionName, reaction.name);
		String xref = "Rhea:RHEA:18885, ChEBI:CHEBI:57273, ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783";
		assertEquals(xref, reaction.xref);
		String ec = "1.1.1.271";
		assertEquals(ec, reaction.ec);

		List<String> evs = new ArrayList<>();
		evs.add("ECO:0000255|HAMAP-Rule:MF_00956");
		evs.add("ECO:0000269|PubMed:10480878");
		evs.add("ECO:0000269|PubMed:11021971");
		evs.add("ECO:0000269|PubMed:9473059");
		checkEvidences(reaction, obj.getEvidenceInfo(), evs);
		List<CcLineObject.CAPhysioDirection> pds = msObj.physiologicalDirections;
		assertEquals(2, pds.size());
		CcLineObject.CAPhysioDirection pd = pds.get(0);
		assertEquals("left-to-right", pd.name);
		assertEquals("Rhea:RHEA:18886", pd.xref);
		List<String> evs2 = new ArrayList<>();
		evs2.add("ECO:0000255|HAMAP-Rule:MF_00956");
		checkEvidences(pd, obj.getEvidenceInfo(), evs2);

		CcLineObject.CAPhysioDirection pd2 = pds.get(1);
		assertEquals("right-to-left", pd2.name);
		assertEquals("Rhea:RHEA:18898", pd2.xref);
		List<String> evs3 = new ArrayList<>();
		evs3.add("ECO:0000255|HAMAP-Rule:MF_00957");
		checkEvidences(pd2, obj.getEvidenceInfo(), evs3);
	}

	@Test
	public void testParseAllWithMultiPD() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
				+ "CC         EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
				+ "CC         ECO:0000269|PubMed:9473059};\n"
				+ "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n"
				+ "CC       PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n";
		CcLineObject obj = parser.parse(ccLine);
		List<Comment> comments = converter.convert(obj);
		assertEquals(1, comments.size());
		Comment com = comments.get(0);
		assertTrue(com instanceof CatalyticActivityComment);
		CatalyticActivityComment comment = (CatalyticActivityComment) com;
		Reaction reaction = comment.getReaction();
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";

		assertEquals(reactionName, reaction.getName());
		assertEquals("1.1.1.271", reaction.getEcNumber().getValue());
		List<DBCrossReference<ReactionReferenceType>> xrefs = reaction.getReactionReferences();
		assertEquals(5, xrefs.size());
		verify(xrefs.get(0), ReactionReferenceType.RHEA, "RHEA:18885");
		verify(xrefs.get(1), ReactionReferenceType.CHEBI, "CHEBI:57273");
		List<Evidence> eviIds = reaction.getEvidences();
		assertEquals(4, eviIds.size());
		Evidence eviId = eviIds.get(2);
		assertEquals("PubMed", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000269|PubMed:11021971", eviId.getValue());

		List<PhysiologicalReaction> directions = comment.getPhysiologicalReactions();
		assertEquals(2, directions.size());
		PhysiologicalReaction direction = directions.get(0);
		assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, direction.getDirectionType());
		verify(direction.getReactionReference(), ReactionReferenceType.RHEA, "RHEA:18886");

		eviIds = direction.getEvidences();
		assertEquals(1, eviIds.size());
		eviId = eviIds.get(0);
		assertEquals("HAMAP-Rule", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00956", eviId.getValue());

		PhysiologicalReaction direction2 = directions.get(1);
		assertEquals(PhysiologicalDirectionType.RIGHT_TO_LEFT, direction2.getDirectionType());
		verify(direction2.getReactionReference(), ReactionReferenceType.RHEA, "RHEA:18898");

		eviIds = direction2.getEvidences();
		assertEquals(1, eviIds.size());
		eviId = eviIds.get(0);
		assertEquals("HAMAP-Rule", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000255|HAMAP-Rule:MF_00957", eviId.getValue());

	}
	@Test
	public void caBasedOnEnzyme() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878};\n";
		CcLineObject obj = parser.parse(ccLine);
		assertNotNull(obj);
		List<Comment> comments = converter.convert(obj);
		assertEquals(1, comments.size());
		Comment com = comments.get(0);
		assertTrue(com instanceof CatalyticActivityComment);
		CatalyticActivityComment comment = (CatalyticActivityComment) com;
		Reaction reaction = comment.getReaction();
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";

		assertEquals(reactionName, reaction.getName());
		assertEquals("1.1.1.271", reaction.getEcNumber().getValue());
		List<DBCrossReference<ReactionReferenceType>> xrefs = reaction.getReactionReferences();
		assertEquals(0, xrefs.size());
		List<Evidence> eviIds = reaction.getEvidences();
		assertEquals(2, eviIds.size());
		Evidence eviId = eviIds.get(1);
		assertEquals("PubMed", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000269|PubMed:10480878", eviId.getValue());
	}
	
	@Test
	public void caBasedOnEnzymeN() {
		String ccLine = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; EC=1.1.1.n271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878};\n";
		CcLineObject obj = parser.parse(ccLine);
		assertNotNull(obj);
		List<Comment> comments = converter.convert(obj);
		assertEquals(1, comments.size());
		Comment com = comments.get(0);
		assertTrue(com instanceof CatalyticActivityComment);
		CatalyticActivityComment comment = (CatalyticActivityComment) com;
		Reaction reaction = comment.getReaction();
		String reactionName = "GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-rhamnose + H(+) + NADPH";

		assertEquals(reactionName, reaction.getName());
		assertEquals("1.1.1.n271", reaction.getEcNumber().getValue());
		List<DBCrossReference<ReactionReferenceType>> xrefs = reaction.getReactionReferences();
		assertEquals(0, xrefs.size());
		List<Evidence> eviIds = reaction.getEvidences();
		assertEquals(2, eviIds.size());
		Evidence eviId = eviIds.get(1);
		assertEquals("PubMed", eviId.getSource().getDatabaseType().getName());
		assertEquals("ECO:0000269|PubMed:10480878", eviId.getValue());
	}
	
	
	
}
