package org.uniprot.core.flatfile.writer.line.rlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.BookBuilder;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.citation.impl.PublicationDateBuilder;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.flatfile.parser.impl.rl.RLLineBuilder;

class RLLineBuilderTest {
    private final RLLineBuilder builder = new RLLineBuilder();

    @Test
    void testJournalArticle() {
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

    private JournalArticle buildJournalArticle(
            String name, String firstPage, String lastPage, String volume, String date) {

        JournalArticleBuilder builder = new JournalArticleBuilder();
        return builder.journalName(name)
                .firstPage(firstPage)
                .lastPage(lastPage)
                .volume(volume)
                .publicationDate(date)
                .build();
    }

    @Test
    void testSubmission() {
        SubmissionBuilder subbuilder = new SubmissionBuilder();
        subbuilder
                .submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .publicationDate("FEB-2004");
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
    void testBook() {
        BookBuilder bookBuilder = new BookBuilder();
        List<String> editors =
                new ArrayList<>(
                        Arrays.asList(
                                "Magnusson S.",
                                "Ottesen M.",
                                "Foltmann B.",
                                "Dano K.",
                                "Neurath H."));
        bookBuilder
                .editorsSet(editors)
                .bookName("CONSERVATION GENETICS")
                .firstPage("205")
                .lastPage("227")
                .publisher("Birkhaeuser Verlag")
                .address("Basel")
                .publicationDate("1994");

        Book citation = bookBuilder.build();

        List<String> lines = builder.buildLine(citation, true, true);
        assertEquals(2, lines.size());
        String expected =
                "RL   CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";
        assertEquals(expected, lines.get(1));
        lines = builder.buildLine(citation, false, true);
        assertEquals(1, lines.size());
        expected =
                "(In) Magnusson S., Ottesen M., Foltmann B., Dano K., Neurath H. (eds.);"
                        + " CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";
        assertEquals(expected, lines.get(0));
    }
    @Test 
    void testElectronicArticle(){
    	//RL   (er) Invest. Ophthalmol. Vis. Sci. 43:ARVO E-Abstract 791(2002)(2002).
    	ElectronicArticleBuilder eabuilder = new ElectronicArticleBuilder();
    	eabuilder.locator("Invest. Ophthalmol. Vis. Sci. 43:ARVO E-Abstract 791(2002)")
    	.publicationDate(new PublicationDateBuilder("2002").build());
    	ElectronicArticle ea = eabuilder.build();
    	 List<String> lines = builder.buildLine(ea, true, true);
    	 assertEquals(1, lines.size());
    	 String expected =
                 "RL   (er) Invest. Ophthalmol. Vis. Sci. 43:ARVO E-Abstract 791(2002)(2002).";
    	 assertEquals(expected, lines.get(0));
    	
    }
}
