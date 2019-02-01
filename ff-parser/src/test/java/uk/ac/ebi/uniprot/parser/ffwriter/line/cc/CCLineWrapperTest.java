package uk.ac.ebi.uniprot.parser.ffwriter.line.cc;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import java.util.ArrayList;
import java.util.List;


public class CCLineWrapperTest extends CCBuildTestAbstr {
    @Test
    public void testTCSpace() {
        String text = "Belongs to the sodium:solute symporter (SSF) (TC 2.A.21) family";
        List<String> evs = new ArrayList<>();
        String evi = "ECO:0000256|RuleBase:RU362091";
        evs.add(evi);
        String expected =
                "CC   -!- SIMILARITY: Belongs to the sodium:solute symporter (SSF)"
                        + "\nCC       (TC 2.A.21) family. {ECO:0000256|RuleBase:RU362091}.";
        FreeTextComment comment = buildComment(CommentType.SIMILARITY, text, evs);
        doTest(expected, comment);

    }

    @Test
    public void testECSpace() {
        String text = "Belongs to the sodium:solute symporter (SSF) (EC 2.A.21) family";
        List<String> evs = new ArrayList<>();
        String evi = "ECO:0000256|RuleBase:RU362091";
        evs.add(evi);
        String expected =
                "CC   -!- SIMILARITY: Belongs to the sodium:solute symporter (SSF)"
                        + "\nCC       (EC 2.A.21) family. {ECO:0000256|RuleBase:RU362091}.";
        FreeTextComment comment = buildComment(CommentType.SIMILARITY, text, evs);
        doTest(expected, comment);

    }

    
    @Test
    public void testDash() {
        String text =
                "Non-catalytic subunit of the queuine tRNA-ribosyltransferase (TGT) that catalyzes the base-exchange of"
                        + " a guanine (G) residue with queuine (Q) at position 34 (anticodon wobble position) in tRNAs with GU(N)"
                        + " anticodons (tRNA-Asp, -Asn, -His and -Tyr), resulting in the hypermodified nucleoside queuosine"
                        + " (7-(((4,5-cis-dihydroxy-2-cyclopenten-1-yl)amino)methyl)-7-deazaguanosine)";
        String evi = "ECO:0000256|HAMAP-Rule:MF_03043";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.FUNCTION, text, evs);
        String expected =
                "CC   -!- FUNCTION: Non-catalytic subunit of the queuine tRNA-"
                        + "\nCC       ribosyltransferase (TGT) that catalyzes the base-exchange of a"
                        + "\nCC       guanine (G) residue with queuine (Q) at position 34 (anticodon"
                        + "\nCC       wobble position) in tRNAs with GU(N) anticodons (tRNA-Asp, -Asn,"
                        + "\nCC       -His and -Tyr), resulting in the hypermodified nucleoside"
                        + "\nCC       queuosine (7-(((4,5-cis-dihydroxy-2-cyclopenten-1-"
                        + "\nCC       yl)amino)methyl)-7-deazaguanosine). {ECO:0000256|HAMAP-"
                        + "\nCC       Rule:MF_03043}.";
        doTest(expected, comment);
    }

    @Test
    public void testDashSpace1() {
        String text =
                "Responsible, at least in part, for anchoring of the major outer membrane lipoprotein (Lpp, also known as the Braun"
                        + " lipoprotein) to the peptidoglycan via a meso-diaminopimelyl-L-Lys- bond on the terminal residue of Lpp. Can be oxidized"
                        + " in vivo, its reduction depends preferentially on DsbG, although DsbC is able to partially replace DsbG.";
        String evi = "ECO:0000269|PubMed:18456808";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.FUNCTION, text, evs);

        String expected =
                "CC   -!- FUNCTION: Responsible, at least in part, for anchoring of the"
                        + "\nCC       major outer membrane lipoprotein (Lpp, also known as the Braun"
                        + "\nCC       lipoprotein) to the peptidoglycan via a meso-diaminopimelyl-L-"
                        + "\nCC       Lys- bond on the terminal residue of Lpp. Can be oxidized in vivo,"
                        + "\nCC       its reduction depends preferentially on DsbG, although DsbC is"
                        + "\nCC       able to partially replace DsbG. {ECO:0000269|PubMed:18456808}.";

        doTest(expected, comment);
    }

    @Test
    public void testMultiDash() {
        String text = "Release of protein hormones and neuropeptides from their precursors, "
                + "generally by hydrolysis of -Lys-Arg-|- bonds.";
        String evi = "ECO:0000269|PubMed:18456808";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);

        String expected = "CC   -!- ACTIVITY REGULATION: Release of protein hormones and neuropeptides"
                + "\nCC       from their precursors, generally by hydrolysis of -Lys-"
                + "\nCC       Arg-|- bonds. {ECO:0000269|PubMed:18456808}.";
        doTest(expected, comment);
    }

    @Test
    public void testMultiDash2() {
        String text =
                "Hydrolysis of -Arg-|-Xaa- and -Lys-|-Xaa- bonds in oligopeptides, even when P1' residue is proline.";
        String evi = "ECO:0000269|PubMed:18456808";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        String expected = "CC   -!- ACTIVITY REGULATION: Hydrolysis of -Arg-|-Xaa- and -Lys-|-"
                + "\nCC       Xaa- bonds in oligopeptides, even when P1' residue is proline."
                + "\nCC       {ECO:0000269|PubMed:18456808}.";
        doTest(expected, comment);
    }

    @Test
    public void testMultiDash3() {
        String text =
                "Hydrolysis of proteins to small peptides in the presence of ATP and magnesium."
                        + " Alpha-casein is the usual test substrate. In the absence of ATP, only oligopeptides"
                        + " shorter than five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec; and"
                        + " Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu- and -Tyr-|-Trp bonds also occurs).";
        String expected =
                "CC   -!- ACTIVITY REGULATION: Hydrolysis of proteins to small peptides in"
                        + "\nCC       the presence of ATP and magnesium. Alpha-casein is the usual test"
                        + "\nCC       substrate. In the absence of ATP, only oligopeptides shorter than"
                        + "\nCC       five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec;"
                        + "\nCC       and Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-"
                        + "\nCC       Leu- and -Tyr-|-Trp bonds also occurs). {ECO:0000255|HAMAP-"
                        + "\nCC       Rule:MF_00444}.";

        String evi = "ECO:0000255|HAMAP-Rule:MF_00444";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        doTest(expected, comment);
    }

    private FreeTextComment buildComment(CommentType type, String text,  List<String> evs) {
    	List<EvidencedValue> evidencedValues = new ArrayList<>();
		evidencedValues.add(createEvidencedValue(text, evs));
    		FreeTextCommentBuilder builder = new FreeTextCommentBuilder();
    		builder.commentType(type)
    		.texts(evidencedValues);
    		return builder.build();
       
    }
}
