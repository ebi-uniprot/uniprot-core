package uk.ac.ebi.uniprot.domain.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

	@Test
	void testResetNull() {
		String value =null;
		String result = Utils.resetNull(value);
		assertEquals("", result);
		value ="some value";
		 result = Utils.resetNull(value);
		 assertTrue(value == result);
	}

	@Test
	void testUnmodifierList() {
		List<String> list = null;
		
		List<String> result = Utils.unmodifierList(list);
		assertTrue(result.isEmpty());
		
		list = Collections.emptyList();	
		 result = Utils.unmodifierList(list);
		assertTrue(result.isEmpty());
		
		list = Arrays.asList("val1", "val2");	
		 result = Utils.unmodifierList(list);
		assertEquals(list, result);
		
	}

}
