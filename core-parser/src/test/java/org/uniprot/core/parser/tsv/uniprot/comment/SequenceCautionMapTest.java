package org.uniprot.core.parser.tsv.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtkbEntry;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.SequenceCautionComment;

class SequenceCautionMapTest {

    @Test
    void testSingleSequenceCaution() {
        String sequenceCautionLine =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=BAB43866.1; Type=Miscellaneous discrepancy; Note=Chimeric cDNA. "
                        + "It is a chimera between Dox-A3 and PPO2.; Evidence={ECO:0000305};";

        UniProtkbEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(sequenceCautionLine);

        List<SequenceCautionComment> sequenceCautionComments =
                entry.getCommentsByType(CommentType.SEQUENCE_CAUTION);
        assertNotNull(entry);
        SequenceCautionMap sequenceCautionMap = new SequenceCautionMap(sequenceCautionComments);
        Map<String, String> mappedSequenceCaution = sequenceCautionMap.attributeValues();
        assertNotNull(mappedSequenceCaution);
        String value = mappedSequenceCaution.get("cc_sequence_caution");
        System.out.println(value);
        String expectedValue =
                "SEQUENCE CAUTION:  Sequence=BAB43866.1; Type=Miscellaneous discrepancy; Note=Chimeric cDNA. "
                        + "It is a chimera between Dox-A3 and PPO2.; Evidence={ECO:0000305};";
        assertEquals(expectedValue, value);
    }

    @Test
    void testMultipleSequenceCaution() {
        String sequenceCautionLine =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAB59730.1; Type=Frameshift; Evidence={ECO:0000305};\n"
                        + "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=AAA42785.1; Type=Erroneous gene model prediction; Evidence={ECO:0000305};\n"
                        + "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAH10679.1; Type=Erroneous termination; Note=Translated as Trp.; Evidence={ECO:0000305};";

        UniProtkbEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(sequenceCautionLine);

        List<SequenceCautionComment> sequenceCautionComments =
                entry.getCommentsByType(CommentType.SEQUENCE_CAUTION);
        assertNotNull(entry);
        SequenceCautionMap sequenceCautionMap = new SequenceCautionMap(sequenceCautionComments);
        Map<String, String> mappedSequenceCaution = sequenceCautionMap.attributeValues();
        assertNotNull(mappedSequenceCaution);
        String value = mappedSequenceCaution.get("cc_sequence_caution");
        System.out.println(value);
        String expectedValue =
                "SEQUENCE CAUTION:  Sequence=CAB59730.1; Type=Frameshift; Evidence={ECO:0000305};  "
                        + "Sequence=AAA42785.1; Type=Erroneous gene model prediction; Evidence={ECO:0000305};  "
                        + "Sequence=CAH10679.1; Type=Erroneous termination; Note=Translated as Trp.; Evidence={ECO:0000305};";
        assertEquals(expectedValue, value);
    }
}
