package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InteractionMapTest {


    @Test
    void testInteractionMapping() {
        String interactionLine = "CC   -!- INTERACTION:\n" +
                "CC       P03259:- (xeno); NbExp=2; IntAct=EBI-475687, EBI-6947456;\n" +
                "CC       P49711:CTCF; NbExp=2; IntAct=EBI-475687, EBI-932887;\n" +
                "CC       Q8JSK4:E1A (xeno); NbExp=2; IntAct=EBI-475687, EBI-7453955;\n" +
                "CC       Q9UJU5:FOXD3; NbExp=2; IntAct=EBI-475687, EBI-475674;\n" +
                "CC       P63158:Hmgb1 (xeno); NbExp=3; IntAct=EBI-475687, EBI-6665811;\n" +
                "CC       O95983:MBD3; NbExp=3; IntAct=EBI-475687, EBI-1783068;\n" +
                "CC       O00308:WWP2; NbExp=4; IntAct=EBI-475687, EBI-743923;";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(interactionLine);

        List<InteractionComment> interactionComments = entry.getCommentByType(CommentType.INTERACTION);
        assertNotNull(entry);
        InteractionMap interactionMap = new InteractionMap(interactionComments);
        Map<String,String> mappedInteraction = interactionMap.attributeValues();
        assertNotNull(mappedInteraction);
        String value = mappedInteraction.get("cc:interaction");
        String expectedValue = "P03259; P49711; Q8JSK4; Q9UJU5; P63158; O95983; O00308";
        assertEquals(expectedValue,value);
    }

    @Test
    void testWithItSelfInteractionMapping(){
        String interactionLine ="CC   -!- INTERACTION:\n" +
                "CC       Self; NbExp=6; IntAct=EBI-15234, EBI-15234;\n" +
                "CC       P09938:RNR2; NbExp=5; IntAct=EBI-15234, EBI-15240;\n" +
                "CC       P49723:RNR4; NbExp=5; IntAct=EBI-15234, EBI-15251;\n" +
                "CC       Q04964:SML1; NbExp=4; IntAct=EBI-15234, EBI-27834;";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(interactionLine);

        List<InteractionComment> interactionComments = entry.getCommentByType(CommentType.INTERACTION);
        assertNotNull(entry);
        InteractionMap interactionMap = new InteractionMap(interactionComments);
        Map<String,String> mappedInteraction = interactionMap.attributeValues();
        assertNotNull(mappedInteraction);
        String value = mappedInteraction.get("cc:interaction");
        String expectedValue = "Itself; P09938; P49723; Q04964";
        assertEquals(expectedValue,value);
    }
}