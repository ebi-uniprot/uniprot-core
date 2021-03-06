package org.uniprot.core.scorer.uniprotkb.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprotkb.comment.CommentType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.scorer.uniprotkb.UniProtEntryScored;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;

class CCScoredTest {
    private static final String CC =
            "CC   -!- FUNCTION: Adapter protein implicated in the regulation of a large\n"
                    + // 9.0
                    "CC       spectrum of both general and specialized signaling pathways. Binds\n"
                    + "CC       to a large number of partners, usually by recognition of a\n"
                    + "CC       phosphoserine or phosphothreonine motif. Binding generally results\n"
                    + "CC       in the modulation of the activity of the binding partner.\n"
                    + "CC   -!- SUBUNIT: Homodimer, and heterodimer with other family members.\n"
                    + // 3.0
                    "CC       {ECO:0000256|HAMAP-Rule:MF_01146}.\n"
                    + "CC   -!- SUBCELLULAR LOCATION: Cytoplasm {ECO:0000256|HAMAP-Rule:MF_01146}.\n"
                    + // 3.0 but is 1.0 in original unp.fw.tools test
                    // When debugging I can see that the subcell object has no associated evidence,
                    // but it should have one. Why?
                    "CC   -!- MASS SPECTROMETRY: Mass=29440; Mass_error=2; Method=MALDI;\n"
                    + // 9.0
                    "CC       Range=1-255; Evidence={ECO:0000269|PubMed:10433554};\n"
                    + "CC   -!- SIMILARITY: Belongs to the 14-3-3 family."; // 3.0

    @Test
    void test1() throws Exception {
        CcLineTransformer ccLineTransformer = new CcLineTransformer("", "");
        List<Comment> comments = ccLineTransformer.transformNoHeader(CC);
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "ID_12345", UniProtKBEntryType.SWISSPROT)
                        .commentsSet(comments)
                        .build();

        UniProtEntryScored scored = new UniProtEntryScored(entry);
        assertEquals(25.0, scored.getEntryScore().getCommentScore(), 0.001);
        List<Comment> functionComments =
                entry.getComments().stream()
                        .filter(c -> c.getCommentType().equals(CommentType.FUNCTION))
                        .collect(Collectors.toList());
        assertEquals(1, functionComments.size());
        Comment comment = functionComments.get(0);
        CommentScored fcScored = CommentScoredFactory.create(comment);
        fcScored.setIsSwissProt(true);

        assertEquals(fcScored.score(), 9.0, 0.001);

        List<Comment> suComments =
                entry.getComments().stream()
                        .filter(c -> c.getCommentType().equals(CommentType.SUBUNIT))
                        .collect(Collectors.toList());
        assertEquals(1, suComments.size());
        Comment suComment = suComments.get(0);
        CommentScored suScored = CommentScoredFactory.create(suComment);
        suScored.setIsSwissProt(true);
        assertEquals(suScored.score(), 3.0, 0.001);

        List<Comment> slComments =
                entry.getComments().stream()
                        .filter(c -> c.getCommentType().equals(SUBCELLULAR_LOCATION))
                        .collect(Collectors.toList());
        assertEquals(1, slComments.size());
        Comment slComment = slComments.get(0);
        CommentScored slScored = CommentScoredFactory.create(slComment);
        slScored.setIsSwissProt(true);
        assertEquals(slScored.score(), 1.0, 0.001);

        List<Comment> msComments =
                entry.getComments().stream()
                        .filter(c -> c.getCommentType().equals(MASS_SPECTROMETRY))
                        .collect(Collectors.toList());
        assertEquals(1, msComments.size());
        Comment msComment = msComments.get(0);
        CommentScored msScored = CommentScoredFactory.create(msComment);
        msScored.setIsSwissProt(true);
        assertEquals(msScored.score(), 9.0, 0.001);

