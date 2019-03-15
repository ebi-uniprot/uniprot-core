package uk.ac.ebi.uniprot.cv.pathway;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.cv.keyword.KeywordDetail;

public class PathwayCacheIT {
private static List<Pathway> pathways;
	
	@BeforeAll
	static void setup() {
		pathways = PathwayCache.INSTANCE.get("");
	}
	
	@Test
	void testNoParent() {
		 String acc = "UPA00611" ;
		 Optional<Pathway> opVal=
				 pathways.stream().filter(val -> val.getAccession().equals(acc))
		 .findFirst();
		 assertTrue(opVal.isPresent());
		 List<Pathway> hi = opVal.get().getIsAParents();
		 List<Pathway> hp = opVal.get().getPartOfParents();
		 assertNull(hi);
		 assertNull(hp);
		
	}
	
	@Test
	void testMultiXrefWithHi() {
		 String acc = "UPA00056" ;
		 Optional<Pathway> opVal=
				 pathways.stream().filter(val -> val.getAccession().equals(acc))
		 .findFirst();
		 assertTrue(opVal.isPresent());
		 List<Pathway> hi = opVal.get().getIsAParents();
		 List<Pathway> hp = opVal.get().getPartOfParents();
		 assertEquals(1, hi.size());
		 assertTrue(hp.isEmpty());
		 System.out.println(
		 opVal.get().getCrossReferences());
		
		
	}
	
	@Test
	void testWithHP() {
		 String acc = "UPA00056" ;
		 Optional<Pathway> opVal=
				 pathways.stream().filter(val -> val.getAccession().equals(acc))
		 .findFirst();
		 assertTrue(opVal.isPresent());
		 List<Pathway> hi = opVal.get().getIsAParents();
		 List<Pathway> hp = opVal.get().getPartOfParents();
		 assertEquals(1, hi.size());
		 assertTrue(hp.isEmpty());
		 System.out.println(
		 opVal.get().getCrossReferences());
		
		
	}
}
