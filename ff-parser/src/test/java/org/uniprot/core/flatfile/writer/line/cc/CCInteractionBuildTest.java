package org.uniprot.core.flatfile.writer.line.cc;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionCommentBuilder;

class CCInteractionBuildTest extends CCBuildTestAbstr {
    @Test
    void testINTERACTION1() {
        String ccLine =
                ("CC   -!- INTERACTION:\n"
                        + "CC       P12345; Q9W158: CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n"
                        + "CC       P12345-2; PRO_0000037566 [P27958]; Xeno; NbExp=1;"
                        + " IntAct=EBI-123485, EBI-126770;");
        String ccLineString =
                ("INTERACTION:\n"
                        + "P12345; Q9W158: CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n"
                        + "P12345-2; PRO_0000037566 [P27958]; Xeno; NbExp=1; IntAct=EBI-123485,"
                        + " EBI-126770;");

        InteractionCommentBuilder builder = new InteractionCommentBuilder();
        List<Interaction> interactions = new ArrayList<>();
        String acc1 = "P12345";
        String acc2 = "Q9W158";
        String geneName = "CG4612";
        int nExperiments = 1;
        String firstInter = "EBI-123485";
        String secInter = "EBI-89895";
        Interaction inter1 =
                buildInteraction(
                        acc1,
                        null,
                        firstInter,
                        acc2,
                        null,
                        geneName,
                        secInter,
                        nExperiments,
                        false);
        interactions.add(inter1);
        firstInter = "EBI-123485";
        secInter = "EBI-126770";
        Interaction inter2 =
                buildInteraction(
                        "P12345-2",
                        null,
                        firstInter,
                        "P27958",
                        "PRO_0000037566",
                        null,
                        secInter,
                        1,
                        true);

        interactions.add(inter2);

        builder.interactionsSet(interactions);
        InteractionComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    //
    @Test
    void testINTERACTION2() {
        //        String ccLine =
        //                ("CC   -!- INTERACTION:\n"
        //                        + "CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844,
        // EBI-212772;\n"
        //                        + "CC       O96017:CHEK2; NbExp=4; IntAct=EBI-372428,
        // EBI-1180783;\n"
        //                        + "CC       Q6ZWQ9:Myl12a (xeno); NbExp=3; IntAct=EBI-372428,
        // EBI-8034418;");
        //        String ccLineString =
        //                ("INTERACTION:\n"
        //                        + "Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n"
        //                        + "O96017:CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n"
        //                        + "Q6ZWQ9:Myl12a (xeno); NbExp=3; IntAct=EBI-372428,
        // EBI-8034418;");

        String ccLine =
                ("CC   -!- INTERACTION:\n"
                        + "CC       PRO_0000037566; Q9W1K5-1: CG11299; NbExp=1; IntAct=EBI-133844,"
                        + " EBI-212772;\n"
                        + "CC       P12345-1; O96017: CHEK2; NbExp=4; IntAct=EBI-372428,"
                        + " EBI-1180783;\n"
                        + "CC       P12345-2; Q6ZWQ9: Myl12a; Xeno; NbExp=3; IntAct=EBI-372428,"
                        + " EBI-8034418;");
        String ccLineString =
                ("INTERACTION:\n"
                        + "PRO_0000037566; Q9W1K5-1: CG11299; NbExp=1; IntAct=EBI-133844,"
                        + " EBI-212772;\n"
                        + "P12345-1; O96017: CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n"
                        + "P12345-2; Q6ZWQ9: Myl12a; Xeno; NbExp=3; IntAct=EBI-372428, EBI-8034418;");

        InteractionCommentBuilder builder = new InteractionCommentBuilder();
        List<Interaction> interactions = new ArrayList<>();
        Interaction inter1 =
                buildInteraction(
                        null,
                        "PRO_0000037566",
                        "EBI-133844",
                        "Q9W1K5-1",
                        null,
                        "CG11299",
                        "EBI-212772",
                        1,
                        false);
        interactions.add(inter1);
        Interaction inter2 =
                buildInteraction(
                        "P12345-1",
                        null,
                        "EBI-372428",
                        "O96017",
                        null,
                        "CHEK2",
                        "EBI-1180783",
                        4,
                        false);

        interactions.add(inter2);

        Interaction inter3 =
                buildInteraction(
                        "P12345-2",
                        null,
                        "EBI-372428",
                        "Q6ZWQ9",
                        null,
                        "Myl12a",
                        "EBI-8034418",
                        3,
                        true);
        interactions.add(inter3);

        builder.interactionsSet(interactions);
        InteractionComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    private Interaction buildInteraction(
            String acc1,
            String chainId1,
            String intActId1,
            String acc2,
            String chainId2,
            String geneName,
            String intActId2,
            int nExperments,
            boolean xeno) {
        InteractionBuilder builder = new InteractionBuilder();
        InteractantBuilder builder1 = new InteractantBuilder();
        InteractantBuilder builder2 = new InteractantBuilder();
        if (acc1 != null) {
            builder1.uniProtKBAccession(acc1);
        }
        if (chainId1 != null) {
            builder1.chainId(chainId1);
        }
        builder1.intActId(intActId1);
        builder2.chainId(chainId2).uniProtKBAccession(acc2).geneName(geneName).intActId(intActId2);
        builder.interactantOne(builder1.build())
                .interactantTwo(builder2.build())
                .numberOfExperiments(nExperments)
                .isOrganismDiffer(xeno);

        return builder.build();
    }
}