        List<Comment> smComments =
                entry.getComments().stream()
                        .filter(c -> c.getCommentType().equals(SIMILARITY))
                        .collect(Collectors.toList());
        assertEquals(1, smComments.size());
        Comment smComment = smComments.get(0);
        CommentScored smScored = CommentScoredFactory.create(smComment);
        smScored.setIsSwissProt(true);
        assertEquals(smScored.score(), 3.0, 0.001);
    }

    private static final String CC_2 =
            "CC   -!- FUNCTION: Adapter protein implicated in the regulation of a large\n"
                    + "CC       spectrum of both general and specialized signaling pathways. Binds\n"
                    + "CC       to a large number of partners, usually by recognition of a\n"
                    + "CC       phosphoserine or phosphothreonine motif. Binding generally results\n"
                    + "CC       in the modulation of the activity of the binding partner. When\n"
                    + "CC       bound to KRT17, regulates protein synthesis and epithelial cell\n"
                    + "CC       growth by stimulating Akt/mTOR pathway."
                    + " {ECO:0000256|HAMAP-Rule:MF_01146}.\n"
                    + "CC   -!- FUNCTION: p53-regulated inhibitor of G2/M progression.\n"
                    + "CC   -!- SUBUNIT: Homodimer. Interacts with KRT17 and SAMSN1 (By\n"
                    + "CC       similarity). Found in a complex with XPO7, EIF4A1, ARHGAP1,\n"
                    + "CC       VPS26A, VPS29, VPS35 and SFN. Interacts with GAB2. Interacts with\n"
                    + "CC       SRPK2. Interacts with COPS6. Interacts with RFWD2; this\n"
                    + "CC       interaction leads to proteasomal degradation.\n"
                    + "CC   -!- INTERACTION:\n"
                    + "CC       P12345; P00519: ABL1; NbExp=2; IntAct=EBI-476295, EBI-375543;\n"
                    + "CC       P12345; Q7L5N1: COPS6; NbExp=7; IntAct=EBI-476295, EBI-486838;\n"
                    + "CC       P12345; Q14103-4: HNRNPD; NbExp=7; IntAct=EBI-476295, EBI-432545;\n"
                    + "CC       P12345; O00444: PLK4; NbExp=2; IntAct=EBI-476295, EBI-746202;\n"
                    + "CC       P12345; Q8NHY2: RFWD2; NbExp=5; IntAct=EBI-476295, EBI-1176214;\n"
                    + "CC   -!- SUBCELLULAR LOCATION: Cytoplasm. Nucleus"
                    + " {ECO:0000256|HAMAP-Rule:MF_01146}.\n"
                    + "CC       Secreted. Note=May be secreted by a non-classical secretory\n"
                    + "CC       pathway.\n"
                    + "CC   -!- ALTERNATIVE PRODUCTS:\n"
                    + "CC       Event=Alternative splicing; Named isoforms=2;\n"
                    + "CC       Name=1;\n"
                    + "CC         IsoId=P31947-1; Sequence=Displayed;\n"
                    + "CC       Name=2;\n"
                    + "CC         IsoId=P31947-2; Sequence=VSP_021768;\n"
                    + "CC         Note=No experimental confirmation available.;\n"
                    + "CC   -!- TISSUE SPECIFICITY: Present mainly in tissues enriched in\n"
                    + "CC       stratified squamous keratinizing epithelium.\n"
                    + "CC   -!- SIMILARITY: Belongs to the 14-3-3 family.";

    @Test
    void test2() {
        CcLineTransformer ccLineTransformer = new CcLineTransformer("", "");
        List<Comment> comments = ccLineTransformer.transformNoHeader(CC_2);
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "ID_12345", UniProtKBEntryType.SWISSPROT)
                        .commentsSet(comments)
                        .build();

        UniProtEntryScored scored = new UniProtEntryScored(entry);
        assertEquals(55.0, scored.getEntryScore().getCommentScore(), 0.001);
    }
}
