package uk.ebi.uniprot.scorer.uniprotkb.comments;

import org.junit.Test;

public class MultiCommentScoredTest extends CommentScoreTestBase {

    @Test
    public void shouldScore60() throws Exception {
        String line = "CC   -!- FUNCTION: Involved in the presentation of foreign antigens to the\n" +
                "CC       immune system. {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "CC   -!- SIMILARITY: Belongs to the MHC class I family.\n" +
                "CC   -!- SIMILARITY: Contains 1 Ig-like C1-type (immunoglobulin-like)\n" +
                "CC       domain.";
        verifyMulti(line, 6.0, true);

    }

    @Test
    public void shouldScore160() throws Exception {
        String line = "CC   -!- FUNCTION: Envelope protein E binding to host cell surface receptor\n" +   // 3.0
                "CC       is followed by virus internalization through clathrin-mediated\n" +
                "CC       endocytosis. Envelope protein E is subsequently involved in\n" +
                "CC       membrane fusion between virion and host late endosomes.\n" +
                "CC       Synthesized as an homodimer with prM which acts as a chaperone for\n" +
                "CC       envelope protein E. After cleavage of prM, envelope protein E\n" +
                "CC       dissociate from small envelope protein M and homodimerizes.\n" +
                "CC       {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "CC   -!- CATALYTIC ACTIVITY:\n" +
                "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n" +              // 3.0
                "CC         rhamnose + H(+); Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n" +
                "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n" +
                "CC         EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n" +
                "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n" +
                "CC         ECO:0000269|PubMed:9473059};\n" +
                "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n" +
                "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n"+
                "CC       PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898;\n" +
                "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00957};\n" +
                "CC   -!- SUBCELLULAR LOCATION: Virion membrane; Multi-pass membrane\n" +          // 2.0    but should be 4.0
                "CC       protein. Host endoplasmic reticulum membrane; Multi-pass membrane2\n" +
                "CC       protein {ECO:0000256|HAMAP-Rule:MF_01146}.\n" +
                "CC   -!- SIMILARITY: Contains 1 RdRp catalytic domain.\n" +
                "CC   -!- SIMILARITY: Contains 1 helicase ATP-binding domain.\n" +
                "CC   -!- SIMILARITY: Contains 1 helicase C-terminal domain.";
        verifyMulti(line, 10.0, true);

    }

    @Test
    public void shouldScore140() throws Exception {
        String line = "CC   -!- FUNCTION: Envelope protein E binding to host cell surface receptor\n" +
                "CC       is followed by virus internalization through clathrin-mediated\n" +
                "CC       endocytosis. Envelope protein E is subsequently involved in\n" +
                "CC       membrane fusion between virion and host late endosomes.\n" +
                "CC       Synthesized as an homodimer with prM which acts as a chaperone for\n" +
                "CC       envelope protein E. After cleavage of prM, envelope protein E\n" +
                "CC       dissociate from small envelope protein M and homodimerizes. {ECO:0000256|PubMed:16629414}.\n"
                +
        //        "CC   -!- CATALYTIC ACTIVITY: ATP + H(2)O = ADP + phosphate.\n" +
        //        "CC   -!- CATALYTIC ACTIVITY: NTP + H(2)O = NDP + phosphate.\n" +
        //        "CC   -!- CATALYTIC ACTIVITY: Nucleoside triphosphate + RNA(n) = diphosphate\n" +
       //         "CC       + RNA(n+1).\n" +
                "CC   -!- SUBCELLULAR LOCATION: Envelope protein E: Virion membrane {ECO:0000256|HAMAP-Rule:MF_01146}; Multi-\n"
                +
                "CC       pass membrane protein {ECO:0000256|HAMAP-Rule:MF_01146}. Host endoplasmic reticulum membrane\n"
                +
                "CC       {ECO:0000256|HAMAP-Rule:MF_01146}; Multi-\n" +
                "CC       pass membrane protein {ECO:0000256|PubMed:16629414}.\n" +
                "CC   -!- SIMILARITY: Contains 1 RdRp catalytic domain. {ECO:0000256|PubMed:16629414}.\n" +
                "CC   -!- SIMILARITY: Contains 1 helicase ATP-binding domain. {ECO:0000256|PubMed:16629414}.\n" +
                "CC   -!- SIMILARITY: Contains 1 helicase C-terminal domain. {ECO:0000256|PubMed:16629414}.";
        verifyMulti(line, 5.0, true);

    }

}