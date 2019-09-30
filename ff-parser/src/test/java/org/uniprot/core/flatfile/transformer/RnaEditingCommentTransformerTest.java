package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.RnaEditingComment;

class RnaEditingCommentTransformerTest {
    private final RnaEditingCommentTransformer transformer = new RnaEditingCommentTransformer();

    @Test
    void testQ9W4K2() {
        String val =
                "Modified_positions=572 {ECO:0000269|PubMed:17018572, ECO:0000269|Ref.3};"
                        + " Note=Partially edited. Target of Adar. {ECO:0000269|PubMed:17018572};";

        RnaEditingComment comment = transformer.transform(val);
        assertNotNull(comment);
        assertEquals(1, comment.getPositions().size());
        assertEquals("572", comment.getPositions().get(0).getPosition());
        assertEquals(2, comment.getPositions().get(0).getEvidences().size());
        assertEquals(1, comment.getNote().getTexts().size());
        assertEquals(
                "Partially edited. Target of Adar", comment.getNote().getTexts().get(0).getValue());
        assertEquals(1, comment.getNote().getTexts().get(0).getEvidences().size());
    }

    @Test
    void testQ85BW1() {
        String val =
                "RNA EDITING: Modified_positions=46 {ECO:0000269|PubMed:12527781, ECO:0000269|PubMed:12711687}"
                        + ", 1052 {ECO:0000269|PubMed:12527781, ECO:0000269|PubMed:12711687};"
                        + " Note=The nonsense codons at positions 46, 421, 973, 984 and 1048 are modified to sense codons.;";
        RnaEditingComment comment = transformer.transform(val);
        assertNotNull(comment);
        assertEquals(2, comment.getPositions().size());
        assertEquals("46", comment.getPositions().get(0).getPosition());
        assertEquals(2, comment.getPositions().get(0).getEvidences().size());
        assertEquals(1, comment.getNote().getTexts().size());
        assertEquals(
                "The nonsense codons at positions 46, 421, 973, 984 and 1048 are modified to sense codons",
                comment.getNote().getTexts().get(0).getValue());
        assertEquals(0, comment.getNote().getTexts().get(0).getEvidences().size());
    }
}
