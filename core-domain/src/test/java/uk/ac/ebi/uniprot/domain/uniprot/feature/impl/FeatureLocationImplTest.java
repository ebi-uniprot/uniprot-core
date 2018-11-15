package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureLocationModifier;

class FeatureLocationImplTest {

	@Test
	void testExact() {
		int start = 34;
		int end = 56;
		FeatureLocation location = new FeatureLocationImpl(start, end);
		verifyFeatureLocation(location, start, end, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
	}

	@Test
	void testUnsureAndOutside() {
        Integer start = 22;
        Integer end = 43;
        FeatureLocationModifier mStart = FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE;
        FeatureLocationModifier mEnd = FeatureLocationModifier.UNSURE;
        FeatureLocation location = new FeatureLocationImpl(start, end, mStart, mEnd);
        verifyFeatureLocation(location, start, end, mStart, mEnd);
	}
	
	@Test
	void testExactAndUnknown() {
        Integer start = null;
        Integer end = 43;
        FeatureLocationModifier mStart = FeatureLocationModifier.UNKOWN;
        FeatureLocationModifier mEnd = FeatureLocationModifier.EXACT;
        FeatureLocation location = new FeatureLocationImpl(start, end, mStart, mEnd);
        verifyFeatureLocation(location, start, end, mStart, mEnd);
	}
	private void verifyFeatureLocation(FeatureLocation flocation, Integer start, Integer end,
			FeatureLocationModifier mStart, FeatureLocationModifier mEnd) {
		assertEquals(start, flocation.getStart());
		assertEquals(end, flocation.getEnd());
		assertEquals(mStart, flocation.getStartModifier());
		assertEquals(mEnd, flocation.getEndModifier());
		TestHelper.verifyJson(flocation);
	}

}
