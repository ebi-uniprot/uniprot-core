package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;

public class CatalyticActivityScoredTest extends CommentScoreTestBase {
    @Test
    public void shouldSpScore30() throws Exception {
        String line = "CATALYTIC ACTIVITY:\n"
				+ "Reaction=cytidine(32)/guanosine(34) in tRNA + 2 S-adenosyl-L\n"
				+ "  methionine = 2'-O-methylcytidine(32)/2'-O-methylguanosine(34) in\n"
				+ "  tRNA + 2 H(+) + 2 S-adenosyl-L-homocysteine; Xref=Rhea:RHEA:42396,\n"
				+ "  Rhea:RHEA-COMP:10246, ChEBI:CHEBI:74269, ChEBI:CHEBI:82748,\n"
				+ "  ChEBI:CHEBI:59789, Rhea:RHEA-COMP:10247, ChEBI:CHEBI:74445,\n"
				+ "  ChEBI:CHEBI:74495, ChEBI:CHEBI:15378, ChEBI:CHEBI:57856;\n"
				+ "  EC=2.1.1.205; Evidence={ECO:0000255|HAMAP-Rule:MF_03162};";
        verify(CommentType.CATALYTIC_ACTIVITY, line, 3.0, true);
    }

    @Test
    public void shouldScore30() throws Exception {
        String line =	 "CATALYTIC ACTIVITY:\n"
				+ "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D\n"
				+ "  rhamnose + H(+) + NADPH; EC=1.1.1.n271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "  ECO:0000269|PubMed:10480878};";
        verify(CommentType.CATALYTIC_ACTIVITY, line, 3.0, false);
    }
    @Test
    public void shouldWithEvScore30() throws Exception {
        String line = "CATALYTIC ACTIVITY:\n"
				+ "Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D\n"
				+ "  rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
				+ "  ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
				+ "  EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
				+ "  ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
				+ "  ECO:0000269|PubMed:9473059};\n"
				+ "PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
				+ "  Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n"
				+ "PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898;\n"
				+ "  Evidence={ECO:0000255|HAMAP-Rule:MF_00957};";
        verify(CommentType.CATALYTIC_ACTIVITY, line, 3.0, false);
    }

}
