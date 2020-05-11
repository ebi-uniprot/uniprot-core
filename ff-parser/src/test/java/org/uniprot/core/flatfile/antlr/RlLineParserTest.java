package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineObject;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineObject.SubmissionDB;

import java.util.Arrays;
import java.util.List;

class RlLineParserTest {
    @Test
    void testJournalArticle1() {
        String rgLines = "RL   J. Mol. Biol. 168:321-331(1983).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.JournalArticle);
        verify(
                (RlLineObject.JournalArticle) obj.getReference(),
                "J. Mol. Biol.",
                "321",
                "331",
                "168",
                1983);
    }

    @Test
    void testJournalArticle2() {
        String rgLines = "RL   Int. J. Parasitol. 0:0-0(2005).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.JournalArticle);
        verify(
                (RlLineObject.JournalArticle) obj.getReference(),
                "Int. J. Parasitol.",
                "0",
                "0",
                "0",
                2005);
    }

    @Test
    void testJournalContainDash() {
        String rgLines = "RL   Hoppe-Seyler's Z. Physiol. Chem. 362:1665-1669(1981).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.JournalArticle);
        verify(
                (RlLineObject.JournalArticle) obj.getReference(),
                "Hoppe-Seyler's Z. Physiol. Chem.",
                "1665",
                "1669",
                "362",
                1981);
    }

    @Test
    void testJournalContainDash2() {
        String rgLines = "RL   Abstr. - Soc. Neurosci. 25:168-168(1999).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.JournalArticle);
        verify(
                (RlLineObject.JournalArticle) obj.getReference(),
                "Abstr. - Soc. Neurosci.",
                "168",
                "168",
                "25",
                1999);
    }

    @Test
    void testJournalContainBracket() {
        String rgLines = "RL   Clin. Endocrinol. (Oxf.) 56:413-418(2002).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.JournalArticle);
        verify(
                (RlLineObject.JournalArticle) obj.getReference(),
                "Clin. Endocrinol. (Oxf.)",
                "413",
                "418",
                "56",
                2002);
    }

    @Test
    void testJournalWithSpeciealPage() {
        String rgLines = "RL   PLoS ONE 3:E1450-E1450(2008).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.JournalArticle);
        verify(
                (RlLineObject.JournalArticle) obj.getReference(),
                "PLoS ONE",
                "E1450",
                "E1450",
                "3",
                2008);
    }

    private void verify(
            RlLineObject.JournalArticle ja,
            String journal,
            String pageStart,
            String pageEnd,
            String volume,
            int year) {
        assertEquals(journal, ja.getJournal());
        assertEquals(pageStart, ja.getFirstPage());
        assertEquals(pageEnd, ja.getLastPage());
        assertEquals(volume, ja.getVolume());
        assertEquals(year, ja.getYear());
    }

    @Test
    void testEpub() {
        String rgLines = "RL   (er) Plant Gene Register PGR98-023.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.EPub);
        verify((RlLineObject.EPub) obj.getReference(), "Plant Gene Register PGR98-023");
    }

    @Test
    void testEpub2() {
        String rgLines = "RL   (er) Invest. Ophthalmol. Vis. Sci. 43:ARVO E-Abstract 791(2002).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.EPub);
        verify(
                (RlLineObject.EPub) obj.getReference(),
                "Invest. Ophthalmol. Vis. Sci. 43:ARVO E-Abstract 791(2002)");
    }

    @Test
    void testEpub3() {
        String rgLines = "RL   (er) J. Am. Chem. Soc. 121:9223-9224(1999).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.EPub);
        verify((RlLineObject.EPub) obj.getReference(), "J. Am. Chem. Soc. 121:9223-9224(1999)");
    }

    private void verify(RlLineObject.EPub epub, String title) {
        assertEquals(title, epub.getTitle());
    }

    @Test
    void testSubmission() {
        String rgLines = "RL   Submitted (OCT-1995) to the EMBL/GenBank/DDBJ databases.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Submission);
        verify((RlLineObject.Submission) obj.getReference(), SubmissionDB.EMBL, 1995, "OCT");
    }

    private void verify(
            RlLineObject.Submission submission, SubmissionDB db, int year, String month) {
        assertEquals(db, submission.getDb());
        assertEquals(month, submission.getMonth());
        assertEquals(year, submission.getYear());
    }

