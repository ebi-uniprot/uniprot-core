package org.uniprot.core.flatfile.writer.line.cc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilder;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

class CCFreeTextBuildTest extends CCBuildTestAbstr {
    @Test
    void testPTMWithEvidence4() {
        String ccLine =
                ("CC   -!- PTM: Proteolytically processed under normal cellular conditions.\n"
                        + "CC       Cleavage either by alpha-secretase, beta-secretase or"
                        + " theta-secretase\n"
                        + "CC       leads to generation and extracellular release of soluble APP"
                        + " peptides,\n"
                        + "CC       S-APP-alpha and S-APP-beta, and the retention of corresponding\n"
                        + "CC       membrane-anchored C-terminal fragments, C80, C83 and C99."
                        + " Subsequent\n"
                        + "CC       processing of C80 and C83 by gamma-secretase yields P3 peptides."
                        + " This\n"
                        + "CC       is the major secretory pathway and is non-amyloidogenic."
                        + " Alternatively,\n"
                        + "CC       presenilin/nicastrin-mediated gamma-secretase processing of C99\n"
                        + "CC       releases the amyloid beta proteins, amyloid-beta 40 (Abeta40)"
                        + " and\n"
                        + "CC       amyloid-beta 42 (Abeta42), major components of amyloid plaques,"
                        + " and the\n"
                        + "CC       cytotoxic C-terminal fragments, gamma-CTF(50), gamma-CTF(57) and"
                        + " gamma-\n"
                        + "CC       CTF(59). {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text1 =
                "Proteolytically processed under normal cellular conditions. "
                        + "Cleavage either by alpha-secretase, beta-secretase or theta-"
                        + "secretase leads to generation and extracellular release of soluble "
                        + "APP peptides, S-APP-alpha and S-APP-beta, and the retention of "
                        + "corresponding membrane-anchored C-terminal fragments, C80, C83 and "
                        + "C99";

        String text2 =
                "Subsequent processing of C80 and C83 by gamma-secretase " + "yields P3 peptides";
        String text3 = "This is the major secretory pathway and is " + "non-amyloidogenic";
        String text4 =
                "Alternatively, presenilin/nicastrin-mediated "
                        + "gamma-secretase processing of C99 releases the amyloid beta "
                        + "proteins, amyloid-beta 40 (Abeta40) and amyloid-beta 42 (Abeta42), "
                        + "major components of amyloid plaques, and the cytotoxic C-terminal "
                        + "fragments, gamma-CTF(50), gamma-CTF(57) and gamma-CTF(59)";
        List<String> evs1 = new ArrayList<>();
        List<String> evs2 = new ArrayList<>();
        List<String> evs3 = new ArrayList<>();
        List<String> evs4 = new ArrayList<>();
        evs4.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs4.add("ECO:0000313|Ensembl:ENSP00000409133");
        List<Map.Entry<String, List<String>>> evidencedTexts = new ArrayList<>();
        Map.Entry<String, List<String>> entry1 = new AbstractMap.SimpleEntry<>(text1, evs1);
        Map.Entry<String, List<String>> entry2 = new AbstractMap.SimpleEntry<>(text2, evs2);
        Map.Entry<String, List<String>> entry3 = new AbstractMap.SimpleEntry<>(text3, evs3);
        Map.Entry<String, List<String>> entry4 = new AbstractMap.SimpleEntry<>(text4, evs4);
        evidencedTexts.add(entry1);
        evidencedTexts.add(entry2);
        evidencedTexts.add(entry3);
        evidencedTexts.add(entry4);
        FreeTextComment comment = buildComment(CommentType.PTM, evidencedTexts);
        doTest(ccLine, comment);
    }

    @Test
    void testPTMWithEvidence2() {
        String ccLine =
                ("CC   -!- PTM: Proteolytically processed under normal cellular conditions.\n"
                        + "CC       Cleavage either by alpha-secretase, beta-secretase or"
                        + " theta-secretase\n"
                        + "CC       leads to generation and extracellular release of soluble APP"
                        + " peptides,\n"
                        + "CC       S-APP-alpha and S-APP-beta, and the retention of corresponding\n"
                        + "CC       membrane-anchored C-terminal fragments, C80, C83 and C99."
                        + " Subsequent\n"
                        + "CC       processing of C80 and C83 by gamma-secretase yields P3"
                        + " peptides.\n"
                        + "CC       {ECO:0000313|Ensembl:ENSP00000409133}. This is the major"
                        + " secretory\n"
                        + "CC       pathway and is non-amyloidogenic."
                        + " {ECO:0000256|HAMAP-Rule:MF_00205}.\n"
                        + "CC       Alternatively, presenilin/nicastrin-mediated gamma-secretase"
                        + " processing\n"
                        + "CC       of C99 releases the amyloid beta proteins, amyloid-beta 40"
                        + " (Abeta40)\n"
                        + "CC       and amyloid-beta 42 (Abeta42), major components of amyloid"
                        + " plaques, and\n"
                        + "CC       the cytotoxic C-terminal fragments, gamma-CTF(50), gamma-CTF(57)"
                        + " and\n"
                        + "CC       gamma-CTF(59). {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text1 =
                "Proteolytically processed under normal cellular conditions. "
                        + "Cleavage either by alpha-secretase, beta-secretase or theta-"
                        + "secretase leads to generation and extracellular release of soluble "
                        + "APP peptides, S-APP-alpha and S-APP-beta, and the retention of "
                        + "corresponding membrane-anchored C-terminal fragments, C80, C83 and "
                        + "C99";

        String text2 =
                "Subsequent processing of C80 and C83 by gamma-secretase " + "yields P3 peptides";
        String text3 = "This is the major secretory pathway and is " + "non-amyloidogenic";
        String text4 =
                "Alternatively, presenilin/nicastrin-mediated "
                        + "gamma-secretase processing of C99 releases the amyloid beta "
                        + "proteins, amyloid-beta 40 (Abeta40) and amyloid-beta 42 (Abeta42), "
                        + "major components of amyloid plaques, and the cytotoxic C-terminal "
                        + "fragments, gamma-CTF(50), gamma-CTF(57) and gamma-CTF(59)";
        List<String> evs1 = new ArrayList<>();
        List<String> evs2 = new ArrayList<>();
        evs2.add("ECO:0000313|Ensembl:ENSP00000409133");
        List<String> evs3 = new ArrayList<>();
        evs3.add("ECO:0000256|HAMAP-Rule:MF_00205");
        List<String> evs4 = new ArrayList<>();
        evs4.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs4.add("ECO:0000313|Ensembl:ENSP00000409133");
        List<Map.Entry<String, List<String>>> evidencedTexts = new ArrayList<>();
        Map.Entry<String, List<String>> entry1 = new AbstractMap.SimpleEntry<>(text1, evs1);
        Map.Entry<String, List<String>> entry2 = new AbstractMap.SimpleEntry<>(text2, evs2);
        Map.Entry<String, List<String>> entry3 = new AbstractMap.SimpleEntry<>(text3, evs3);
        Map.Entry<String, List<String>> entry4 = new AbstractMap.SimpleEntry<>(text4, evs4);
        evidencedTexts.add(entry1);
        evidencedTexts.add(entry2);
        evidencedTexts.add(entry3);
        evidencedTexts.add(entry4);
        FreeTextComment comment = buildComment(CommentType.PTM, evidencedTexts);
        doTest(ccLine, comment);
    }

    @Test
    void testPTMWithEvidence3() {
        String ccLine =
                ("CC   -!- PTM: Proteolytically processed under normal cellular conditions.\n"
                        + "CC       Cleavage either by alpha-secretase, beta-secretase or"
                        + " theta-secretase\n"
                        + "CC       leads to generation and extracellular release of soluble APP"
                        + " peptides,\n"
                        + "CC       S-APP-alpha and S-APP-beta, and the retention of corresponding\n"
                        + "CC       membrane-anchored C-terminal fragments, C80, C83 and C99.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205,"
                        + " ECO:0000313|Ensembl:ENSP00000409133}.\n"
                        + "CC       Subsequent processing of C80 and C83 by gamma-secretase yields"
                        + " P3\n"
                        + "CC       peptides. {ECO:0000313|Ensembl:ENSP00000409133}. This is the"
                        + " major\n"
                        + "CC       secretory pathway and is non-amyloidogenic. {ECO:0000256|HAMAP-\n"
                        + "CC       Rule:MF_00205}. Alternatively, presenilin/nicastrin-mediated"
                        + " gamma-\n"
                        + "CC       secretase processing of C99 releases the amyloid beta proteins,\n"
                        + "CC       amyloid-beta 40 (Abeta40) and amyloid-beta 42 (Abeta42), major\n"
                        + "CC       components of amyloid plaques, and the cytotoxic C-terminal"
                        + " fragments,\n"
                        + "CC       gamma-CTF(50), gamma-CTF(57) and gamma-CTF(59)."
                        + " {ECO:0000256|HAMAP-\n"
                        + "CC       Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133}.");
        String text1 =
                "Proteolytically processed under normal cellular conditions. "
                        + "Cleavage either by alpha-secretase, beta-secretase or theta-"
                        + "secretase leads to generation and extracellular release of soluble "
                        + "APP peptides, S-APP-alpha and S-APP-beta, and the retention of "
                        + "corresponding membrane-anchored C-terminal fragments, C80, C83 and "
                        + "C99";

        String text2 =
                "Subsequent processing of C80 and C83 by gamma-secretase " + "yields P3 peptides";
        String text3 = "This is the major secretory pathway and is " + "non-amyloidogenic";
        String text4 =
                "Alternatively, presenilin/nicastrin-mediated "
                        + "gamma-secretase processing of C99 releases the amyloid beta "
                        + "proteins, amyloid-beta 40 (Abeta40) and amyloid-beta 42 (Abeta42), "
                        + "major components of amyloid plaques, and the cytotoxic C-terminal "
                        + "fragments, gamma-CTF(50), gamma-CTF(57) and gamma-CTF(59)";
        List<String> evs1 = new ArrayList<>();
        evs1.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs1.add("ECO:0000313|Ensembl:ENSP00000409133");
        List<String> evs2 = new ArrayList<>();
        evs2.add("ECO:0000313|Ensembl:ENSP00000409133");
        List<String> evs3 = new ArrayList<>();
        evs3.add("ECO:0000256|HAMAP-Rule:MF_00205");
        List<String> evs4 = new ArrayList<>();
        evs4.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs4.add("ECO:0000313|Ensembl:ENSP00000409133");
        List<Map.Entry<String, List<String>>> evidencedTexts = new ArrayList<>();
        Map.Entry<String, List<String>> entry1 = new AbstractMap.SimpleEntry<>(text1, evs1);
        Map.Entry<String, List<String>> entry2 = new AbstractMap.SimpleEntry<>(text2, evs2);
        Map.Entry<String, List<String>> entry3 = new AbstractMap.SimpleEntry<>(text3, evs3);
        Map.Entry<String, List<String>> entry4 = new AbstractMap.SimpleEntry<>(text4, evs4);
        evidencedTexts.add(entry1);
        evidencedTexts.add(entry2);
        evidencedTexts.add(entry3);
        evidencedTexts.add(entry4);
        FreeTextComment comment = buildComment(CommentType.PTM, evidencedTexts);
        doTest(ccLine, comment);
    }

    @Test
    void testAllergen() throws Exception {
        String ccLine =
                ("CC   -!- ALLERGEN: Causes an allergic reaction in human. Binds to IgE."
                        + " Partially\n"
                        + "CC       heat-labile allergen that may cause both respiratory and"
                        + " food-allergy\n"
                        + "CC       symptoms in patients with the bird-egg syndrome.");
        String text =
                "Causes an allergic reaction in human. Binds to IgE. "
                        + "Partially heat-labile allergen that may cause both respiratory and "
                        + "food-allergy symptoms in patients with the bird-egg syndrome.";
        String ccLineString = "ALLERGEN: " + text;
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.ALLERGEN, text, evs);
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineString, comment);
    }

