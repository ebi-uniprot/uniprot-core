package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SequenceCautionMapTest {

    @Test
    void testSingleSequenceCaution() {
        String sequenceCautionLine = "CC   -!- SEQUENCE CAUTION:\n" +
                "CC       Sequence=BAB43866.1; Type=Miscellaneous discrepancy; Note=Chimeric cDNA. " +
                "It is a chimera between Dox-A3 and PPO2.; Evidence={ECO:0000305};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(sequenceCautionLine);

        List<SequenceCautionComment> sequenceCautionComments = entry.getCommentByType(CommentType.SEQUENCE_CAUTION);
        assertNotNull(entry);
        SequenceCautionMap sequenceCautionMap = new SequenceCautionMap(sequenceCautionComments);
        Map<String,String> mappedSequenceCaution = sequenceCautionMap.attributeValues();
        assertNotNull(mappedSequenceCaution);
        String value = mappedSequenceCaution.get("cc:sequence_caution");
        String expectedValue = "SEQUENCE CAUTION:  Sequence=BAB43866.1; Type=MISCELLANEOUS_DISCREPANCY; Note=Chimeric cDNA. " +
                "It is a chimera between Dox-A3 and PPO2.; Evidence={ECO:0000305}";
        assertEquals(expectedValue,value);
    }


    @Test
    void testMultipleSequenceCaution() {
        String sequenceCautionLine = "CC   -!- SEQUENCE CAUTION:\n" +
                "CC       Sequence=CAB59730.1; Type=Frameshift; Positions=76, 138; Evidence={ECO:0000305};\n" +
                "CC   -!- SEQUENCE CAUTION:\n" +
                "CC       Sequence=AAA42785.1; Type=Erroneous gene model prediction; Evidence={ECO:0000305};\n" +
                "CC   -!- SEQUENCE CAUTION:\n" +
                "CC       Sequence=CAH10679.1; Type=Erroneous termination; Positions=431; Note=Translated as Trp.; Evidence={ECO:0000305};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(sequenceCautionLine);

        List<SequenceCautionComment> sequenceCautionComments = entry.getCommentByType(CommentType.SEQUENCE_CAUTION);
        assertNotNull(entry);
        SequenceCautionMap sequenceCautionMap = new SequenceCautionMap(sequenceCautionComments);
        Map<String,String> mappedSequenceCaution = sequenceCautionMap.attributeValues();
        assertNotNull(mappedSequenceCaution);
        String value = mappedSequenceCaution.get("cc:sequence_caution");
        String expectedValue = "SEQUENCE CAUTION:  Sequence=CAB59730.1; Type=FRAMESHIFT; Positions=76, 138; Evidence={ECO:0000305};  " +
                "Sequence=AAA42785.1; Type=ERRONEOUS_PREDICTION; Evidence={ECO:0000305};  " +
                "Sequence=CAH10679.1; Type=ERRONEOUS_TERMIINATION; Positions=431; Note=Translated as Trp.; Evidence={ECO:0000305}";
        assertEquals(expectedValue,value);
    }

}