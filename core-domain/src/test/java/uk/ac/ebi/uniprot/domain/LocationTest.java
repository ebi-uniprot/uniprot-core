package uk.ac.ebi.uniprot.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
*/

class LocationTest {

	@Test
	void testLocationIntInt() {
		Location location = new Location(23, 91);
		assertEquals(23, location.getStart());
		assertEquals(91, location.getEnd());
	}

	
}

