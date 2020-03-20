package org.uniprot.core.parser.tsv.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.RnaEditingComment;

class RnaEditingMapTest {

    @Test
    void testRnaEditingMapping() {
        String rnaEditingLine =
                "CC   -!- RNA EDITING: Modified_positions=471 {ECO:0000269|PubMed:10880477,\n"
                        + "CC       ECO:0000269|PubMed:10966106}, 1455 {ECO:0000269|PubMed:10880477,\n"
                        + "CC       ECO:0000269|PubMed:10966106}, 1587 {ECO:0000269|PubMed:10880477,\n"
                        + "CC       ECO:0000269|PubMed:10966106, ECO:0000269|PubMed:2550145};\n"
                        + "CC       Note=Partially edited. Further sites are edited by Adar. Positions\n"
                        + "CC       1455 and 1587 show minimal editing from embryos through to third\n"
                        + "CC       larval instar, then a 40-fold increase at pupation. Position 471\n"
                        + "CC       has slightly higher levels during early development with only a\n"
                        + "CC       four-fold increase at pupation.;";

        UniProtKBEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(rnaEditingLine);

        List<RnaEditingComment> rnaEditingComments =
                entry.getCommentsByType(CommentType.RNA_EDITING);
        assertNotNull(entry);
        RnaEditingMap RnaEditingMap = new RnaEditingMap(rnaEditingComments);
        Map<String, String> mappedRnaEditing = RnaEditingMap.attributeValues();
        assertNotNull(mappedRnaEditing);
        String value = mappedRnaEditing.get("cc_rna_editing");
        String expectedValue =
                "RNA EDITING: Modified_positions=471 "
                        + "{ECO:0000269|PubMed:10880477, ECO:0000269|PubMed:10966106}, "
                        + "1455 {ECO:0000269|PubMed:10880477, ECO:0000269|PubMed:10966106}, "
                        + "1587 {ECO:0000269|PubMed:10880477, ECO:0000269|PubMed:10966106, ECO:0000269|PubMed:2550145}; "
                        + "Note=Partially edited. Further sites are edited by Adar. "
                        + "Positions 1455 and 1587 show minimal editing from embryos through to third larval instar, "
                        + "then a 40-fold increase at pupation. Position 471 has slightly higher levels during early "
                        + "development with only a four-fold increase at pupation.;";
        assertEquals(expectedValue, value);
    }
}
