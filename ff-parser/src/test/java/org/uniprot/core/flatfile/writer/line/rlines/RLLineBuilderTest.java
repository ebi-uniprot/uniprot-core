package org.uniprot.core.flatfile.writer.line.rlines;

import org.junit.Test;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.BookBuilder;
import org.uniprot.core.citation.builder.JournalArticleBuilder;
import org.uniprot.core.citation.builder.SubmissionBuilder;
import org.uniprot.core.flatfile.parser.impl.rl.RLLineBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RLLineBuilderTest {
    private final RLLineBuilder builder = new RLLineBuilder();

    @Test
    public void testJournalArticle() {
        JournalArticle ja = buildJournalArticle("Virology", "70", "84", "323", "2004");
        List<String> lines = builder.buildLine(ja, true, true);
        assertEquals(1, lines.size());
        String expected = "RL   Virology 323:70-84(2004).";
        assertEquals(expected, lines.get(0));
        lines = builder.buildLine(ja, false, true);
        assertEquals(1, lines.size());
        expected = "Virology 323:70-84(2004).";
        assertEquals(expected, lines.get(0));
    }

    private JournalArticle buildJournalArticle(String name,
                                               String firstPage, String lastPage, String volume, String date) {

        JournalArticleBuilder builder = new JournalArticleBuilder();
        return builder.journalName(name)
                .firstPage(firstPage)
                .lastPage(lastPage)
                .volume(volume)
                .publicationDate(date)
                .build();

    }

    @Test
    public void testSubmission() {
        SubmissionBuilder subbuilder = new SubmissionBuilder();
        subbuilder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .publicationDate("FEB-2004")
        ;
        Submission citation = subbuilder.build();
        List<String> lines = builder.buildLine(citation, true, true);
        assertEquals(1, lines.size());
        String expected = "RL   Submitted (FEB-2004) to the EMBL/GenBank/DDBJ databases.";
        assertEquals(expected, lines.get(0));
        lines = builder.buildLine(citation, false, true);
        assertEquals(1, lines.size());
        expected = "Submitted (FEB-2004) to the EMBL/GenBank/DDBJ databases.";
        assertEquals(expected, lines.get(0));
    }

    @Test
    public void testBook() {
        BookBuilder bookBuilder = new BookBuilder();
        List<String> editors = new ArrayList<>(Arrays.asList("Magnusson S.",
                                                             "Ottesen M.", "Foltmann B.", "Dano K.",
                                                             "Neurath H."));
        bookBuilder.editors(editors)
                .bookName("CONSERVATION GENETICS")
                .firstPage("205")
                .lastPage("227")
                .publisher("Birkhaeuser Verlag")
                .address("Basel")
                .publicationDate("1994");

        Book citation = bookBuilder.build();

        List<String> lines = builder.buildLine(citation, true, true);
        assertEquals(3, lines.size());
        String expected = "RL   CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";
        assertEquals(expected, lines.get(2));
        lines = builder.buildLine(citation, false, true);
        assertEquals(1, lines.size());
        expected = "(In) Magnusson S., Ottesen M., Foltmann B., Dano K., Neurath H. (eds.); CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";
        assertEquals(expected, lines.get(0));
    }
}