    @Test
    void testPatent() {
        String rgLines = "RL   Patent number WO9010703, 20-SEP-1990.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Patent);
        verify((RlLineObject.Patent) obj.getReference(), "WO9010703", 1990, "SEP", 20);
    }

    private void verify(
            RlLineObject.Patent patent, String patentNumber, int year, String month, int day) {
        assertEquals(patentNumber, patent.getPatentNumber());
        assertEquals(month, patent.getMonth());
        assertEquals(year, patent.getYear());
        assertEquals(day, patent.getDay());
    }

    @Test
    void testThesis() {
        String rgLines = "RL   Thesis (1977), University of Geneva, Switzerland.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Thesis);
        verify(
                (RlLineObject.Thesis) obj.getReference(),
                "University of Geneva",
                "Switzerland",
                1977);
    }

    @Test
    void testThesis2() {
        String rgLines = "RL   Thesis (2001), A. Mickiewicz University, Poland.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Thesis);
        verify(
                (RlLineObject.Thesis) obj.getReference(),
                "A. Mickiewicz University",
                "Poland",
                2001);
    }

    @Test
    void testThesis3() {
        String rgLines =
                "RL   Thesis (2008), Department of Biosystems, K.U.Leuven, Leuven, Belgium.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Thesis);
        verify(
                (RlLineObject.Thesis) obj.getReference(),
                "Department of Biosystems",
                "K.U.Leuven, Leuven, Belgium",
                2008);
    }

    @Test
    void testThesis4() {
        String rgLines = "RL   Thesis (2001), A. Mickiewicz University.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Thesis);
        verify((RlLineObject.Thesis) obj.getReference(), "A. Mickiewicz University", null, 2001);
    }

    @Test
    void testThesis5() {
        String rgLines =
                "RL   Thesis (2000), Department of Veterinary Medicine,\n"
                        + "RL   Justus-Liebig-University, D-35392 Giessen, Germany.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Thesis);
        verify(
                (RlLineObject.Thesis) obj.getReference(),
                "Department of Veterinary Medicine",
                "Justus-Liebig-University, D-35392 Giessen, Germany",
                2000);
    }

    @Test
    void testThesis6() {
        String rgLines =
                "RL   Thesis (2010), Suranaree Univercity of Technology, 111 Suranaree Ave.\n"
                        + "RL   Suranaree Univercity of Technology, Thailand, Nakhon Ratchasima,\n"
                        + "RL   Thailand.\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Thesis);
        verify(
                (RlLineObject.Thesis) obj.getReference(),
                "Suranaree Univercity of Technology",
                "111 Suranaree Ave. Suranaree Univercity of Technology, Thailand, Nakhon"
                        + " Ratchasima, Thailand",
                2010);
    }

    private void verify(RlLineObject.Thesis thesis, String institute, String country, int year) {
        assertEquals(institute, thesis.getInstitute());
        assertEquals(country, thesis.getCountry());
        assertEquals(year, thesis.getYear());
    }

