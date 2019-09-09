package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

public class CatalyticActivityScoredTest extends CommentScoreTestBase {
	@Test
	public void shouldSpScore30() throws Exception {
		String line = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=cytidine(32)/guanosine(34) in tRNA + 2 S-adenosyl-L-\n"
				+ "CC         methionine = 2'-O-methylcytidine(32)/2'-O-methylguanosine(34) in\n"
				+ "CC         tRNA + 2 H(+) + 2 S-adenosyl-L-homocysteine; Xref=Rhea:RHEA:42396,\n"
				+ "CC         Rhea:RHEA-COMP:10246, ChEBI:CHEBI:74269, ChEBI:CHEBI:82748,\n"
				+ "CC         ChEBI:CHEBI:59789, Rhea:RHEA-COMP:10247, ChEBI:CHEBI:74445,\n"
				+ "CC         ChEBI:CHEBI:74495, ChEBI:CHEBI:15378, ChEBI:CHEBI:57856;\n"
				+ "CC         EC=2.1.1.205; Evidence={ECO:0000255|HAMAP-Rule:MF_03162};";
		verify(CommentType.CATALYTIC_ACTIVITY, line, 3.0, true);
	}

	@Test
	public void shouldScore30() throws Exception {
		String line =	 "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; EC=1.1.1.n271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878};";
		verify(CommentType.CATALYTIC_ACTIVITY, line, 3.0, false);
	}

	@Test
	public void shouldWithEvScore30() throws Exception {
		String line = "CC   -!- CATALYTIC ACTIVITY:\n"
				+ "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
				+ "CC         rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
				+ "CC         EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
				+ "CC         ECO:0000269|PubMed:9473059};\n"
				+ "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n"
				+ "CC       PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898;\n"
				+ "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00957};";
		verify(CommentType.CATALYTIC_ACTIVITY, line, 3.0, false);
	}
}
