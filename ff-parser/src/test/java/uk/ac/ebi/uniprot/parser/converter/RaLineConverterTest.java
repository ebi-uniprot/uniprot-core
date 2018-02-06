package uk.ac.ebi.uniprot.parser.converter;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineObject;

public class RaLineConverterTest {
	@Test
	public void test(){
		// "RA   Galinier A., Perriere G., Duclos B.;\n";
		RaLineObject ra =new RaLineObject();
		ra.authors.add("Galinier A.");
		ra.authors.add("Perriere G.");
		ra.authors.add("Duclos B.");
		
		RaLineConverter converter = new RaLineConverter();
		 List<Author> authors = converter.convert (ra);
		 TestCase.assertEquals(3, authors.size());
		 validate(authors, "Galinier A.");
		 validate(authors, "Perriere G.");
		 validate(authors, "Duclos B.");
	}
	private void validate(List<Author> authors , String author){
		boolean found =false;
		for(Author au:authors){
			if(author.equals(au.getValue())){
				found= true;
				break;
			}
		}
		TestCase.assertTrue(found);
	}
}
