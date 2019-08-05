package org.uniprot.core.parser.tsv.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.parser.tsv.uniprot.EntryTypeMap;
import org.uniprot.core.uniprot.UniProtEntryType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntryTypeMapTest {
	@Test
	void testFields() {
		List<String> fields =  EntryTypeMap.FIELDS;
		List<String> expected = Collections.singletonList("reviewed");
		assertEquals(expected, fields);
	}
	
	@Test
	void testSWISSPROT() {
		UniProtEntryType info = UniProtEntryType.SWISSPROT;
		EntryTypeMap downloadable = new EntryTypeMap(info);
		Map<String, String> result = downloadable.attributeValues();
		assertEquals(1, result.size());
		verify("reviewed", "reviewed", result);
	}

	@Test
	void testTREMBL() {
		UniProtEntryType info = UniProtEntryType.TREMBL;
		EntryTypeMap downloadable = new EntryTypeMap(info);
		Map<String, String> result = downloadable.attributeValues();
		assertEquals(1, result.size());
		verify("reviewed", "unreviewed", result);
	}

	private void verify(String field, String expected, Map<String, String> result) {
		assertEquals(expected, result.get(field));
	}
}
