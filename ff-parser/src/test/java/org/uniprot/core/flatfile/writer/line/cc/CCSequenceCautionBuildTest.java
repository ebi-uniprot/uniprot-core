package org.uniprot.core.flatfile.writer.line.cc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CCSequenceCautionCommentLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.builder.SequenceCautionCommentBuilder;

class CCSequenceCautionBuildTest extends CCBuildTestAbstr {
    private CCSequenceCautionCommentLineBuilder builder = new CCSequenceCautionCommentLineBuilder();

    @Test
    void testSequenceCauction() {
        String ccLine =
                ("CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAA57511.1; Type=Frameshift; Note=The predicted gene.;");
        String ccLineString =
                ("SEQUENCE CAUTION:\n"
                        + "Sequence=CAA57511.1; Type=Frameshift; Note=The predicted gene.;");
        String sequence = "CAA57511.1";
        String note = "The predicted gene.";
        List<String> evs = new ArrayList<>();
     

        SequenceCautionComment comment =
                buildComment(sequence, SequenceCautionType.FRAMESHIFT, note, evs);
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    void testSequenceCauctionWithEvidence() {
        String ccLine =
                ("CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAA57511.1; Type=Frameshift; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};");
        String ccLineString =
                ("SEQUENCE CAUTION:\n"
                        + "Sequence=CAA57511.1; Type=Frameshift; Note=The predicted gene.;");
        String ccLineStringEvidence =
                ("SEQUENCE CAUTION:\n"
                        + "Sequence=CAA57511.1; Type=Frameshift; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};");

        String sequence = "CAA57511.1";
        String note = "The predicted gene.";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
        evs.add("ECO:0000313|Ensembl:ENSP00000409133");

        SequenceCautionComment comment =
                buildComment(sequence, SequenceCautionType.FRAMESHIFT, note, evs);
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    @Test
    void testSequenceCauction2() {
        String ccLine =
                ("CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.;");
        String sequence = "CAA57511.1";
        String note = "The predicted gene.";
        List<String> evs = new ArrayList<>();


        SequenceCautionComment comment =
                buildComment(
                        sequence, SequenceCautionType.ERRONEOUS_PREDICTION, note, evs);
        doTest(ccLine, comment);
    }

    @Test
    void testSequenceCauction2WithEvidence() {
        String ccLine =
                ("CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205};");
        String ccLineStringEvidence =
                ("SEQUENCE CAUTION:\n"
                        + "Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205};");
        String ccLineString =
                ("SEQUENCE CAUTION:\n"
                        + "Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.;");

        String sequence = "CAA57511.1";
        String note = "The predicted gene.";
        List<String> evs = new ArrayList<>();
        evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
       

        SequenceCautionComment comment =
                buildComment(
                        sequence, SequenceCautionType.ERRONEOUS_PREDICTION, note, evs);
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        // doTestStringEv(ccLineStringEvidence, comment);
    }

    SequenceCautionComment buildComment(
            String sequence,
            SequenceCautionType type,
            String note,
            List<String> evs) {

        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();

        builder.sequenceCautionType(type)
                .sequence(sequence)
                .note(note)
                .evidences(createEvidence(evs));

        return builder.build();
    }

    private void doTest(String ccLine, SequenceCautionComment comment) {
        FFLine ffLine = builder.buildWithEvidence(comment);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(ccLine);
        assertEquals(ccLine, resultString);
    }

    private void doTestString(String ccLine, SequenceCautionComment comment) {
        String value = builder.buildString(comment);

        System.out.println(value);
        assertEquals(ccLine, value);
    }

    private void doTestStringEv(String ccLine, SequenceCautionComment comment) {
        String value = builder.buildStringWithEvidence(comment);

        System.out.println(value);
        assertEquals(ccLine, value);
    }
}
