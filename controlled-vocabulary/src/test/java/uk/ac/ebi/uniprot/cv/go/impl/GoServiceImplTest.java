package uk.ac.ebi.uniprot.cv.go.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.cv.go.GoTerm;

class GoServiceImplTest {
	static GoServiceImpl goService;
	@BeforeAll
	static void setup() {
		String filepath = "src/main/resources/go";
		goService = new GoServiceImpl(filepath);
	}
	@Test
	void testGO_0003674() {
		String id = "GO:0003674";
		GoTerm goTerm = goService.getGoTermById(id);
		assertNotNull(goTerm);
		List<String> isA = goService.getIsAById(id);
		assertNull(isA);
		List<String> partOf = goService.getPartOfById(id);
		assertNull(partOf);
		List<String> children = goService.getChildrenById(id);
		assertNotNull(children);
		assertFalse(children.isEmpty());
	}
	@Test
	void testGO_0003824() {
		String id = "GO:0003824";
		GoTerm goTerm = goService.getGoTermById(id);
		assertNotNull(goTerm);
		List<String> isA = goService.getIsAById(id);
		assertNotNull(isA);
		assertFalse(isA.isEmpty());
		List<String> partOf = goService.getPartOfById(id);
		assertNull(partOf);
		List<String> children = goService.getChildrenById(id);
		assertNotNull(children);
		assertFalse(children.isEmpty());
	}
}
