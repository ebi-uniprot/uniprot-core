package org.uniprot.core.flatfile.parser.converter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Author;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineConverter;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RaLineConverterTest {
	@Test
	void test(){
		// "RA   Galinier A., Perriere G., Duclos B.;\n";
		RaLineObject ra =new RaLineObject();
		ra.authors.add("Galinier A.");
		ra.authors.add("Perriere G.");
		ra.authors.add("Duclos B.");
		
		RaLineConverter converter = new RaLineConverter();
		 List<Author> authors = converter.convert (ra);
		 assertEquals(3, authors.size());
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
		assertTrue(found);
	}
}
