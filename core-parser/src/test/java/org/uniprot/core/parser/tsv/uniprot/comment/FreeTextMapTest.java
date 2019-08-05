package org.uniprot.core.parser.tsv.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.parser.tsv.uniprot.comment.FreeTextMap;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.FreeTextComment;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FreeTextMapTest {

    @Test
    void testFreeTextMappingInduction() {
        String freeTextLine = "CC   -!- INDUCTION: Cell cycle-regulated with highest activity in S phase.\n" +
                "CC       Moderately induced by DNA-damage. {ECO:0000269|PubMed:2199320}.";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(freeTextLine);

        List<FreeTextComment> freeTextComments = entry.getCommentByType(CommentType.INDUCTION);
        assertNotNull(entry);
        FreeTextMap freeTextMap = new FreeTextMap(freeTextComments,CommentType.INDUCTION);
        Map<String,String> mappedFreeText = freeTextMap.attributeValues();
        assertNotNull(mappedFreeText);
        String value = mappedFreeText.get("cc:induction");
        String expectedValue = "INDUCTION: Cell cycle-regulated with highest activity in S phase. Moderately " +
                "induced by DNA-damage. {ECO:0000269|PubMed:2199320}.";
        assertEquals(expectedValue,value);
    }


    @Test
    void testFreeTextSimilarityMapping() {
        String freeTextLine = "CC   -!- SIMILARITY: In the N-terminal section; belongs to the PMEI family.\n" +
                "CC       {ECO:0000305}.\n" +
                "CC   -!- SIMILARITY: In the C-terminal section; belongs to the\n" +
                "CC       pectinesterase family. {ECO:0000305}.";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(freeTextLine);

        List<FreeTextComment> freeTextComments = entry.getCommentByType(CommentType.SIMILARITY);
        assertNotNull(entry);
        FreeTextMap freeTextMap = new FreeTextMap(freeTextComments,CommentType.SIMILARITY);
        Map<String,String> mappedFreeText = freeTextMap.attributeValues();
        assertNotNull(mappedFreeText);
        String value = mappedFreeText.get("cc:similarity");
        String expectedValue = "SIMILARITY: In the N-terminal section; belongs to the PMEI family. {ECO:0000305}.; " +
                "SIMILARITY: In the C-terminal section; belongs to the pectinesterase family. {ECO:0000305}.";
        assertEquals(expectedValue,value);
    }
}