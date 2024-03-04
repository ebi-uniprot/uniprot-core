package org.uniprot.core.flatfile.writer.line.rlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.*;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.impl.RLineBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.ReferenceCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBReferenceBuilder;
import org.uniprot.cv.evidence.EvidenceHelper;

class RLineBuildTest {
    /*
     * "RN   [1]\n" + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n" +
     * "RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;\n" +
     * "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;\n" +
     * "RT   \"Comparative genomic analyses of frog virus 3, type species of the\n"
     * + "RT   genus Ranavirus (family Iridoviridae).\";\n" +
     * "RL   Virology 323:70-84(2004).\n" +
     */
    RLineBuilder builder = new RLineBuilder();

    @Test
    void testJournalArticle() {
        String rlines =
                "RN   [1]\n"
                        + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n"
                        + "RC   PLASMID=pSd11_G1246, pSd12_G1263, pSd13_G1271, pSd2_G1252,"
                        + " pSd3_G1281,\n"
                        + "RC   pSd4_G1190, and pSd5_G1213;\n"
                        + "RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;\n"
                        + "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;\n"
                        + "RT   \"Comparative genomic analyses of frog virus 3, type species of the"
                        + " genus\n"
                        + "RT   Ranavirus (family Iridoviridae).\";\n"
                        + "RL   Virology 323:70-84(2004).";

        JournalArticleBuilder jaBuilder = new JournalArticleBuilder();
        List<String> authors =
                Arrays.asList(
                        new String[] {
                            "Tan W.G.", "Barkman T.J.", "Gregory Chinchar V.", "Essani K."
                        });
        jaBuilder.authorsSet(authors);

        String title =
                "Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus"
                        + " (family Iridoviridae).";
        jaBuilder.title(title);
        jaBuilder.citationCrossReferencesSet(
                buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", null));
        jaBuilder
                .journalName("Virology")
                .firstPage("70")
                .lastPage("84")
                .volume("323")
                .publicationDate("2004");

        List<String> plasmids =
                new ArrayList<>(
                        Arrays.asList(
                                new String[] {
                                    "pSd11_G1246",
                                    "pSd12_G1263",
                                    "pSd13_G1271",
                                    "pSd2_G1252",
                                    "pSd3_G1281",
                                    "pSd4_G1190",
                                    "pSd5_G1213"
                                }));

        List<ReferenceComment> referenceComments =
                buildReferenceComments(plasmids, ReferenceCommentType.PLASMID);

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(1)
                        .citation(jaBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .referenceCommentsSet(referenceComments)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testJournalArticleEvidence() {
        String rlines =
                "RN   [1] {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6,"
                        + " ECO:0000313|EMBL:BAG16761.1}\n"
                        + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n"
                        + "RC   PLASMID=pSd11_G1246 {ECO:0000269|PubMed:10433554,\n"
                        + "RC   ECO:0000313|EMBL:BAG16761.1}, pSd12_G1263\n"
                        + "RC   {ECO:0000269|PubMed:10433554}, pSd13_G1271\n"
                        + "RC   {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}, pSd4_G1190\n"
                        + "RC   {ECO:0000303|Ref.6}, and pSd5_G1213"
                        + " {ECO:0000256|HAMAP-Rule:MF_00205};\n"
                        + "RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;\n"
                        + "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;\n"
                        + "RT   \"Comparative genomic analyses of frog virus 3, type species of the"
                        + " genus\n"
                        + "RT   Ranavirus (family Iridoviridae).\";\n"
                        + "RL   Virology 323:70-84(2004).";

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        List<String> evs = new ArrayList<>();
        evs.add(ev1);
        evs.add(ev2);
        evs.add(ev3);

        JournalArticleBuilder jaBuilder = new JournalArticleBuilder();
        List<String> authors =
                Arrays.asList(
                        new String[] {
                            "Tan W.G.", "Barkman T.J.", "Gregory Chinchar V.", "Essani K."
                        });
        jaBuilder.authorsSet(authors);

        String title =
                "Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus"
                        + " (family Iridoviridae).";
        jaBuilder.title(title);
        jaBuilder.citationCrossReferencesSet(
                buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", null));
        jaBuilder
                .journalName("Virology")
                .firstPage("70")
                .lastPage("84")
                .volume("323")
                .publicationDate("2004");

        List<ReferenceComment> referenceComments = new ArrayList<>();
        String ss1 = "pSd11_G1246";
        List<String> evs1 = new ArrayList<>();
        evs1.add(ev1);
        evs1.add(ev2);
        referenceComments.add(buildReferenceComment(ss1, ReferenceCommentType.PLASMID, evs1));

        String ss2 = "pSd12_G1263";
        List<String> evs2 = new ArrayList<>();
        evs2.add(ev2);
        referenceComments.add(buildReferenceComment(ss2, ReferenceCommentType.PLASMID, evs2));

        String ss3 = "pSd13_G1271";
        List<String> evs3 = new ArrayList<>();
        evs3.add(ev4);
        evs3.add(ev5);
        referenceComments.add(buildReferenceComment(ss3, ReferenceCommentType.PLASMID, evs3));

        String ss4 = "pSd4_G1190";
        List<String> evs4 = new ArrayList<>();
        evs4.add(ev3);
        referenceComments.add(buildReferenceComment(ss4, ReferenceCommentType.PLASMID, evs4));

        String ss5 = "pSd5_G1213";
        List<String> evs5 = new ArrayList<>();
        evs5.add(ev5);
        referenceComments.add(buildReferenceComment(ss5, ReferenceCommentType.PLASMID, evs5));

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(1)
                        .citation(jaBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .referenceCommentsSet(referenceComments)
                        .evidencesSet(createEvidence(evs))
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testSubmission() {
        String rlines =
                "RN   [2]\n"
                        + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n"
                        + "RC   STRAIN=439-80 / Serotype O:9, and pSd11_G1246;\n"
                        + "RC   PLASMID=pYV, and pSd2_G1252;\n"
                        + "RC   TRANSPOSON=Tn2502, pSd4_G1190, and pSd5_G1213;\n"
                        + "RA   Tan W.G.H., Barkman T.J., Chinchar V.G.;\n"
                        + "RT   \"Emergence of plasmid-mediated quinolone resistance in Escherichia"
                        + " coli in\n"
                        + "RT   Europe.\";\n"
                        + "RL   Submitted (FEB-2004) to the EMBL/GenBank/DDBJ databases.";
        SubmissionBuilder smBuilder = new SubmissionBuilder();
        List<String> authors =
                new ArrayList<>(
                        Arrays.asList(
                                new String[] {"Tan W.G.H.", "Barkman T.J.", "Chinchar V.G."}));
        String title =
                "Emergence of plasmid-mediated quinolone resistance in Escherichia coli in Europe.";
        smBuilder.authorsSet(authors).title(title).publicationDate("FEB-2004");

        smBuilder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ);
        List<ReferenceComment> referenceComments = new ArrayList<>();
        List<String> strains = Arrays.asList(new String[] {"439-80 / Serotype O:9", "pSd11_G1246"});
        referenceComments.addAll(buildReferenceComments(strains, ReferenceCommentType.STRAIN));
        List<String> plasmids = Arrays.asList(new String[] {"pYV", "pSd2_G1252"});
        referenceComments.addAll(buildReferenceComments(plasmids, ReferenceCommentType.PLASMID));

        List<String> transponsons =
                new ArrayList<>(Arrays.asList(new String[] {"Tn2502", "pSd4_G1190", "pSd5_G1213"}));

        referenceComments.addAll(
                buildReferenceComments(transponsons, ReferenceCommentType.TRANSPOSON));
        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(2)
                        .citation(smBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .referenceCommentsSet(referenceComments)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testSubmissionEvidence() {
        String rlines =
                "RN   [2]\n"
                        + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n"
                        + "RC   STRAIN=439-80 / Serotype O:9 {ECO:0000269|PubMed:10433554,\n"
                        + "RC   ECO:0000313|EMBL:BAG16761.1}, and pSd11_G1246\n"
                        + "RC   {ECO:0000269|PubMed:10433554};\n"
                        + "RC   PLASMID=pYV {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2},\n"
                        + "RC   and pSd2_G1252 {ECO:0000303|Ref.6};\n"
                        + "RC   TRANSPOSON=Tn2502 {ECO:0000256|HAMAP-Rule:MF_00205}, and pSd4_G1190\n"
                        + "RC   {ECO:0000256|HAMAP-Rule:MF_00205};\n"
                        + "RA   Tan W.G.H., Barkman T.J., Chinchar V.G.;\n"
                        + "RT   \"Emergence of plasmid-mediated quinolone resistance in Escherichia"
                        + " coli in\n"
                        + "RT   Europe.\";\n"
                        + "RL   Submitted (FEB-2004) to the EMBL/GenBank/DDBJ databases.";

        String ev1 = "ECO:0000313|EMBL:BAG16761.1";
        String ev2 = "ECO:0000269|PubMed:10433554";
        String ev3 = "ECO:0000303|Ref.6";
        String ev4 = "ECO:0000313|PDB:3OW2";
        String ev5 = "ECO:0000256|HAMAP-Rule:MF_00205";

        SubmissionBuilder smBuilder = new SubmissionBuilder();
        List<String> authors =
                Arrays.asList(new String[] {"Tan W.G.H.", "Barkman T.J.", "Chinchar V.G."});
        String title =
                "Emergence of plasmid-mediated quinolone resistance in Escherichia coli in Europe.";
        smBuilder.authorsSet(authors).title(title).publicationDate("FEB-2004");

        smBuilder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ);
        List<ReferenceComment> referenceComments = new ArrayList<>();

        String ss1 = "439-80 / Serotype O:9";
        List<String> evs1 = new ArrayList<>();
        evs1.add(ev1);
        evs1.add(ev2);
        referenceComments.add(buildReferenceComment(ss1, ReferenceCommentType.STRAIN, evs1));

        String ss2 = "pSd11_G1246";
        List<String> evs2 = new ArrayList<>();
        evs2.add(ev2);
        referenceComments.add(buildReferenceComment(ss2, ReferenceCommentType.STRAIN, evs2));

        String ss3 = "pYV";
        List<String> evs3 = new ArrayList<>();
        evs3.add(ev4);
        evs3.add(ev5);
        referenceComments.add(buildReferenceComment(ss3, ReferenceCommentType.PLASMID, evs3));

        String ss4 = "pSd2_G1252";
        List<String> evs4 = new ArrayList<>();
        evs4.add(ev3);
        referenceComments.add(buildReferenceComment(ss4, ReferenceCommentType.PLASMID, evs4));

        String ss5 = "Tn2502";
        List<String> evs5 = new ArrayList<>();
        evs5.add(ev5);
        referenceComments.add(buildReferenceComment(ss5, ReferenceCommentType.TRANSPOSON, evs5));

        String ss6 = "pSd4_G1190";
        List<String> evs6 = new ArrayList<>();
        evs6.add(ev5);
        referenceComments.add(buildReferenceComment(ss6, ReferenceCommentType.TRANSPOSON, evs6));

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(2)
                        .citation(smBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .referenceCommentsSet(referenceComments)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testBook1() {
        String rlines =
                "RN   [1]\n"
                        + "RP   NUCLEOTIDE SEQUENCE.\n"
                        + "RA   Arctander P., Fjeldsaa J.;\n"
                        + "RT   \"Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A"
                        + " study\n"
                        + "RT   of speciation using DNA sequence data.\";\n"
                        + "RL   (In) Loeschcke V., Magnusson S., Ottesen M., Foltmann B., Dano K.,\n"
                        + "RL   Neurath H. (eds.);\n"
                        + "RL   CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";
        BookBuilder bkBuilder = new BookBuilder();
        List<String> authors = Arrays.asList(new String[] {"Arctander P.", "Fjeldsaa J."});

        List<String> editors =
                new ArrayList<>(
                        Arrays.asList(
                                new String[] {
                                    "Loeschcke V.",
                                    "Magnusson S.",
                                    "Ottesen M.",
                                    "Foltmann B.",
                                    "Dano K.",
                                    "Neurath H."
                                }));
        String title =
                "Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A study of"
                        + " speciation using DNA sequence data.";
        bkBuilder.authorsSet(authors);
        bkBuilder.editorsSet(editors);
        bkBuilder.title(title);
        bkBuilder
                .bookName("CONSERVATION GENETICS")
                .firstPage("205")
                .lastPage("227")
                .publisher("Birkhaeuser Verlag")
                .address("Basel")
                .publicationDate("1994");

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(1)
                        .citation(bkBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testBook2() {
        String rlines =
                "RN   [5]\n"
                        + "RP   NUCLEOTIDE SEQUENCE.\n"
                        + "RA   Arctander P., Fjeldsaa J.;\n"
                        + "RT   \"Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A"
                        + " study\n"
                        + "RT   of speciation using DNA sequence data.\";\n"
                        + "RL   (In) Magnusson S., Ottesen M., Foltmann B., Dano K., Neurath H."
                        + " (eds.);\n"
                        + "RL   CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";

        BookBuilder bkBuilder = new BookBuilder();
        List<String> authors = Arrays.asList(new String[] {"Arctander P.", "Fjeldsaa J."});

        List<String> editors =
                new ArrayList<>(
                        Arrays.asList(
                                new String[] {
                                    "Magnusson S.",
                                    "Ottesen M.",
                                    "Foltmann B.",
                                    "Dano K.",
                                    "Neurath H."
                                }));
        String title =
                "Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A study of"
                        + " speciation using DNA sequence data.";
        bkBuilder.authorsSet(authors);
        bkBuilder.editorsSet(editors);
        bkBuilder.title(title);
        bkBuilder
                .bookName("CONSERVATION GENETICS")
                .firstPage("205")
                .lastPage("227")
                .publisher("Birkhaeuser Verlag")
                .address("Basel")
                .publicationDate("1994");

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(5)
                        .citation(bkBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testBook3() {
        String rlines =
                "RN   [5]\n"
                        + "RP   NUCLEOTIDE SEQUENCE.\n"
                        + "RA   Arctander P., Fjeldsaa J.;\n"
                        + "RT   \"Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A"
                        + " study\n"
                        + "RT   of speciation using DNA sequence data.\";\n"
                        + "RL   (In) Proceedings of the 9th international conference on Arabidopsis\n"
                        + "RL   research, abstract#501708527, Madison (1998).";

        BookBuilder bkBuilder = new BookBuilder();
        List<String> authors = Arrays.asList("Arctander P.", "Fjeldsaa J.");

        List<String> editors = Arrays.asList(new String[] {});
        String title =
                "Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A study of"
                        + " speciation using DNA sequence data.";
        String bookTitle =
                "Proceedings of the 9th international conference on Arabidopsis research";
        bkBuilder.authorsSet(authors);
        bkBuilder.editorsSet(editors);
        bkBuilder.title(title);
        bkBuilder
                .bookName(bookTitle)
                .firstPage("abstract#501708527")
                // .lastPage("227")
                .publisher("Madison")
                // .address("Basel")
                .publicationDate("1998");

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(5)
                        .citation(bkBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testThesis() {
        String rlines =
                "RN   [5]\n"
                        + "RP   NUCLEOTIDE SEQUENCE.\n"
                        + "RA   Arctander P., Fjeldsaa J.;\n"
                        + "RT   \"Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A"
                        + " study\n"
                        + "RT   of speciation using DNA sequence data.\";\n"
                        + "RL   Thesis (2002), Department of Fakultaet fuer Biologie, Universitaet\n"
                        + "RL   Heidelberg, Heidelberg, Germany.";

        ThesisBuilder thBuilder = new ThesisBuilder();

        List<String> authors = Arrays.asList("Arctander P.", "Fjeldsaa J.");

        String title =
                "Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A study of"
                        + " speciation using DNA sequence data.";
        thBuilder.authorsSet(authors);

        thBuilder.title(title);

        thBuilder.publicationDate("2002");
        String institute = "Department of Fakultaet fuer Biologie, Universitaet Heidelberg";
        thBuilder.institute(institute);
        thBuilder.address("Heidelberg, Germany");

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(5)
                        .citation(thBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testPatent() {
        String rlines =
                "RN   [5]\n"
                        + "RP   NUCLEOTIDE SEQUENCE.\n"
                        + "RA   Arctander P., Fjeldsaa J.;\n"
                        + "RT   \"Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A"
                        + " study\n"
                        + "RT   of speciation using DNA sequence data.\";\n"
                        + "RL   Patent number WO0149833, 12-JUL-2001.";

        PatentBuilder paBuilder = new PatentBuilder();

        List<String> authors = Arrays.asList("Arctander P.", "Fjeldsaa J.");
        paBuilder.authorsSet(authors);
        String title =
                "Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A study of"
                        + " speciation using DNA sequence data.";
        paBuilder.patentNumber("WO0149833");

        paBuilder.title(title);

        paBuilder.publicationDate("12-JUL-2001");

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(5)
                        .citation(paBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testElectronicJournal() {
        String rlines =
                "RN   [5]\n"
                        + "RP   NUCLEOTIDE SEQUENCE.\n"
                        + "RA   Arctander P., Fjeldsaa J.;\n"
                        + "RT   \"Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A"
                        + " study\n"
                        + "RT   of speciation using DNA sequence data.\";\n"
                        + "RL   (er) Plant Gene Register PGR99-114.";

        ElectronicArticleBuilder eaBuilder = new ElectronicArticleBuilder();

        List<String> authors = Arrays.asList(new String[] {"Arctander P.", "Fjeldsaa J."});
        eaBuilder.authorsSet(authors);
        String title =
                "Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A study of"
                        + " speciation using DNA sequence data.";

        eaBuilder.title(title);
        String locator = "PGR99-114";
        String journal = "Plant Gene Register";
        eaBuilder.locator(locator).journalName(journal);

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(5)
                        .citation(eaBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .build();
        doTest(rlines, uniRef);
    }

    @Test
    void testUnpublishedObservation() {
        String rlines =
                "RN   [5]\n"
                        + "RP   NUCLEOTIDE SEQUENCE.\n"
                        + "RA   Arctander P., Fjeldsaa J.;\n"
                        + "RT   \"Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A"
                        + " study\n"
                        + "RT   of speciation using DNA sequence data.\";\n"
                        + "RL   Unpublished observations (JAN-2012).";

        UnpublishedBuilder uoBuilder = new UnpublishedBuilder();

        List<String> authors = Arrays.asList(new String[] {"Arctander P.", "Fjeldsaa J."});
        uoBuilder.authorsSet(authors);
        String title =
                "Andean tapaculos of the genus Scytalopus (Aves, Rhinocryptidae): A study of"
                        + " speciation using DNA sequence data.";

        uoBuilder.title(title);

        uoBuilder.publicationDate("JAN-2012");

        List<String> referencePositions = new ArrayList<>();
        referencePositions.add("NUCLEOTIDE SEQUENCE");
        UniProtKBReference uniRef =
                new UniProtKBReferenceBuilder()
                        .referenceNumber(5)
                        .citation(uoBuilder.build())
                        .referencePositionsSet(referencePositions)
                        .build();
        doTest(rlines, uniRef);
    }

    private ReferenceComment buildReferenceComment(
            String val, ReferenceCommentType type, List<String> evs) {
        return new ReferenceCommentBuilder()
                .type(type)
                .value(val)
                .evidencesSet(createEvidence(evs))
                .build();
    }

    private List<ReferenceComment> buildReferenceComments(
            List<String> vals, ReferenceCommentType type) {
        List<ReferenceComment> sss = new ArrayList<>();
        if ((vals != null) && (!vals.isEmpty())) {
            for (String val : vals) {
                sss.add(buildReferenceComment(val, type, Collections.emptyList()));
            }
        }
        return sss;
    }

    private List<CrossReference<CitationDatabase>> buildCitationXref(
            String pubmed, String doi, String agricolaId) {
        List<CrossReference<CitationDatabase>> xrefs = new ArrayList<>();
        if (pubmed != null) {
            xrefs.add(
                    new CrossReferenceBuilder<CitationDatabase>()
                            .database(CitationDatabase.PUBMED)
                            .id(pubmed)
                            .build());
        }
        if (doi != null)
            xrefs.add(
                    new CrossReferenceBuilder<CitationDatabase>()
                            .database(CitationDatabase.DOI)
                            .id(doi)
                            .build());
        if (agricolaId != null)
            xrefs.add(
                    new CrossReferenceBuilder<CitationDatabase>()
                            .database(CitationDatabase.AGRICOLA)
                            .id(agricolaId)
                            .build());
        return xrefs;
    }

    private void doTest(String rlines, UniProtKBReference citation) {
        FFLine ffLine = builder.buildWithEvidence(citation);
        String resultString = ffLine.toString();

        assertEquals(rlines, resultString);
    }

    private List<Evidence> createEvidence(List<String> evIds) {
        return evIds.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }
}
