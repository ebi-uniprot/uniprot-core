package org.uniprot.core.flatfile.writer.line.cc;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilder;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

class CCLineWrapperTest extends CCBuildTestAbstr {
    @Test
    void testTCSpace() {
        String text = "Belongs to the sodium:solute symporter (SSF) (TC 2.A.21) family";
        List<String> evs = new ArrayList<>();
        String evi = "ECO:0000256|RuleBase:RU362091";
        evs.add(evi);
        String expected =
                "CC   -!- SIMILARITY: Belongs to the sodium:solute symporter (SSF) (TC 2.A.21)\n"
                        + "CC       family. {ECO:0000256|RuleBase:RU362091}.";
        FreeTextComment comment = buildComment(CommentType.SIMILARITY, text, evs);
        doTest(expected, comment);
    }

    @Test
    void testECSpace() {
        String text = "Belongs to the sodium:solute symporter (SSF) (EC 2.A.21) family";
        List<String> evs = new ArrayList<>();
        String evi = "ECO:0000256|RuleBase:RU362091";
        evs.add(evi);
        String expected =
                "CC   -!- SIMILARITY: Belongs to the sodium:solute symporter (SSF) (EC 2.A.21)\n"
                        + "CC       family. {ECO:0000256|RuleBase:RU362091}.";
        FreeTextComment comment = buildComment(CommentType.SIMILARITY, text, evs);
        doTest(expected, comment);
    }

    @Test
    void testDash() {
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
                "CC   -!- FUNCTION: Non-catalytic subunit of the queuine tRNA-ribosyltransferase\n"
                        + "CC       (TGT) that catalyzes the base-exchange of a guanine (G) residue with\n"
                        + "CC       queuine (Q) at position 34 (anticodon wobble position) in tRNAs with\n"
                        + "CC       GU(N) anticodons (tRNA-Asp, -Asn, -His and -Tyr), resulting in the\n"
                        + "CC       hypermodified nucleoside queuosine (7-(((4,5-cis-dihydroxy-2-\n"
                        + "CC       cyclopenten-1-yl)amino)methyl)-7-deazaguanosine). {ECO:0000256|HAMAP-\n"
                        + "CC       Rule:MF_03043}.";
        doTest(expected, comment);
    }

    @Test
    void testDashSpace1() {
        String text =
                "Responsible, at least in part, for anchoring of the major outer membrane lipoprotein (Lpp, also known as the Braun"
                        + " lipoprotein) to the peptidoglycan via a meso-diaminopimelyl-L-Lys- bond on the terminal residue of Lpp. Can be oxidized"
                        + " in vivo, its reduction depends preferentially on DsbG, although DsbC is able to partially replace DsbG.";
        String evi = "ECO:0000269|PubMed:18456808";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.FUNCTION, text, evs);

        String expected =
                "CC   -!- FUNCTION: Responsible, at least in part, for anchoring of the major\n"
                        + "CC       outer membrane lipoprotein (Lpp, also known as the Braun lipoprotein)\n"
                        + "CC       to the peptidoglycan via a meso-diaminopimelyl-L-Lys- bond on the\n"
                        + "CC       terminal residue of Lpp. Can be oxidized in vivo, its reduction depends\n"
                        + "CC       preferentially on DsbG, although DsbC is able to partially replace\n"
                        + "CC       DsbG. {ECO:0000269|PubMed:18456808}.";

        doTest(expected, comment);
    }

    @Test
    void testMultiDash() {
        String text =
                "Release of protein hormones and neuropeptides from their precursors, "
                        + "generally by hydrolysis of -Lys-Arg-|- bonds.";
        String evi = "ECO:0000269|PubMed:18456808";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);

        String expected =
                "CC   -!- ACTIVITY REGULATION: Release of protein hormones and neuropeptides from\n"
                        + "CC       their precursors, generally by hydrolysis of -Lys-Arg-|- bonds.\n"
                        + "CC       {ECO:0000269|PubMed:18456808}.";
        doTest(expected, comment);
    }

    @Test
    void testMultiDash2() {
        String text =
                "Hydrolysis of -Arg-|-Xaa- and -Lys-|-Xaa- bonds in oligopeptides, even when P1' residue is proline.";
        String evi = "ECO:0000269|PubMed:18456808";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        String expected =
                "CC   -!- ACTIVITY REGULATION: Hydrolysis of -Arg-|-Xaa- and -Lys-|-Xaa- bonds in\n"
                        + "CC       oligopeptides, even when P1' residue is proline.\n"
                        + "CC       {ECO:0000269|PubMed:18456808}.";
        doTest(expected, comment);
    }

    @Test
    void testMultiDash3() {
        String text =
                "Hydrolysis of proteins to small peptides in the presence of ATP and magnesium."
                        + " Alpha-casein is the usual test substrate. In the absence of ATP, only oligopeptides"
                        + " shorter than five residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec; and"
                        + " Leu-Tyr-Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu- and -Tyr-|-Trp bonds also occurs).";
        String expected =
                "CC   -!- ACTIVITY REGULATION: Hydrolysis of proteins to small peptides in the\n"
                        + "CC       presence of ATP and magnesium. Alpha-casein is the usual test\n"
                        + "CC       substrate. In the absence of ATP, only oligopeptides shorter than five\n"
                        + "CC       residues are hydrolyzed (such as succinyl-Leu-Tyr-|-NHMec; and Leu-Tyr-\n"
                        + "CC       Leu-|-Tyr-Trp, in which cleavage of the -Tyr-|-Leu- and -Tyr-|-Trp\n"
                        + "CC       bonds also occurs). {ECO:0000255|HAMAP-Rule:MF_00444}.";

        String evi = "ECO:0000255|HAMAP-Rule:MF_00444";
        List<String> evs = new ArrayList<>();
        evs.add(evi);
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        doTest(expected, comment);
    }

    private FreeTextComment buildComment(CommentType type, String text, List<String> evs) {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(createEvidencedValue(text, evs));
        FreeTextCommentBuilder builder = new FreeTextCommentBuilder();
        builder.commentType(type).textsSet(evidencedValues);
        return builder.build();
    }
}
