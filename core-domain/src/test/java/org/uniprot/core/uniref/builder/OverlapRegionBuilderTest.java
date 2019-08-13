package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.OverlapRegion;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

class OverlapRegionBuilderTest {

	@Test
	void testFrom() {
		String name ="some name";
		int start = 50;
		int end = 65;
		
		OverlapRegion overlapRegion = new OverlapRegionBuilder().name(name)
				.start(start).end(end)
				.build();
		OverlapRegion overlapRegion2 = new OverlapRegionBuilder().from(overlapRegion).build();
		assertEquals(overlapRegion, overlapRegion2);
	}

	@Test
	void testName() {
		String name ="some name";
		
		OverlapRegion overlapRegion = new OverlapRegionBuilder().name(name).build();
		assertEquals(name, overlapRegion.getName());
	}

	@Test
	void testStartEnd() {
		String name ="some name";
		int start = 50;
		int end = 65;
		
		OverlapRegion overlapRegion = new OverlapRegionBuilder().name(name)
				.start(start).end(end)
				.build();
		assertEquals(name, overlapRegion.getName());
		assertEquals(start, overlapRegion.getStart());
		assertEquals(end, overlapRegion.getEnd());
	}

}

