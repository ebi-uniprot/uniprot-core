package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;

class InteractionCommentTransformerTest {
    private final InteractionCommentTransformer transformer = new InteractionCommentTransformer();

    @Test
    public void test1() {
        String ccLineString =
                ("INTERACTION:\n"
                        + "P12345; Q9W158: CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n"
                        + "P12345; PRO_0000037566 [P27958]; Xeno; NbExp=2; IntAct=EBI-123485,"
                        + " EBI-126770;");
        InteractionComment comment = transformer.transform(CommentType.INTERACTION, ccLineString);
        assertNotNull(comment);
        assertEquals(2, comment.getInteractions().size());

        Interaction interaction1 = comment.getInteractions().get(0);
        assertEquals(1, interaction1.getNumberOfExperiments());
        assertFalse(interaction1.isOrganismsDiffer());
        verifyInteractor(interaction1.getInteractantOne(), null, "P12345", null, "EBI-123485");
        verifyInteractor(interaction1.getInteractantTwo(), null, "Q9W158", "CG4612", "EBI-89895");

        Interaction interaction2 = comment.getInteractions().get(1);
        assertEquals(2, interaction2.getNumberOfExperiments());
        assertTrue(interaction2.isOrganismsDiffer());
        verifyInteractor(interaction2.getInteractantOne(), null, "P12345", null, "EBI-123485");
        verifyInteractor(
                interaction2.getInteractantTwo(), "PRO_0000037566", "P27958", null, "EBI-126770");
    }

    private void verifyInteractor(
            Interactant interactor, String chainId, String acc, String gene, String intActId) {
        assertEquals(chainId, interactor.getChainId());
        if (acc == null) {
            assertNull(interactor.getUniProtKBAccession());
        } else {
            assertEquals(acc, interactor.getUniProtKBAccession().getValue());
        }
        assertEquals(gene, interactor.getGeneName());
        assertEquals(intActId, interactor.getIntActId());
    }

    @Test
    void test2() {

        String ccLineString =
                ("INTERACTION:\n"
                     + "P12345; Q9W1K5-1: CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n"
                     + "PRO_0000037566; O96017: CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n"
                     + "P12345; Q6ZWQ9: Myl12a; Xeno; NbExp=3; IntAct=EBI-372428, EBI-8034418;");

        InteractionComment comment = transformer.transform(CommentType.INTERACTION, ccLineString);
        assertNotNull(comment);
        assertEquals(3, comment.getInteractions().size());
        Interaction interaction1 = comment.getInteractions().get(0);
        assertEquals(1, interaction1.getNumberOfExperiments());
        assertFalse(interaction1.isOrganismsDiffer());
        verifyInteractor(interaction1.getInteractantOne(), null, "P12345", null, "EBI-133844");
        verifyInteractor(
                interaction1.getInteractantTwo(), null, "Q9W1K5-1", "CG11299", "EBI-212772");

        Interaction interaction2 = comment.getInteractions().get(1);
        assertEquals(4, interaction2.getNumberOfExperiments());
        assertFalse(interaction2.isOrganismsDiffer());
        verifyInteractor(
                interaction2.getInteractantOne(), "PRO_0000037566", null, null, "EBI-372428");
        verifyInteractor(interaction2.getInteractantTwo(), null, "O96017", "CHEK2", "EBI-1180783");

        Interaction interaction3 = comment.getInteractions().get(2);
        assertEquals(3, interaction3.getNumberOfExperiments());
        assertTrue(interaction3.isOrganismsDiffer());
        verifyInteractor(interaction3.getInteractantOne(), null, "P12345", null, "EBI-372428");
        verifyInteractor(interaction3.getInteractantTwo(), null, "Q6ZWQ9", "Myl12a", "EBI-8034418");
    }

    @Test
    void test3() {
        String ccLineString =
                ("P12345; Q9W1K5-1: CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n"
                        + "P12345-1; O96017: CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n"
                        + "P12345; Q6ZWQ9: Myl12a; Xeno; NbExp=3; IntAct=EBI-372428, EBI-8034418;");
        InteractionComment comment = transformer.transform(CommentType.INTERACTION, ccLineString);
        assertNotNull(comment);
        assertEquals(3, comment.getInteractions().size());
        Interaction interaction1 = comment.getInteractions().get(0);
        assertEquals(1, interaction1.getNumberOfExperiments());
        assertFalse(interaction1.isOrganismsDiffer());
        verifyInteractor(interaction1.getInteractantOne(), null, "P12345", null, "EBI-133844");
        verifyInteractor(
                interaction1.getInteractantTwo(), null, "Q9W1K5-1", "CG11299", "EBI-212772");

        Interaction interaction2 = comment.getInteractions().get(1);
        assertEquals(4, interaction2.getNumberOfExperiments());
        assertFalse(interaction2.isOrganismsDiffer());
        verifyInteractor(interaction2.getInteractantOne(), null, "P12345-1", null, "EBI-372428");
        verifyInteractor(interaction2.getInteractantTwo(), null, "O96017", "CHEK2", "EBI-1180783");

        Interaction interaction3 = comment.getInteractions().get(2);
        assertEquals(3, interaction3.getNumberOfExperiments());
        assertTrue(interaction3.isOrganismsDiffer());
        verifyInteractor(interaction3.getInteractantOne(), null, "P12345", null, "EBI-372428");
        verifyInteractor(interaction3.getInteractantTwo(), null, "Q6ZWQ9", "Myl12a", "EBI-8034418");
    }
}