    @Test
    void testUnpublished() {
        String rgLines = "RL   Unpublished observations (OCT-1978).\n";

        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Unpublished);
        verify((RlLineObject.Unpublished) obj.getReference(), "OCT", 1978);
    }

    private void verify(RlLineObject.Unpublished up, String month, int year) {
        assertEquals(month, up.getMonth());
        assertEquals(year, up.getYear());
    }

    @Test
    void testBook() {
        String rgLines =
                "RL   (In) Boyer P.D. (eds.);\n"
                        + "RL   The enzymes (3rd ed.), pp.11:397-547, Academic Press, New York"
                        + " (1975).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Boyer P.D."}),
                "The enzymes (3rd ed.)",
                "397",
                "547",
                "11",
                "Academic Press",
                "New York",
                1975);
    }

    @Test
    void testBook2() {
        String rgLines =
                "RL   (In) Rich D.H., Gross E. (eds.);\n"
                    + "RL   Proceedings of the 7th American peptide symposium, pp.69-72, Pierce\n"
                    + "RL   Chemical Co., Rockford Il. (1981).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Rich D.H.", "Gross E."}),
                "Proceedings of the 7th American peptide symposium",
                "69",
                "72",
                null,
                "Pierce Chemical Co.",
                "Rockford Il.",
                1981);
    }

    private void verify(
            RlLineObject.Book book,
            List<String> editors,
            String title,
            String pageStart,
            String pageEnd,
            String volume,
            String press,
            String place,
            int year) {
        assertEquals(editors, book.getEditors());
        assertEquals(title, book.getTitle());
        assertEquals(pageStart, book.getPageStart());
        assertEquals(pageEnd, book.getPageEnd());
        assertEquals(volume, book.getVolume());
        assertEquals(press, book.getPress());
        assertEquals(place, book.getPlace());
        assertEquals(year, book.getYear());
    }

    @Test
    void testBookTitleWithSpot() {
        String rgLines =
                "RL   (In) Kueck U. (eds.);\n"
                    + "RL   The Mycota II, Genetics and Biotechnology (2nd edition), pp.95-112,\n"
                    + "RL   Springer-Verlag, Berlin-Heidelberg (2004).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Kueck U."}),
                "The Mycota II, Genetics and Biotechnology (2nd edition)",
                "95",
                "112",
                null,
                "Springer-Verlag",
                "Berlin-Heidelberg",
                2004);
    }

    @Test
    void testBookMultilineEditor() {
        String rgLines =
                "RL   (In) Cummings D.J., Brost P., Dawid I.B., Weissman S.M., Fox C.F.\n"
                    + "RL   (eds.);\n"
                    + "RL   Extrachromosomal DNA, pp.339-355, Academic Press, New York (1979).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(
                        new String[] {
                            "Cummings D.J.", "Brost P.", "Dawid I.B.", "Weissman S.M.", "Fox C.F."
                        }),
                "Extrachromosomal DNA",
                "339",
                "355",
                null,
                "Academic Press",
                "New York",
                1979);
    }

    @Test
    void testBookProceedings() {
        String rgLines =
                "RL   (In) Proceedings of the 20th international conference on Arabidopsis\n"
                        + "RL   research, abstract#543, Edinburgh (2009).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {}),
                "Proceedings of the 20th international conference on Arabidopsis research",
                null,
                null,
                null,
                "Edinburgh",
                null,
                2009);
        assertEquals("abstract#543", ((RlLineObject.Book) obj.getReference()).getPageString());
    }

    @Test
    void testVolumeContainDot() {
        String rgLines =
                "RL   (In) Biggins J. (eds.);\n"
                        + "RL   Progress in photosynthesis research, pp.II.1:13-16, Martinus"
                        + " Nijhoff,\n"
                        + "RL   The Hague (1987).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Biggins J."}),
                "Progress in photosynthesis research",
                "13",
                "16",
                "II.1",
                "Martinus Nijhoff",
                "The Hague",
                1987);
    }

    @Test
    void testVolumeContainDot2() {
        String rgLines =
                "RL   (In) Biggins J. (eds.);\n"
                    + "RL   Progress in photosynthesis research, pp.II.1:13-16, Martinus Nijhoff"
                    + " (1987).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Biggins J."}),
                "Progress in photosynthesis research",
                "13",
                "16",
                "II.1",
                "Martinus Nijhoff",
                null,
                1987);
    }

    @Test
    void testBookTitle() {
        String rgLines =
                "RL   (In) Barnett A.A., Veiga L.M., Ferrari S.F., Norconk M.A. (eds.);\n"
                    + "RL   EVOLUTIONARY BIOLOGY AND CONSERVATION OF TITIS, SAKIS AND UACARIS,\n"
                    + "RL   pp.0-0, Cambridge University Press, Cambridge, UK (2009).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(
                        new String[] {
                            "Barnett A.A.", "Veiga L.M.", "Ferrari S.F.", "Norconk M.A."
                        }),
                "EVOLUTIONARY BIOLOGY AND CONSERVATION OF TITIS, SAKIS AND UACARIS",
                "0",
                "0",
                null,
                "Cambridge University Press",
                "Cambridge, UK",
                2009);
    }

    @Test
    void testBookTitle2() {
        String rgLines =
                "RL   (In) Goffinet B., Hollowell V., Magill R. (eds.);\n"
                        + "RL   MOLECULAR SYSTEMATICS OF BRYOPHYTES - MONOGRAPHS IN SYSTEMATIC"
                        + " BOTANY,\n"
                        + "RL   pp.61-86, Missouri Botanical Garden Press, USA (2004).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Goffinet B.", "Hollowell V.", "Magill R."}),
                "MOLECULAR SYSTEMATICS OF BRYOPHYTES - MONOGRAPHS IN SYSTEMATIC BOTANY",
                "61",
                "86",
                null,
                "Missouri Botanical Garden Press",
                "USA",
                2004);
    }

    @Test
    void testBookStrange() {
        String rgLines =
                "RL   (In) Unknown A. (eds.);\n"
                    + "RL   PROCEEDINGS OF III CONGRESSO NACIONAL DE SAUDE PUBLICA VETERINARIA E"
                    + " I\n"
                    + "RL   ENCONTRO INTERNACIONAL DE SAUDE PUBLICA VETERINARIA, pp.0-0, Brazil\n"
                    + "RL   (2009).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Unknown A."}),
                "PROCEEDINGS OF III CONGRESSO NACIONAL DE SAUDE PUBLICA VETERINARIA E I ENCONTRO"
                        + " INTERNACIONAL DE SAUDE PUBLICA VETERINARIA",
                "0",
                "0",
                null,
                "Brazil",
                null,
                2009);
    }

    @Test
    void testBookStrange2() {
        String rgLines =
                "RL   (In) Klenk, H.-D. (eds.);\n"
                    + "RL   XIIth International Congress of Virology, pp.9-0, EDK. Medical and\n"
                    + "RL   Scientific International Pubulisher, 75014 Paris, France (2002).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Klenk", "H.-D."}),
                "XIIth International Congress of Virology",
                "9",
                "0",
                null,
                "EDK. Medical and Scientific International Pubulisher",
                "75014 Paris, France",
                2002);
    }

    @Test
    void testBookStrange3() {
        String rgLines =
                "RL   (In) Unknown A. (eds.);\n"
                    + "RL   Abstract, pp.8-0, European Symposium on Drosophila\n"
                    + "RL   Neurobiology(Neurofly-2000) Alicante, Spain:23rd to 27th September\n"
                    + "RL   2000:1-1; CSIC (2000).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Unknown A."}),
                "Abstract",
                "8",
                "0",
                null,
                "European Symposium on Drosophila Neurobiology(Neurofly-2000) Alicante",
                "Spain:23rd to 27th September 2000:1-1; CSIC",
                2000);
    }

    @Test
    void testBookStrange4() {
        String rgLines =
                "RL   (In) Proceedings of the 39th annual Drosophila research conference,\n"
                        + "RL   pp.39:414C-414C, Washington D.C. (1998).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {}),
                "Proceedings of the 39th annual Drosophila research conference",
                "414C",
                "414C",
                "39",
                "Washington D.C.",
                null,
                1998);
    }

    @Test
    void testBookStrange5() {
        String rgLines =
                "RL   (In) Proceedings of Plant Biology '2000: The annual meeting of the\n"
                        + "RL   American Society of Plant Physiologists, pp.abstract#272:0-0,\n"
                        + "RL   San Diego (2000).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {}),
                "Proceedings of Plant Biology '2000: The annual meeting of the American Society of"
                        + " Plant Physiologists",
                "0",
                "0",
                "abstract#272",
                "San Diego",
                null,
                2000);
    }

    @Test
    void testBookStrange6() {
        String rgLines =
                "RL   (In) Proceedings of the 20th international conference on Arabidopsis\n"
                        + "RL   research, abstract#501734213, Edinburgh (2009).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {}),
                "Proceedings of the 20th international conference on Arabidopsis research",
                null,
                null,
                null,
                "Edinburgh",
                null,
                2009);
        assertEquals(
                "abstract#501734213", ((RlLineObject.Book) obj.getReference()).getPageString());
    }

    @Test
    void testBookStrange7() {
        String rgLines =
                "RL   (In) Rossiter A., Kawanabe H. (eds.);\n"
                        + "RL   ADVANCES IN ECOLOGICAL RESEARCH 31: BIOLOGY OF ANCIENT LAKES;\n"
                        + "RL   BIODIVERSITY, ECOLOGY AND EVOLUTION, pp.275-302, Academic Press,\n"
                        + "RL   London, United Kingdom (2000).\n";
        UniprotKBLineParser<RlLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRlLineParser();
        RlLineObject obj = parser.parse(rgLines);
        assertTrue(obj.getReference() instanceof RlLineObject.Book);
        verify(
                (RlLineObject.Book) obj.getReference(),
                Arrays.asList(new String[] {"Rossiter A.", "Kawanabe H."}),
                "ADVANCES IN ECOLOGICAL RESEARCH 31: BIOLOGY OF ANCIENT LAKES; BIODIVERSITY,"
                        + " ECOLOGY AND EVOLUTION",
                "275",
                "302",
                null,
                "Academic Press",
                "London, United Kingdom",
                2000);
    }
}
