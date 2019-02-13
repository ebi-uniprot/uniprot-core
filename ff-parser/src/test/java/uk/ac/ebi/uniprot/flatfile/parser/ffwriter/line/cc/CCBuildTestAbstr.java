package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line.cc;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.cv.disease.impl.DiseaseServiceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidencedValueBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CCLineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CCLineBuilderFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

public abstract class CCBuildTestAbstr {
    CCLineBuilder builder = new CCLineBuilder();

    <T extends Comment> void doTest(String ftLine, T comment) {
        FFLineBuilder<Comment> builder = CCLineBuilderFactory
                .create(comment);

        FFLine ffLine = builder.buildWithEvidence(comment);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(ftLine);
        assertEquals(ftLine, resultString);
    }

    <T extends Comment> void doTestString(String ccLine, T comment) {
        FFLineBuilder<T> builder = CCLineBuilderFactory
                .create(comment);
        String value = builder.buildString(comment);

        System.out.println(value);
        assertEquals(ccLine, value);
    }

    <T extends Comment> void doTestStringEv(String ccLine, T comment) {
        FFLineBuilder<Comment> builder = CCLineBuilderFactory
                .create(comment);
        String value = builder.buildStringWithEvidence(comment);

        System.out.println(value);
        assertEquals(ccLine, value);
    }

    protected void doTest(String ccLine) {
        UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(ccLine + "\n");
        CcLineConverter converter = new CcLineConverter(new DiseaseServiceImpl(""));
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
        if (Strings.isNullOrEmpty(note))
            return null;
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(createEvidencedValue(note, evids));
        return new NoteBuilder(evidencedValues).build();

    }


    protected Note buildNote(List<Map.Entry<String, List<String>>> notes) {
        if (!notes.isEmpty()) {
            List<EvidencedValue> evidencedValues =
                    notes
                            .stream().map(entry -> createEvidencedValue(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList());
            return new NoteBuilder(evidencedValues).build();
        }
        return null;

    }
}
