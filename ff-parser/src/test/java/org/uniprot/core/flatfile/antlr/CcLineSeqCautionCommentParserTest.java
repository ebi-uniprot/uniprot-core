package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.SequenceCaution;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.SequenceCautionObject;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;

class CcLineSeqCautionCommentParserTest {
    @Test
    void test1() {
        String lines =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAI24940.1; Type=Erroneous gene model prediction;\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(1, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "CAI24940.1",
                CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION,
                null);
    }

    private void verify(
            SequenceCautionObject obj,
            String seq,
            CcLineObject.SequenceCautionType type,
            String note) {
        assertEquals(seq, obj.getSequence());

        assertEquals(type, obj.getType());

        assertEquals(note, obj.getNote());
    }

    @Test
    void testAllFields() {
        String lines =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=AAG34697.1; Type=Erroneous termination; Note=Translated as Ser;\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(1, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "AAG34697.1",
                CcLineObject.SequenceCautionType.ERRONEOUS_TERMINATION,
                "Translated as Ser");
    }

    @Test
    void test2() {
        String lines =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;\n"
                        + "CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction;\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(2, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "CAI12537.1",
                CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION,
                null);
        verify(
                sc.getSequenceCautionObjects().get(1),
                "CAI39742.1",
                CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION,
                null);
    }

    @Test
    void test3() {
        String lines =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=AAA25676.1; Type=Frameshift;\n"
                        + "CC       Sequence=CAD59919.1; Type=Frameshift;\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(2, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "AAA25676.1",
                CcLineObject.SequenceCautionType.FRAMESHIFT,
                null);

        verify(
                sc.getSequenceCautionObjects().get(1),
                "CAD59919.1",
                CcLineObject.SequenceCautionType.FRAMESHIFT,
                null);
    }

    @Test
    void test4() {
        String lines =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=AAA85813.1; Type=Frameshift; Note=Frameshift correction allows the C-terminal sequence to be compatible with the results of mass spectrometry and X-ray crystallography;\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(1, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "AAA85813.1",
                CcLineObject.SequenceCautionType.FRAMESHIFT,
                "Frameshift correction allows the C-terminal sequence to be compatible with the results of mass spectrometry and X-ray crystallography");
    }

    @Test
    void test5() {
        String lines =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAA57511.1; Type=Frameshift; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(1, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "CAA57511.1",
                CcLineObject.SequenceCautionType.FRAMESHIFT,
                "The predicted gene.");
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sc.getSequenceCautionObjects().get(0))
                        .get(0));
        assertEquals(
                "ECO:0000313|Ensembl:ENSP00000409133",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sc.getSequenceCautionObjects().get(0))
                        .get(1));
    }

    @Test
    void testNoHeader() {
        String ccLineString =
                "SEQUENCE CAUTION:\n"
                        + "Sequence=CAA57511.1; Type=Frameshift; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineFormater formater = new CcLineFormater();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(1, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "CAA57511.1",
                CcLineObject.SequenceCautionType.FRAMESHIFT,
                "The predicted gene.");
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sc.getSequenceCautionObjects().get(0))
                        .get(0));
        assertEquals(
                "ECO:0000313|Ensembl:ENSP00000409133",
                obj.getEvidenceInfo()
                        .getEvidences()
                        .get(sc.getSequenceCautionObjects().get(0))
                        .get(1));
    }

    @Test
    void testPositionSeveral() {
        String lines =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAA39814.1; Type=Frameshift; Evidence={ECO:0000305};\n";
        UniprotLineParser<CcLineObject> parser =
                new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof SequenceCaution);
        SequenceCaution sc = (SequenceCaution) cc.getObject();
        assertEquals(1, sc.getSequenceCautionObjects().size());
        verify(
                sc.getSequenceCautionObjects().get(0),
                "CAA39814.1",
                CcLineObject.SequenceCautionType.FRAMESHIFT,
                null);
        CcLineConverter converter = new CcLineConverter(new HashMap<>(), new HashMap<>(), true);
        List<Comment> comments = converter.convert(obj);
        assertEquals(1, comments.size());
        assertEquals(CommentType.SEQUENCE_CAUTION, comments.get(0).getCommentType());
        SequenceCautionComment comment = (SequenceCautionComment) comments.get(0);
        assertEquals("CAA39814.1", comment.getSequence());
        assertEquals(
                org.uniprot.core.uniprot.comment.SequenceCautionType.FRAMESHIFT,
                comment.getSequenceCautionType());
    }
}
