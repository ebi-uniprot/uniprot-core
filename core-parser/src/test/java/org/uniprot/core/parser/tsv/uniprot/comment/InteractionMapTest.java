package org.uniprot.core.parser.tsv.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.InteractionComment;

import java.util.List;
import java.util.Map;

class InteractionMapTest {

    @Test
    void testInteractionMapping() {
        String interactionLine =
                "CC   -!- INTERACTION:\n"
                    + "CC       P12345; P03259; Xeno; NbExp=2; IntAct=EBI-475687, EBI-6947456;\n"
                    + "CC       P12345; P49711: CTCF; NbExp=2; IntAct=EBI-475687, EBI-932887;\n"
                    + "CC       P12345; Q8JSK4: E1A; Xeno; NbExp=2; IntAct=EBI-475687,"
                    + " EBI-7453955;\n"
                    + "CC       P12345; Q9UJU5: FOXD3; NbExp=2; IntAct=EBI-475687, EBI-475674;\n"
                    + "CC       P12345; P63158: Hmgb1; Xeno; NbExp=3; IntAct=EBI-475687,"
                    + " EBI-6665811;\n"
                    + "CC       P12345; O95983: MBD3; NbExp=3; IntAct=EBI-475687, EBI-1783068;\n"
                    + "CC       P12345; O00308: WWP2; NbExp=4; IntAct=EBI-475687, EBI-743923;";

        UniProtKBEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(interactionLine);

        List<InteractionComment> interactionComments =
                entry.getCommentsByType(CommentType.INTERACTION);
        assertNotNull(entry);
        InteractionMap interactionMap = new InteractionMap(interactionComments);
        Map<String, String> mappedInteraction = interactionMap.attributeValues();
        assertNotNull(mappedInteraction);
        String value = mappedInteraction.get("cc_interaction");
        String expectedValue = "P03259; P49711; Q8JSK4; Q9UJU5; P63158; O95983; O00308";
        assertEquals(expectedValue, value);
    }

    @Test
    void testWithItSelfInteractionMapping() {
        String interactionLine =
                "CC   -!- INTERACTION:\n"
                        + "CC       P12345; P12345-2; NbExp=6; IntAct=EBI-15234, EBI-15234;\n"
                        + "CC       P12345; P09938: RNR2; NbExp=5; IntAct=EBI-15234, EBI-15240;\n"
                        + "CC       P12345; P49723: RNR4; NbExp=5; IntAct=EBI-15234, EBI-15251;\n"
                        + "CC       P12345; Q04964: SML1; NbExp=4; IntAct=EBI-15234, EBI-27834;";

        UniProtKBEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(interactionLine);

        List<InteractionComment> interactionComments =
                entry.getCommentsByType(CommentType.INTERACTION);
        assertNotNull(entry);
        InteractionMap interactionMap = new InteractionMap(interactionComments);
        Map<String, String> mappedInteraction = interactionMap.attributeValues();
        assertNotNull(mappedInteraction);
        String value = mappedInteraction.get("cc_interaction");
        String expectedValue = "P12345-2; P09938; P49723; Q04964";
        assertEquals(expectedValue, value);
    }
}