    @Test
    void testAllergenWithEvidence() throws Exception {
        String ccLine =
                ("CC   -!- ALLERGEN: Causes an allergic reaction in human. Binds to IgE."
                        + " Partially\n"
                        + "CC       heat-labile allergen that may cause both respiratory and"
                        + " food-allergy\n"
                        + "CC       symptoms in patients with the bird-egg syndrome."
                        + " {ECO:0000256|HAMAP-\n"
                        + "CC       Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133}.");
        String text =
                "Causes an allergic reaction in human. Binds to IgE. "
                        + "Partially heat-labile allergen that may cause both respiratory and "
                        + "food-allergy symptoms in patients with the bird-egg syndrome.";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.ALLERGEN, text, evs);
        String ccLineString = "ALLERGEN: " + text;
        String ccLineStringEvidence =
                ccLineString
                        + " {ECO:0000256|HAMAP-Rule:MF_00205,"
                        + " ECO:0000313|Ensembl:ENSP00000409133}.";
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testBIOTECHNOLOGY() {
        String ccLine =
                ("CC   -!- BIOTECHNOLOGY: The effect of PG can be neutralized by introducing an\n"
                        + "CC       antisense PG gene by genetic manipulation. The Flavr Savr"
                        + " tomato\n"
                        + "CC       produced by Calgene (Monsanto) in such a manner has a longer"
                        + " shelf life\n"
                        + "CC       due to delayed ripening (Probable).");
        String text =
                "The effect of PG can be neutralized by introducing "
                        + "an antisense PG gene by genetic manipulation. The Flavr Savr "
                        + "tomato produced by Calgene (Monsanto) in such a manner has a "
                        + "longer shelf life due to delayed ripening (Probable).";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.BIOTECHNOLOGY, text, evs);
        String ccLineString = "BIOTECHNOLOGY: " + text;
        // String ccLineStringEvidence =ccLineString+ " {ECO:0000256|HAMAP-Rule:MF_00205,
        // ECO:0000313|Ensembl:ENSP00000409133}.";
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineString, comment);
    }

    @Test
    void testBIOTECHNOLOGYWithEvidence() {
        String ccLine =
                ("CC   -!- BIOTECHNOLOGY: The effect of PG can be neutralized by introducing an\n"
                        + "CC       antisense PG gene by genetic manipulation. The Flavr Savr"
                        + " tomato\n"
                        + "CC       produced by Calgene (Monsanto) in such a manner has a longer"
                        + " shelf life\n"
                        + "CC       due to delayed ripening (Probable).\n"
                        + "CC       {ECO:0000313|Ensembl:ENSP00000409133}.");
        String text =
                "The effect of PG can be neutralized by introducing "
                        + "an antisense PG gene by genetic manipulation. The Flavr Savr "
                        + "tomato produced by Calgene (Monsanto) in such a manner has a "
                        + "longer shelf life due to delayed ripening (Probable).";
        List<String> evs = new ArrayList<>();
        //		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.BIOTECHNOLOGY, text, evs);
        String ccLineString = "BIOTECHNOLOGY: " + text;
        String ccLineStringEvidence = ccLineString + " {ECO:0000313|Ensembl:ENSP00000409133}.";
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testActivityRegulation() {
        String ccLine =
                ("CC   -!- ACTIVITY REGULATION: ATP + L-glutamate + NH(3) = ADP + phosphate + L-\n"
                        + "CC       glutamine.");
        String text = "ATP + L-glutamate + NH(3) = ADP + phosphate + L-glutamine.";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testActivityRegulationWithEvidence() {
        String ccLine =
                ("CC   -!- ACTIVITY REGULATION: ATP + L-glutamate + NH(3) = ADP + phosphate + L-\n"
                        + "CC       glutamine. {ECO:0000313|Ensembl:ENSP00000409133}.");
        String text = "ATP + L-glutamate + NH(3) = ADP + phosphate + L-glutamine.";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testCaution() {
        String ccLine =
                ("CC   -!- CAUTION: It is uncertain whether Met-1 or Met-3 is the initiator.");
        String text = "It is uncertain whether Met-1 or Met-3 is the initiator.";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.CAUTION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testCautionWithEvidence() {
        String ccLine =
                ("CC   -!- CAUTION: It is uncertain whether Met-1 or Met-3 is the initiator.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205}.");
        String text = "It is uncertain whether Met-1 or Met-3 is the initiator.";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.CAUTION, text, evs);
        doTest(ccLine, comment);
    }

    @Disabled
    void testCOFACTOR() {
        String ccLine = ("CC   -!- COFACTOR: FAD (By similarity).");
        String text = "FAD";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.COFACTOR, text, evs);
        doTest(ccLine, comment);
    }

    @Disabled
    void testCOFACTORWithEvidence() {
        String ccLine =
                ("CC   -!- COFACTOR: FAD (By similarity). {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text = "FAD";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");

        FreeTextComment comment = buildComment(CommentType.COFACTOR, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testDevelopmental() {
        String ccLine =
                ("CC   -!- DEVELOPMENTAL STAGE: Expressed early during conidial (dormant spores)\n"
                        + "CC       differentiation.");
        String text = "Expressed early during conidial (dormant spores) differentiation.";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.DEVELOPMENTAL_STAGE, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testDevelopmentalWithEvidence() {
        String ccLine =
                ("CC   -!- DEVELOPMENTAL STAGE: Expressed early during conidial (dormant spores)\n"
                        + "CC       differentiation. {ECO:0000313|Ensembl:ENSP00000409133}.");
        String text = "Expressed early during conidial (dormant spores) differentiation.";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.DEVELOPMENTAL_STAGE, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testDOMAIN() {
        String ccLine =
                ("CC   -!- DOMAIN: The B chain is composed of two domains, each domain consists"
                        + " of\n"
                        + "CC       3 homologous subdomains (alpha, beta, gamma).");
        String text =
                "The B chain is composed of two domains, each domain "
                        + "consists of 3 homologous subdomains (alpha, beta, gamma).";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.DOMAIN, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testDOMAINWithEvidence() {
        String ccLine =
                ("CC   -!- DOMAIN: The B chain is composed of two domains, each domain consists"
                        + " of\n"
                        + "CC       3 homologous subdomains (alpha, beta, gamma)."
                        + " {ECO:0000256|HAMAP-\n"
                        + "CC       Rule:MF_00205}.");
        String text =
                "The B chain is composed of two domains, each domain "
                        + "consists of 3 homologous subdomains (alpha, beta, gamma).";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.DOMAIN, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testEnxymeRegulation() {
        String ccLine =
                ("CC   -!- ACTIVITY REGULATION: The activity of this enzyme is controlled by\n"
                        + "CC       adenylation under conditions of abundant glutamine. The fully\n"
                        + "CC       adenylated enzyme complex is inactive (By similarity).");
        String text =
                "The activity of this enzyme is controlled by "
                        + "adenylation under conditions of abundant glutamine. The fully "
                        + "adenylated enzyme complex is inactive (By similarity)";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //	evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testEnxymeRegulationWithEvidence() {
        String ccLine =
                ("CC   -!- ACTIVITY REGULATION: The activity of this enzyme is controlled by\n"
                        + "CC       adenylation under conditions of abundant glutamine. The fully\n"
                        + "CC       adenylated enzyme complex is inactive (By similarity).\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205,"
                        + " ECO:0000313|Ensembl:ENSP00000409133}.");
        String text =
                "The activity of this enzyme is controlled by "
                        + "adenylation under conditions of abundant glutamine. The fully "
                        + "adenylated enzyme complex is inactive (By similarity)";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.ACTIVITY_REGULATION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testINDUCTION() {
        String ccLine =
                ("CC   -!- INDUCTION: By infection, plant wounding, or elicitor treatment of cell\n"
                        + "CC       cultures.");
        String text = "By infection, plant wounding, or elicitor treatment of cell cultures.";
        List<String> evs = new ArrayList<>();
        //		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.INDUCTION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testINDUCTIONWithEvidence() {
        String ccLine =
                ("CC   -!- INDUCTION: By infection, plant wounding, or elicitor treatment of cell\n"
                        + "CC       cultures. {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text = "By infection, plant wounding, or elicitor treatment of cell cultures.";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.INDUCTION, text, evs);
        doTest(ccLine, comment);
        String ccLineString = "INDUCTION: " + text;
        String ccLineStringEvidence =
                ccLineString
                        + " {ECO:0000256|HAMAP-Rule:MF_00205,"
                        + " ECO:0000313|Ensembl:ENSP00000409133}.";
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testMISC() {
        String ccLine = ("CC   -!- MISCELLANEOUS: Binds to bacitracin.");
        String text = "Binds to bacitracin.";
        List<String> evs = new ArrayList<>();
        //		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.MISCELLANEOUS, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testMISCWithEvidence() {
        String ccLine =
                ("CC   -!- MISCELLANEOUS: Binds to bacitracin.\n"
                        + "CC       {ECO:0000313|Ensembl:ENSP00000409133}.");
        String text = "Binds to bacitracin";
        List<String> evs = new ArrayList<>();
        //		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.MISCELLANEOUS, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testPATH() {
        String ccLine =
                ("CC   -!- PATHWAY: Porphyrin biosynthesis by the C5 pathway; second step.");
        String text = "Porphyrin biosynthesis by the C5 pathway; second step.";
        List<String> evs = new ArrayList<>();
        //		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.PATHWAY, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testPATHWithEvidence() {
        String ccLine =
                ("CC   -!- PATHWAY: Porphyrin biosynthesis by the C5 pathway; second step.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205}.");
        String text = "Porphyrin biosynthesis by the C5 pathway; second step.";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.PATHWAY, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testPAHRMA() {
        String ccLine =
                ("CC   -!- PHARMACEUTICAL: Available under the names Avonex (Biogen), Betaseron\n"
                        + "CC       (Berlex) and Rebif (Serono). Used in the treatment of multiple\n"
                        + "CC       sclerosis (MS). Betaseron is a slightly modified form of IFNB1"
                        + " with two\n"
                        + "CC       residue substitutions.");
        String text =
                "Available under the names Avonex (Biogen), "
                        + "Betaseron (Berlex) and Rebif (Serono). Used in the treatment of "
                        + "multiple sclerosis (MS). Betaseron is a slightly modified form of "
                        + "IFNB1 with two residue substitutions.";
        List<String> evs = new ArrayList<>();
        //		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.PHARMACEUTICAL, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testPAHRMAWithEvidence() {
        String ccLine =
                ("CC   -!- PHARMACEUTICAL: Available under the names Avonex (Biogen), Betaseron\n"
                        + "CC       (Berlex) and Rebif (Serono). Used in the treatment of multiple\n"
                        + "CC       sclerosis (MS). Betaseron is a slightly modified form of IFNB1"
                        + " with two\n"
                        + "CC       residue substitutions. {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text =
                "Available under the names Avonex (Biogen), "
                        + "Betaseron (Berlex) and Rebif (Serono). Used in the treatment of "
                        + "multiple sclerosis (MS). Betaseron is a slightly modified form of "
                        + "IFNB1 with two residue substitutions";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.PHARMACEUTICAL, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testPTM() {
        String ccLine =
                ("CC   -!- PTM: Proteolytically processed under normal cellular conditions.\n"
                        + "CC       Cleavage either by alpha-secretase, beta-secretase or"
                        + " theta-secretase\n"
                        + "CC       leads to generation and extracellular release of soluble APP"
                        + " peptides,\n"
                        + "CC       S-APP-alpha and S-APP-beta, and the retention of corresponding\n"
                        + "CC       membrane-anchored C-terminal fragments, C80, C83 and C99."
                        + " Subsequent\n"
                        + "CC       processing of C80 and C83 by gamma-secretase yields P3 peptides."
                        + " This\n"
                        + "CC       is the major secretory pathway and is non-amyloidogenic."
                        + " Alternatively,\n"
                        + "CC       presenilin/nicastrin-mediated gamma-secretase processing of C99\n"
                        + "CC       releases the amyloid beta proteins, amyloid-beta 40 (Abeta40)"
                        + " and\n"
                        + "CC       amyloid-beta 42 (Abeta42), major components of amyloid plaques,"
                        + " and the\n"
                        + "CC       cytotoxic C-terminal fragments, gamma-CTF(50), gamma-CTF(57) and"
                        + " gamma-\n"
                        + "CC       CTF(59).");
        String text =
                "Proteolytically processed under normal cellular conditions. "
                        + "Cleavage either by alpha-secretase, beta-secretase or theta-"
                        + "secretase leads to generation and extracellular release of soluble "
                        + "APP peptides, S-APP-alpha and S-APP-beta, and the retention of "
                        + "corresponding membrane-anchored C-terminal fragments, C80, C83 and "
                        + "C99. Subsequent processing of C80 and C83 by gamma-secretase "
                        + "yields P3 peptides. This is the major secretory pathway and is "
                        + "non-amyloidogenic. Alternatively, presenilin/nicastrin-mediated "
                        + "gamma-secretase processing of C99 releases the amyloid beta "
                        + "proteins, amyloid-beta 40 (Abeta40) and amyloid-beta 42 (Abeta42), "
                        + "major components of amyloid plaques, and the cytotoxic C-terminal "
                        + "fragments, gamma-CTF(50), gamma-CTF(57) and gamma-CTF(59)";
        List<String> evs = new ArrayList<>();
        //		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.PTM, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testPTMWithEvidence() {
        String ccLine =
                ("CC   -!- PTM: Proteolytically processed under normal cellular conditions.\n"
                        + "CC       Cleavage either by alpha-secretase, beta-secretase or"
                        + " theta-secretase\n"
                        + "CC       leads to generation and extracellular release of soluble APP"
                        + " peptides,\n"
                        + "CC       S-APP-alpha and S-APP-beta, and the retention of corresponding\n"
                        + "CC       membrane-anchored C-terminal fragments, C80, C83 and C99."
                        + " Subsequent\n"
                        + "CC       processing of C80 and C83 by gamma-secretase yields P3 peptides."
                        + " This\n"
                        + "CC       is the major secretory pathway and is non-amyloidogenic."
                        + " Alternatively,\n"
                        + "CC       presenilin/nicastrin-mediated gamma-secretase processing of C99\n"
                        + "CC       releases the amyloid beta proteins, amyloid-beta 40 (Abeta40)"
                        + " and\n"
                        + "CC       amyloid-beta 42 (Abeta42), major components of amyloid plaques,"
                        + " and the\n"
                        + "CC       cytotoxic C-terminal fragments, gamma-CTF(50), gamma-CTF(57) and"
                        + " gamma-\n"
                        + "CC       CTF(59). {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text =
                "Proteolytically processed under normal cellular conditions. "
                        + "Cleavage either by alpha-secretase, beta-secretase or theta-"
                        + "secretase leads to generation and extracellular release of soluble "
                        + "APP peptides, S-APP-alpha and S-APP-beta, and the retention of "
                        + "corresponding membrane-anchored C-terminal fragments, C80, C83 and "
                        + "C99. Subsequent processing of C80 and C83 by gamma-secretase "
                        + "yields P3 peptides. This is the major secretory pathway and is "
                        + "non-amyloidogenic. Alternatively, presenilin/nicastrin-mediated "
                        + "gamma-secretase processing of C99 releases the amyloid beta "
                        + "proteins, amyloid-beta 40 (Abeta40) and amyloid-beta 42 (Abeta42), "
                        + "major components of amyloid plaques, and the cytotoxic C-terminal "
                        + "fragments, gamma-CTF(50), gamma-CTF(57) and gamma-CTF(59)";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.PTM, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testSIMILARITY() {
        String ccLine = ("CC   -!- SIMILARITY: Belongs to the annexin family.");

        String text = "Belongs to the annexin family";
        List<String> evs = new ArrayList<>();
        // evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.SIMILARITY, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testSIMILARITYWithEvidence() {
        String ccLine =
                ("CC   -!- SIMILARITY: Belongs to the annexin family. {ECO:0000256|HAMAP-\n"
                        + "CC       Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133}.");

        String text = "Belongs to the annexin family";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.SIMILARITY, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testSUBUNIT() {
        String ccLine = ("CC   -!- SUBUNIT: Homotetramer.");
        String text = "Homotetramer";
        List<String> evs = new ArrayList<>();
        // evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.SUBUNIT, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testSUBUNITWithEvidence() {
        String ccLine = ("CC   -!- SUBUNIT: Homotetramer. {ECO:0000256|HAMAP-Rule:MF_00205}.");
        String text = "Homotetramer";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.SUBUNIT, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testTissueSpecificity() {
        String ccLine =
                ("CC   -!- TISSUE SPECIFICITY: Shoots, roots, and cotyledon from dehydrating\n"
                        + "CC       seedlings.");
        String text = "Shoots, roots, and cotyledon from dehydrating seedlings";
        List<String> evs = new ArrayList<>();
        // evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.TISSUE_SPECIFICITY, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testTissueSpecificityWithEvidence() {
        String ccLine =
                ("CC   -!- TISSUE SPECIFICITY: Shoots, roots, and cotyledon from dehydrating\n"
                        + "CC       seedlings. {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text = "Shoots, roots, and cotyledon from dehydrating seedlings";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.TISSUE_SPECIFICITY, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testToxic() {
        String ccLine =
                ("CC   -!- TOXIC DOSE: PD(50) is 1.72 mg/kg by injection in blowfly larvae.");
        String text = "PD(50) is 1.72 mg/kg by injection in blowfly larvae";
        List<String> evs = new ArrayList<>();
        // evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.TOXIC_DOSE, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testToxicWithEvidence() {
        String ccLine =
                ("CC   -!- TOXIC DOSE: PD(50) is 1.72 mg/kg by injection in blowfly larvae.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205,"
                        + " ECO:0000313|Ensembl:ENSP00000409133}.");
        String text = "PD(50) is 1.72 mg/kg by injection in blowfly larvae";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.TOXIC_DOSE, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testFunctionWithNoEvidence() {
        String ccLine =
                ("CC   -!- FUNCTION: Bifunctional enzyme that catalyzes the first two steps in\n"
                        + "CC       lysine degradation. The N-terminal and the C-terminal contain"
                        + " lysine-\n"
                        + "CC       oxoglutarate reductase and saccharopine dehydrogenase "
                        + " activity,\n"
                        + "CC       respectively. Negatively regulates free Lys accumulation in"
                        + " seeds.");
        String text =
                "Bifunctional enzyme that catalyzes the first two steps "
                        + "in lysine degradation. The N-terminal and the C-terminal contain "
                        + "lysine-oxoglutarate reductase and saccharopine dehydrogenase "
                        + " activity, respectively. Negatively regulates free Lys accumulation "
                        + "in seeds";
        List<String> evs = new ArrayList<>();
        //	evs.add("ECO:0000256|HAMAP-Rule:MF_00205");

        FreeTextComment comment = buildComment(CommentType.FUNCTION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testFunctionWithEvidence() {
        String ccLine =
                ("CC   -!- FUNCTION: Bifunctional enzyme that catalyzes the first two steps in\n"
                        + "CC       lysine degradation. The N-terminal and the C-terminal contain"
                        + " lysine-\n"
                        + "CC       oxoglutarate reductase and saccharopine dehydrogenase "
                        + " activity,\n"
                        + "CC       respectively. Negatively regulates free Lys accumulation in"
                        + " seeds.\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205}.");
        String text =
                "Bifunctional enzyme that catalyzes the first two steps "
                        + "in lysine degradation. The N-terminal and the C-terminal contain "
                        + "lysine-oxoglutarate reductase and saccharopine dehydrogenase "
                        + " activity, respectively. Negatively regulates free Lys accumulation "
                        + "in seeds";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        //			Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205"});
        FreeTextComment comment = buildComment(CommentType.FUNCTION, text, evs);
        doTest(ccLine, comment);
    }

    @Test
    void test2() throws Exception {
        String ccLine =
                ("CC   -!- FUNCTION: Stimulates the proliferation of early hematopoietic cells.\n"
                        + "CC       Synergizes well with a number of other colony stimulating factors"
                        + " and\n"
                        + "CC       interleukins. {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");

        String text =
                "Stimulates the proliferation of early hematopoietic "
                        + "cells. Synergizes well with a number of other colony stimulating "
                        + "factors and interleukins";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.FUNCTION, text, evs);
        doTest(ccLine, comment);
    }
    //
    @Test
    void testFunctionWithEvidence2() {
        String ccLine =
                ("CC   -!- FUNCTION: Bifunctional enzyme that catalyzes the first two steps in\n"
                        + "CC       lysine degradation. The N-terminal and the C-terminal contain"
                        + " lysine-\n"
                        + "CC       oxoglutarate reductase and saccharopine dehydrogenase activity,\n"
                        + "CC       respectively. Negatively regulates free Lys accumulation in seeds"
                        + " (By\n"
                        + "CC       similarity). {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|Ensembl:ENSP00000409133}.");
        String text =
                "Bifunctional enzyme that catalyzes the first two steps "
                        + "in lysine degradation. The N-terminal and the C-terminal contain "
                        + "lysine-oxoglutarate reductase and saccharopine dehydrogenase"
                        + " activity, respectively. Negatively regulates free Lys accumulation "
                        + "in seeds (By similarity)";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");
        FreeTextComment comment = buildComment(CommentType.FUNCTION, text, evs);
        doTest(ccLine, comment);
    }

    private FreeTextComment buildComment(
            CommentType type, List<Map.Entry<String, List<String>>> evidencedTexts) {
        List<EvidencedValue> evidencedValues =
                evidencedTexts.stream()
                        .map(val -> createEvidencedValue(val.getKey(), val.getValue()))
                        .collect(Collectors.toList());
        FreeTextCommentBuilder builder = new FreeTextCommentBuilder();
        builder.commentType(type).textsSet(evidencedValues);
        return builder.build();
    }

    private FreeTextComment buildComment(CommentType type, String text, List<String> evs) {
        List<Map.Entry<String, List<String>>> evidencedTexts = new ArrayList<>();
        evidencedTexts.add(new AbstractMap.SimpleEntry<>(text, evs));
        return buildComment(type, evidencedTexts);
    }
}
