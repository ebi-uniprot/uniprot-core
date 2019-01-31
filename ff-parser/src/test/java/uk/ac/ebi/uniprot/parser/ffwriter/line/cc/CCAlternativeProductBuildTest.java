package uk.ac.ebi.uniprot.parser.ffwriter.line.cc;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APIsoformBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.IsoformNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.cc.CCAPCommentLineBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CCAlternativeProductBuildTest extends CCBuildTestAbstr {
    CCAPCommentLineBuilder ccLineBuilder = new CCAPCommentLineBuilder();

    @Test
    public void TestNoEvidence() {
        String ccLine = "CC   -!- ALTERNATIVE PRODUCTS:\n" + "CC       Event=Alternative splicing; Named isoforms=6;\n"
                + "CC         Comment=Additional isoforms seem to exist.;\n" + "CC       Name=1; Synonyms=A;\n"
                + "CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                + "CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                + "CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
                + "CC         confirmation available.;\n" + "CC       Name=2;\n"
                + "CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                + "CC                                  VSP_000480, VSP_000481;\n"
                + "CC       Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10,\n"
                + "CC       Bim-AD, BimAD;\n"
                + "CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                + "CC       Name=4; Synonyms=B;\n"
                + "CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" + "CC       Name=5;\n"
                + "CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                + "CC         Note=No experimental confirmation available.;\n" + "CC       Name=6; Synonyms=D;\n"
                + "CC         IsoId=Q9V8R9-6; Sequence=Described;\n"
                + "CC         Note=No experimental confirmation available.;";

        String ccLineString = "ALTERNATIVE PRODUCTS:\n" + "Event=Alternative splicing; Named isoforms=6;\n"
                + "Comment=Additional isoforms seem to exist.;\n" + "Name=1; Synonyms=A;\n"
                + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T. No experimental confirmation available.;\n"
                + "Name=2;\n" + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n"
                + "Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10, Bim-AD, BimAD;\n"
                + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" + "Name=4; Synonyms=B;\n"
                + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" + "Name=5;\n"
                + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                + "Note=No experimental confirmation available.;\n" + "Name=6; Synonyms=D;\n"
                + "IsoId=Q9V8R9-6; Sequence=Described;\n" + "Note=No experimental confirmation available.;";

        String commentStr = "Additional isoforms seem to exist.";
        List<String> commentEvs = new ArrayList<>();
        Map<String, List<String>> commentNotes = new TreeMap<>();
        commentNotes.put(commentStr, commentEvs);
        APCommentBuilder builder = buildComment(Arrays.asList(new String[]{"Alternative splicing"}), commentNotes);

        List<APIsoform> isoforms = new ArrayList<>();

        // "CC Name=1; Synonyms=A;\n" +
        // "CC IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
        // "CC Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n" +
        // "CC sequence is in conflict in positions: 33:I->T. No experimental\n" +
        // "CC confirmation available;\n" +

        Map<String, List<String>> synonyms = new TreeMap<>();
        List<String> isoEv = new ArrayList<>();
        synonyms.put("A", isoEv);
        String note = "Does not exhibit APOBEC1 complementation activity. Ref.4 "
                + "sequence is in conflict in positions: 33:I->T. No experimental confirmation available.";
        List<String> noteEvidences = new ArrayList<>();
        List<String> isoIds = Arrays.asList(new String[]{"Q9V8R9-1"});
        List<String> seqIds = new ArrayList<>();
        List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvidences));

        isoforms.add(buildAPIsoform("1", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));

        // "CC Name=2;\n" +
        // "CC IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n" +
        // "CC VSP_000480, VSP_000481;\n" +

        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();

        isoIds = Arrays.asList(new String[]{"Q9V8R9-2"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000476", "VSP_000477", "VSP_000479", "VSP_000480", "VSP_000481"});
        notes = new ArrayList<>();
        //	notes.add(new AbstractMap.SimpleEntry<>(note, noteEvidences));
        isoforms.add(buildAPIsoform("2", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));
        // "CC Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10,\n" +
        // "CC BimAD, Bim-AD;\n" +
        // "CC IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        synonyms.put("BCL2-like 11 transcript variant 10", isoEv);
        synonyms.put("BimAD", isoEv);
        synonyms.put("Bim-AD", isoEv);
        isoIds = Arrays.asList(new String[]{"Q9V8R9-3"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000475", "VSP_000478", "VSP_000479"});
        notes = new ArrayList<>();

        isoforms.add(buildAPIsoform("Bim-alpha3", new ArrayList<String>(), synonyms, notes, isoIds,
                                    seqIds, IsoformSequenceStatus.DISPLAYED));

        // "CC Name=4; Synonyms=B;\n" +
        // "CC IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        synonyms.put("B", isoEv);
        isoIds = Arrays.asList(new String[]{"Q9V8R9-4"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000476", "VSP_000477", "VSP_000479"});
        notes = new ArrayList<>();
        isoforms.add(buildAPIsoform("4", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));

        // "CC Name=5;\n" +
        // "CC IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
        // "CC Note=No experimental confirmation available;\n" +

        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        // synonyms.put("B", isoEv);
        note = "No experimental confirmation available.";
        noteEvidences = new ArrayList<>();
        isoIds = Arrays.asList(new String[]{"Q9V8R9-5"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000474", "VSP_000478"});
        notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvidences));
        isoforms.add(buildAPIsoform("5", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));

        // "CC Name=6; Synonyms=D;\n" +
        // "CC IsoId=Q9V8R9-6; Sequence=EXTERNAL;\n" +
        // "CC Note=No experimental confirmation available;";

        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        synonyms.put("D", isoEv);
        note = "No experimental confirmation available.";
        noteEvidences = new ArrayList<>();
        isoIds = Arrays.asList(new String[]{"Q9V8R9-6"});
        seqIds = new ArrayList<>();
        // seqIds = Arrays.asList(new String[] {""});
        notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvidences));
        isoforms.add(buildAPIsoform("6", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DESCRIBED));
        builder.isoforms(isoforms);
        AlternativeProductsComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    public void TestEvidence1() {
        String ccLine = "CC   -!- ALTERNATIVE PRODUCTS:\n" + "CC       Event=Alternative splicing; Named isoforms=6;\n"
                + "CC         Comment=Additional isoforms seem to exist.\n"
                + "CC         {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}. Another\n"
                + "CC         additional isoforms also seem to exist.\n"
                + "CC         {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                + "CC       Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
                + "CC       {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                + "CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                + "CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                + "CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
                + "CC         confirmation available. {ECO:0000313|PDB:3OW2};\n" + "CC       Name=2;\n"
                + "CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                + "CC                                  VSP_000480, VSP_000481;\n"
                + "CC       Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                + "CC       ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
                + "CC       {ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
                + "CC       {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                + "CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                + "CC       Name=4; Synonyms=B;\n"
                + "CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                + "CC       Name=5;\n"
                + "CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                + "CC         Note=No experimental confirmation available.\n"
                + "CC         {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.\n"
                + "CC         Another no experimental confirmation also available.\n"
                + "CC         {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                + "CC       Name=6; Synonyms=D;\n" + "CC         IsoId=Q9V8R9-6; Sequence=Described;\n"
                + "CC         Note=No experimental confirmation.;";

        String ccLineString = "ALTERNATIVE PRODUCTS:\n" + "Event=Alternative splicing; Named isoforms=6;\n"
                + "Comment=Additional isoforms seem to exist.. Another additional isoforms also seem to exist.;\n"
                + "Name=1; Synonyms=A;\n" + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
                + " No experimental confirmation available.;\n" + "Name=2;\n"
                + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n"
                + "Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10, Bim-AD, BimAD;\n"
                + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" + "Name=4; Synonyms=B;\n"
                + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" + "Name=5;\n"
                + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                + "Note=No experimental confirmation available.. Another no experimental confirmation also available.;\n"
                + "Name=6; Synonyms=D;\n" + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                + "Note=No experimental confirmation.;";

        String ccLineStringEvidence = "ALTERNATIVE PRODUCTS:\n" + "Event=Alternative splicing; Named isoforms=6;\n"
                + "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}."
                + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                + "Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
                + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n" + "Name=2;\n"
                + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n"
                + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
                + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" + "Name=4; Synonyms=B;\n"
                + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" + "Name=5;\n"
                + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                + "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}."
                + " Another no experimental confirmation also available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                + "Name=6; Synonyms=D;\n" + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                + "Note=No experimental confirmation.;";

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";
        String commentStr = "Additional isoforms seem to exist.";
        String commentStr2 = "Another additional isoforms also seem to exist.";
        List<String> commentEvs = new ArrayList<>();
        commentEvs.add(ev3);
        commentEvs.add(ev2);

        Map<String, List<String>> commentNotes = new TreeMap<>();
        commentNotes.put(commentStr, commentEvs);
        commentNotes.put(commentStr2, commentEvs);
        APCommentBuilder builder = buildComment(Arrays.asList(new String[]{"Alternative splicing"}), commentNotes);


        List<APIsoform> isoforms = new ArrayList<>();

        // "CC Name=1; Synonyms=A;\n" +
        // "CC IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
        // "CC Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n" +
        // "CC sequence is in conflict in positions: 33:I->T. No experimental\n" +
        // "CC confirmation available;\n" +
        String isoName = "1";
        List<String> isoNameEv = new ArrayList<>();
        isoNameEv.add(ev1);

        Map<String, List<String>> synonyms = new TreeMap<>();
        List<String> isoEv = new ArrayList<>();
        isoEv.add(ev4);
        isoEv.add(ev5);
        synonyms.put("A", isoEv);
        String note = "Does not exhibit APOBEC1 complementation activity. Ref.4 "
                + "sequence is in conflict in positions: 33:I->T. No experimental confirmation available.";
        List<String> noteEvidences = new ArrayList<>();
        noteEvidences.add(ev4);
        List<String> isoIds = Arrays.asList(new String[]{"Q9V8R9-1"});
        List<String> seqIds = new ArrayList<>();
        List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvidences));

        isoforms.add(buildAPIsoform("1", isoNameEv, synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));

        // "CC Name=2;\n" +
        // "CC IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n" +
        // "CC VSP_000480, VSP_000481;\n" +

        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();

        note = "";
        noteEvidences = new ArrayList<>();
        isoIds = Arrays.asList(new String[]{"Q9V8R9-2"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000476", "VSP_000477", "VSP_000479", "VSP_000480", "VSP_000481"});
        notes = new ArrayList<>();
        isoforms.add(buildAPIsoform("2", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));


        // "CC Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10,\n" +
        // "CC BimAD, Bim-AD;\n" +
        // "CC IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
        isoName = "Bim-alpha3";
        isoNameEv = new ArrayList<>();
        isoNameEv.add(ev4);
        isoNameEv.add(ev5);

        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        isoEv.add(ev1);
        synonyms.put("BCL2-like 11 transcript variant 10", isoEv);
        isoEv = new ArrayList<>();
        isoEv.add(ev5);
        synonyms.put("Bim-AD", isoEv);
        isoEv = new ArrayList<>();
        isoEv.add(ev4);
        synonyms.put("BimAD", isoEv);

        note = "";
        noteEvidences = new ArrayList<>();
        isoIds = Arrays.asList(new String[]{"Q9V8R9-3"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000475", "VSP_000478", "VSP_000479"});


        notes = new ArrayList<>();
        isoforms.add(buildAPIsoform(isoName, isoNameEv, synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));

        // "CC Name=4; Synonyms=B;\n" +
        // "CC IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        synonyms.put("B", isoEv);
        note = "";
        noteEvidences = new ArrayList<>();
        isoIds = Arrays.asList(new String[]{"Q9V8R9-4"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000476", "VSP_000477", "VSP_000479"});
        notes = new ArrayList<>();

        isoforms.add(buildAPIsoform("4", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));

        // "CC Name=5;\n" +
        // "CC IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
        // "CC Note=No experimental confirmation available;\n" +

        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        // synonyms.put("B", isoEv);
        note = "No experimental confirmation available.";
        noteEvidences = new ArrayList<>();
        noteEvidences.add(ev1);
        noteEvidences.add(ev2);
        String noteStr2 = "Another no experimental confirmation also available.";
        isoIds = Arrays.asList(new String[]{"Q9V8R9-5"});
        seqIds = new ArrayList<>();
        seqIds = Arrays.asList(new String[]{"VSP_000474", "VSP_000478"});
        notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvidences));
        notes.add(new AbstractMap.SimpleEntry<>(noteStr2, noteEvidences));
        isoforms.add(buildAPIsoform("5", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DISPLAYED));

        // "CC Name=6; Synonyms=D;\n" +
        // "CC IsoId=Q9V8R9-6; Sequence=EXTERNAL;\n" +
        // "CC Note=No experimental confirmation available;";

        synonyms = new TreeMap<>();
        isoEv = new ArrayList<>();
        synonyms.put("D", isoEv);
        note = "No experimental confirmation.";
        noteEvidences = new ArrayList<>();
        isoIds = Arrays.asList(new String[]{"Q9V8R9-6"});
        seqIds = new ArrayList<>();
        notes = new ArrayList<>();
        notes.add(new AbstractMap.SimpleEntry<>(note, noteEvidences));
        isoforms.add(buildAPIsoform("6", new ArrayList<String>(), synonyms, notes, isoIds, seqIds,
                                    IsoformSequenceStatus.DESCRIBED));


        builder.isoforms(isoforms);
        AlternativeProductsComment comment = builder.build();
        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
        doTestStringEv(ccLineStringEvidence, comment);
    }

    private APCommentBuilder buildComment(List<String> events,
                                          Map<String, List<String>> notes) {
        APCommentBuilder builder = new APCommentBuilder();
        List<APEventType> apEvents = events.stream().map(val -> APEventType.typeOf(val))
                .collect(Collectors.toList());
        builder.events(apEvents);
        List<EvidencedValue> evidencedValues =
                notes.entrySet()
                        .stream().map(entry -> createEvidencedValue(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
        builder.note(new NoteBuilder(evidencedValues).build());

        return builder;
    }


    private APIsoform buildAPIsoform(String name, List<String> nameEvs, Map<String, List<String>> synonyms,
                                     List<Map.Entry<String, List<String>>> notes, List<String> isoformIdsStr, List<String> seqIds,
                                     IsoformSequenceStatus seqStatus) {
        APIsoformBuilder builder = new APIsoformBuilder();
        builder.name(new IsoformNameBuilder(name, createEvidence(nameEvs)).build());

        List<IsoformName> isoSynoyms = synonyms.entrySet().stream()
                .map(entry -> new IsoformNameBuilder(entry.getKey(), createEvidence(entry.getValue())).build())
                .collect(Collectors.toList());
        builder.synonyms(isoSynoyms);
        if (!notes.isEmpty()) {
            List<EvidencedValue> evidencedValues =
                    notes
                            .stream().map(entry -> createEvidencedValue(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList());
            builder.note(new NoteBuilder(evidencedValues).build());
        }


        builder.ids(isoformIdsStr);
        builder.sequenceIds(seqIds);
        builder.sequenceStatus(seqStatus);

        return builder.build();
    }

    private void doTest(String ccLine, AlternativeProductsComment comment) {
        FFLine ffLine = ccLineBuilder.buildWithEvidence(comment);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println("\n");
        // System.out.println(ccLine);
        assertEquals(ccLine, resultString);
    }

    private void doTestString(String ccLine, AlternativeProductsComment comment) {
        String value = ccLineBuilder.buildString(comment);

        System.out.println(value);
        assertEquals(ccLine, value);

    }

    private void doTestStringEv(String ccLine, AlternativeProductsComment comment) {
        String value = ccLineBuilder.buildStringWithEvidence(comment);

        System.out.println(value);
        assertEquals(ccLine, value);

    }
}
