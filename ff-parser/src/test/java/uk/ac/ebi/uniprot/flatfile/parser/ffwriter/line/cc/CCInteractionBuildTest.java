package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line.cc;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;

import java.util.ArrayList;
import java.util.List;


public class CCInteractionBuildTest extends CCBuildTestAbstr {
    @Test
    public void testINTERACTION1() {
        String ccLine = ("CC   -!- INTERACTION:\n" +
                "CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;\n" +
                "CC       Q9W158:CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n" +
                "CC       Q9VYI0:fne; NbExp=1; IntAct=EBI-123485, EBI-126770;");
        String ccLineString = ("INTERACTION:\n" +
                "Self; NbExp=1; IntAct=EBI-123485, EBI-123485;\n" +
                "Q9W158:CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n" +
                "Q9VYI0:fne; NbExp=1; IntAct=EBI-123485, EBI-126770;");
        InteractionCommentBuilder builder = new InteractionCommentBuilder();
        List<Interaction> interactions = new ArrayList<>();
        String acc = "";
        String geneName = "";
        int nExperiments = 1;
        String firstInter = "EBI-123485";
        String secInter = "EBI-123485";
        Interaction inter1 = buildInteraction(InteractionType.SELF, acc, geneName, nExperiments,
                                              firstInter, secInter);
        interactions.add(inter1);
        acc = "Q9W158";
        geneName = "CG4612";
        nExperiments = 1;
        firstInter = "EBI-123485";
        secInter = "EBI-89895";
        Interaction inter2 = buildInteraction(InteractionType.BINARY, acc, geneName, nExperiments,
                                              firstInter, secInter);
        interactions.add(inter2);

        acc = "Q9VYI0";
        geneName = "fne";
        nExperiments = 1;
        firstInter = "EBI-123485";
        secInter = "EBI-126770";
        Interaction inter3 = buildInteraction(InteractionType.BINARY, acc, geneName, nExperiments,
                                              firstInter, secInter);
        interactions.add(inter3);
        builder.interactions(interactions);
        InteractionComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    //
    @Test
    public void testINTERACTION2() {
        String ccLine = ("CC   -!- INTERACTION:\n" +
                "CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n" +
                "CC       O96017:CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n" +
                "CC       Q6ZWQ9:Myl12a (xeno); NbExp=3; IntAct=EBI-372428, EBI-8034418;");
        String ccLineString = ("INTERACTION:\n" +
                "Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n" +
                "O96017:CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n" +
                "Q6ZWQ9:Myl12a (xeno); NbExp=3; IntAct=EBI-372428, EBI-8034418;");
        InteractionCommentBuilder builder = new InteractionCommentBuilder();
        List<Interaction> interactions = new ArrayList<>();
        String acc = "Q9W1K5-1";
        String geneName = "CG11299";
        int nExperiments = 1;
        String firstInter = "EBI-133844";
        String secInter = "EBI-212772";
        Interaction inter1 = buildInteraction(InteractionType.BINARY, acc, geneName, nExperiments,
                                              firstInter, secInter);
        interactions.add(inter1);

        acc = "O96017";
        geneName = "CHEK2";
        nExperiments = 4;
        firstInter = "EBI-372428";
        secInter = "EBI-1180783";
        Interaction inter2 = buildInteraction(InteractionType.BINARY, acc, geneName, nExperiments,
                                              firstInter, secInter);
        interactions.add(inter2);

        acc = "Q6ZWQ9";
        geneName = "Myl12a";
        nExperiments = 3;
        firstInter = "EBI-372428";
        secInter = "EBI-8034418";
        Interaction inter3 = buildInteraction(InteractionType.XENO, acc, geneName, nExperiments,
                                              firstInter, secInter);
        interactions.add(inter3);

        builder.interactions(interactions);
        InteractionComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    private Interaction buildInteraction(InteractionType type, String uniProtAcc,
                                         String geneName, int nExperments,
                                         String firstAcc, String secAcc) {
        InteractionBuilder builder = new InteractionBuilder();

        builder.interactionType(type);
        builder.firstInteractor(firstAcc);
        builder.secondInteractor(secAcc);
        builder.uniProtAccession(uniProtAcc);
        builder.geneName(geneName);
        builder.numberOfExperiments(nExperments);
        return builder.build();
    }
}
