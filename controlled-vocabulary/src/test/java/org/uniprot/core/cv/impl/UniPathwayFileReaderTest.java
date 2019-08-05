package org.uniprot.core.cv.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.impl.UniPathwayFileReader;
import org.uniprot.core.cv.pathway.UniPathway;

class UniPathwayFileReaderTest {
	private final UniPathwayFileReader reader = new UniPathwayFileReader();
	
	@Test
	void test() {
		String filename ="src/main/resources/unipathway.txt"; 
		List<UniPathway> unipathwayList = reader.parse(filename);
		assertNotNull(unipathwayList);
		assertFalse(unipathwayList.isEmpty());
		String id ="611.743";
		String text = "Alcohol metabolism; butanol biosynthesis";
		Optional<UniPathway> opUP = unipathwayList.stream().filter(val -> val.getAccession().equals(id))
				.findFirst();
		assertTrue(opUP.isPresent());
		assertEquals(text, opUP.get().getName());
		 String id2 = "411";
		 
		 opUP = unipathwayList.stream().filter(val -> val.getAccession().equals(id2))
					.findFirst();
		 
			assertTrue(opUP.isPresent());
			assertTrue(!opUP.get().getChildren().isEmpty());
		 
		
	}

}
