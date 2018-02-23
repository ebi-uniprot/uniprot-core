package uk.ac.ebi.uniprot.parser.ffwriter.line.rlines;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.parser.impl.ra.RALineBuilder;


public class RALineBuilderTest {
	private final RALineBuilder builder = new RALineBuilder();
	
	@Test
	public void test1(){
		List<String > authorsS =new ArrayList<>(Arrays.asList(new String[] { "Tan W.G.", "Barkman T.J.", "Gregory Chinchar V.", "Essani K.", "Arctander P.", "Fjeldsaa J."}));
		List<Author> authors = buildAuthors(authorsS);
		List<String> lines =builder.buildLine(authors, true, true);
		assertEquals(2, lines.size());
		String expected ="RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K., Arctander P.,";
		assertEquals(expected, lines.get(0));
		
	}
	@Test
	public void test2(){
		List<String > authorsS =new ArrayList<>(Arrays.asList(new String[] { "Tan W.G.", "Barkman T.J.", "Gregory Chinchar V.", "Essani K.", "Arctander P.", "Fjeldsaa J.", "Gjeldsaa J."}));
		List<Author> authors = buildAuthors(authorsS);
		List<String> lines =builder.buildLine(authors, false, true);
		assertEquals(1, lines.size());
		String expected ="Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K., Arctander P., Fjeldsaa J., Gjeldsaa J.;";
		assertEquals(expected, lines.get(0));
	}
	
	private List<Author>  buildAuthors(List<String> names){
		List<Author> authors =new ArrayList<>();
		for(String name:names){
			authors.add( AbstractCitationBuilder.createAuthor(name));
		}
		return authors;
	}
}
