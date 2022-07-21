package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.*;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineConverter;
import org.uniprot.core.flatfile.parser.impl.rl.RlLineObject;

class RlLineConverterTest {
    private final RlLineConverter converter = new RlLineConverter();

    @Test
    void testJournalArticle() {
        // "RL   J. Mol. Biol. 168:321-331(1983).
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.JournalArticle ja = new RlLineObject.JournalArticle();
        ja.setFirstPage("321");
        ja.setLastPage("331");
        ja.setVolume("168");
        ja.setYear(1983);
        ja.setJournal("J. Mol. Biol.");
        rlObject.setReference(ja);
        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof JournalArticleBuilder);
        JournalArticle journal = ((JournalArticleBuilder) builder).build();

        assertEquals(CitationType.JOURNAL_ARTICLE, journal.getCitationType());

        assertEquals("321", journal.getFirstPage());
        assertEquals("331", journal.getLastPage());
        assertEquals("168", journal.getVolume());
        assertEquals("1983", journal.getPublicationDate().getValue());
        assertEquals("J. Mol. Biol.", journal.getJournal().getName());
    }

    @Test
    void testSubmission() {
        // "RL   Submitted (OCT-1995) to the EMBL/GenBank/DDBJ databases.
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.Submission subm = new RlLineObject.Submission();
        subm.setMonth("OCT");
        subm.setYear(1995);
        subm.setDb(RlLineObject.SubmissionDB.EMBL);
        rlObject.setReference(subm);
        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof SubmissionBuilder);
        Submission submission = ((SubmissionBuilder) builder).build();

        assertEquals(CitationType.SUBMISSION, submission.getCitationType());

        assertEquals("OCT-1995", submission.getPublicationDate().getValue());
        assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, submission.getSubmissionDatabase());
    }

    @Test
    void testThesis() {
        // "RL   Thesis (1977), University of Geneva, Switzerland.\n";
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.Thesis th = new RlLineObject.Thesis();
        th.setCountry("Switzerland");
        th.setInstitute("University of Geneva");
        th.setYear(1977);
        rlObject.setReference(th);
        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof ThesisBuilder);
        Thesis thesis = ((ThesisBuilder) builder).build();

        assertEquals(CitationType.THESIS, thesis.getCitationType());

        assertEquals("Switzerland", thesis.getAddress());
        assertEquals("1977", thesis.getPublicationDate().getValue());
        assertEquals("University of Geneva", thesis.getInstitute());
    }

    @Test
    void testPatent() {
        // "RL   Patent number WO9010703, 20-SEP-1990.\n";
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.Patent th = new RlLineObject.Patent();
        th.setDay(20);
        th.setMonth("SEP");
        th.setYear(1990);
        th.setPatentNumber("WO9010703");
        rlObject.setReference(th);
        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof PatentBuilder);
        Patent patent = ((PatentBuilder) builder).build();

        assertEquals(CitationType.PATENT, patent.getCitationType());

        assertEquals("WO9010703", patent.getPatentNumber());
        assertEquals("20-SEP-1990", patent.getPublicationDate().getValue());
    }

    @Test
    void testBook() {
        // RL   (In) Boyer P.D. (eds.);
        // RL   The enzymes (3rd ed.), pp.11:397-547, Academic Press, New York (1975).
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.Book th = new RlLineObject.Book();
        th.getEditors().add("Boyer P.D.");
        th.setTitle("The enzymes (3rd ed.)");
        th.setPageStart("397");
        th.setPageEnd("547");
        th.setPlace("New York");
        th.setPress("Academic Press");
        th.setVolume("11");
        th.setYear(1975);
        rlObject.setReference(th);
        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof BookBuilder);
        Book book = ((BookBuilder) builder).build();

        assertEquals(CitationType.BOOK, book.getCitationType());
        assertEquals(1, book.getEditors().size());
        assertEquals("Boyer P.D.", book.getEditors().get(0).getValue());
        assertEquals("The enzymes (3rd ed.)", book.getBookName());
        assertEquals("397", book.getFirstPage());
        assertEquals("547", book.getLastPage());
        assertEquals("1975", book.getPublicationDate().getValue());
        assertEquals("11", book.getVolume());
    }

    @Test
    void testElectronicArticle() {
        // "RL   (er) Plant Gene Register PGR98-023.\n";
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.EPub th = new RlLineObject.EPub();
        th.setTitle("Plant Gene Register PGR98-023");

        rlObject.setReference(th);

        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof ElectronicArticleBuilder);
        ElectronicArticle ea = ((ElectronicArticleBuilder) builder).build();
        assertEquals(CitationType.ELECTRONIC_ARTICLE, ea.getCitationType());
        assertEquals("Plant Gene Register", ea.getJournal().getName());
        assertEquals("PGR98-023", ea.getLocator().getValue());
    }

    @Test
    void testElectronicArticle2() {
        // "RL   (er) J. Am. Chem. Soc. 121:9223-9224(1999).\n";
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.EPub th = new RlLineObject.EPub();
        th.setTitle("J. Am. Chem. Soc. 121:9223-9224(1999)");

        rlObject.setReference(th);

        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof ElectronicArticleBuilder);
        ElectronicArticle ea = ((ElectronicArticleBuilder) builder).build();
        assertEquals(CitationType.ELECTRONIC_ARTICLE, ea.getCitationType());
        // assertEquals("Plant Gene Register", ea.getJournal().getName());
        assertEquals("J. Am. Chem. Soc. 121:9223-9224", ea.getLocator().getValue());
    }

    @Test
    void testUnpublished() {
        // "RL   Unpublished observations (OCT-1978).\n";
        RlLineObject rlObject = new RlLineObject();
        RlLineObject.Unpublished th = new RlLineObject.Unpublished();
        th.setMonth("OCT");
        th.setYear(1978);

        rlObject.setReference(th);

        CitationBuilder builder = converter.convert(rlObject);
        assertTrue(builder instanceof UnpublishedBuilder);
        Unpublished ea = ((UnpublishedBuilder) builder).build();

        assertEquals(CitationType.UNPUBLISHED, ea.getCitationType());
        assertEquals("OCT-1978", ea.getPublicationDate().getValue());
    }
}
