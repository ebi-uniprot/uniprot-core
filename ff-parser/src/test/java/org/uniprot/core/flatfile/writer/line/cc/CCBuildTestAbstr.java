package org.uniprot.core.flatfile.writer.line.cc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CCLineBuilder;
import org.uniprot.core.flatfile.parser.impl.cc.CCLineBuilderFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;

import com.google.common.base.Strings;

abstract class CCBuildTestAbstr {
    CCLineBuilder builder = new CCLineBuilder();

    <T extends Comment> void doTest(String ftLine, T comment) {
        FFLineBuilder<Comment> builder = CCLineBuilderFactory.create(comment);

        FFLine ffLine = builder.buildWithEvidence(comment);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(ftLine);
        assertEquals(ftLine, resultString);
    }

    <T extends Comment> void doTestString(String ccLine, T comment) {
        FFLineBuilder<T> builder = CCLineBuilderFactory.create(comment);
        String value = builder.buildString(comment);

        System.out.println(value);
        assertEquals(ccLine, value);
    }

    <T extends Comment> void doTestStringEv(String ccLine, T comment) {
        FFLineBuilder<Comment> builder = CCLineBuilderFactory.create(comment);
        String value = builder.buildStringWithEvidence(comment);

        System.out.println(value);
        assertEquals(ccLine, value);
    }

    protected void doTest(String ccLine) {
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(ccLine + "\n");
        CcLineConverter converter = new CcLineConverter(new HashMap<>(), new HashMap<>(), true);
        List<Comment> comments = converter.convert(obj);
        FFLine ffLine = builder.buildWithEvidence(comments);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(ccLine);
        assertEquals(ccLine, resultString);
        System.out.println(builder.buildString(comments));
    }

    protected List<Evidence> createEvidence(List<String> evIds) {

        return evIds.stream().map(val -> parseEvidenceLine(val)).collect(Collectors.toList());
    }

    protected EvidencedValue createEvidencedValue(String val, List<String> evIds) {
        return new EvidencedValueBuilder(val, createEvidence(evIds)).build();
    }

    protected Note buildNote(String note, List<String> evids) {
        if (Strings.isNullOrEmpty(note)) return null;
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(createEvidencedValue(note, evids));
        return new NoteBuilder(evidencedValues).build();
    }

    protected Note buildNote(List<Map.Entry<String, List<String>>> notes) {
        if (!notes.isEmpty()) {
            List<EvidencedValue> evidencedValues =
                    notes.stream()
                            .map(entry -> createEvidencedValue(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList());
            return new NoteBuilder(evidencedValues).build();
        }
        return null;
    }
}
