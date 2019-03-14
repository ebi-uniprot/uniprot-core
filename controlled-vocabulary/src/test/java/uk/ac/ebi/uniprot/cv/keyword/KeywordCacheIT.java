package uk.ac.ebi.uniprot.cv.keyword;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class KeywordCacheIT {
	private static List<KeywordDetail> keywords;
	
	@BeforeAll
	static void setup() {
		keywords = KeywordCache.INSTANCE.get("");
	}
	
	
	@Test
	void testWithMultiParents() {
		 String acc = "KW-0869" ;
		 Optional<KeywordDetail> opVal=
				 keywords.stream().filter(val -> val.getKeyword().getAccession().equals(acc))
		 .findFirst();
		 assertTrue(opVal.isPresent());
		 List<KeywordDetail> hi = opVal.get().getParents();	
		 System.out.println("testWithMultiParents");
		 hi.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));
		// System.out.println(opVal.get().getCategory().getAccession() + "\t" + opVal.get().getCategory().getId());
		 
	}
	@Test
	void testCategory() {
		 String acc = "KW-9990" ;
		 Optional<KeywordDetail> opVal=
				 keywords.stream().filter(val -> val.getKeyword().getAccession().equals(acc))
		 .findFirst();
		 assertTrue(opVal.isPresent());
		 List<KeywordDetail> hi = opVal.get().getParents();	
		 assertNull(hi);
		 List<KeywordDetail> children = opVal.get().getChildren();
		 System.out.println("testCategory");
		 children.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));
		// System.out.println(opVal.get().getCategory().getAccession() + "\t" + opVal.get().getCategory().getId());
		 
	}
	
	@Test
	void testWithParentsAndChildren() {
		 String acc = "KW-0540" ;
		 Optional<KeywordDetail> opVal=
				 keywords.stream().filter(val -> val.getKeyword().getAccession().equals(acc))
		 .findFirst();
		 assertTrue(opVal.isPresent());
		 System.out.println("testWithParentsAndChildren");
		 List<KeywordDetail> parents = opVal.get().getParents();	
		 System.out.println("Parents");
		 parents.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));
		 List<KeywordDetail> children = opVal.get().getChildren();
		 System.out.println("Children");
		 children.forEach(val -> System.out.println(val.getKeyword().getAccession() + "\t" + val.getKeyword().getId()));
		// System.out.println(opVal.get().getCategory().getAccession() + "\t" + opVal.get().getCategory().getId());
		 
	}

}
