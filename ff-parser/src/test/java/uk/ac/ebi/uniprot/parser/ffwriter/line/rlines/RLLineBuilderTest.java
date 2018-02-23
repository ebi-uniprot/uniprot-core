package uk.ac.ebi.uniprot.parser.ffwriter.line.rlines;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.parser.impl.rl.RLLineBuilder;

public class RLLineBuilderTest {
	private final RLLineBuilder builder = new RLLineBuilder();
	@Test
	public void testJournalArticle(){
		JournalArticle ja  = buildJournalArticle( "Virology", "70", "84", "323", "2004");
		List<String> lines = builder.buildLine(ja, true, true);
		assertEquals(1, lines.size());
		String expected = "RL   Virology 323:70-84(2004).";
		assertEquals(expected, lines.get(0));
		lines  = builder.buildLine(ja, false, true);
		assertEquals(1, lines.size());
		 expected = "Virology 323:70-84(2004).";
		assertEquals(expected, lines.get(0));
	}
	private JournalArticle buildJournalArticle( String name,
			String firstPage, String lastPage, String volume, String date){
		
		JournalArticleBuilder builder = JournalArticleBuilder.newInstance();
		return builder.journalName(name)
		.firstPage(firstPage)
		.lastPage(lastPage)
		.volume(volume)
		.publicationDate(AbstractCitationBuilder.createPublicationDate(date))
		.build();
	
	}
	
	@Test 
	public void testSubmission(){
		SubmissionBuilder subbuilder = SubmissionBuilder.newInstance();
		subbuilder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
		.publicationDate(AbstractCitationBuilder.createPublicationDate("FEB-2004"))
		;
		Submission citation =subbuilder.build();
		List<String> lines = builder.buildLine(citation, true, true);
		assertEquals(1, lines.size());
		String expected = "RL   Submitted (FEB-2004) to the EMBL/GenBank/DDBJ databases.";
		assertEquals(expected, lines.get(0));
		lines  = builder.buildLine(citation, false, true);
		assertEquals(1, lines.size());
		 expected = "Submitted (FEB-2004) to the EMBL/GenBank/DDBJ databases.";
		assertEquals(expected, lines.get(0));
	}
	
	@Test
	public void testBook(){
		BookBuilder bookBuilder  =BookBuilder.newInstance();
		List<String > editors =new ArrayList<>(Arrays.asList(new String[] {"Magnusson S.", 
				"Ottesen M.", "Foltmann B.",  "Dano K.", "Neurath H."}));
		bookBuilder.editors(buildEditors(editors))
		.bookName("CONSERVATION GENETICS")
		.firstPage("205")
		.lastPage("227")
		.publisher("Birkhaeuser Verlag")
		.address("Basel")
		.publicationDate(AbstractCitationBuilder.createPublicationDate("1994"));
		
		Book citation =bookBuilder.build();
		
		List<String> lines = builder.buildLine(citation, true, true);
		assertEquals(3, lines.size());
		String expected = "RL   CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";
		assertEquals(expected, lines.get(2));
		lines  = builder.buildLine(citation, false, true);
		assertEquals(1, lines.size());
		 expected = "(In) Magnusson S., Ottesen M., Foltmann B., Dano K., Neurath H. (eds.); CONSERVATION GENETICS, pp.205-227, Birkhaeuser Verlag, Basel (1994).";
		assertEquals(expected, lines.get(0));
		
	}
	
	 private List<Author> buildEditors(List<String> names){
		return names.stream().map(val-> AbstractCitationBuilder.createAuthor(val))
		 .collect(Collectors.toList());		
		}
}
