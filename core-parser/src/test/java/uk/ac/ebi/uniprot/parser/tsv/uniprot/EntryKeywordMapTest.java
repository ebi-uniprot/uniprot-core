package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.builder.KeywordBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntryKeywordMapTest {
	@Test
	void testFields() {
		List<String> fields = EntryKeywordMap.FIELDS;
		List<String> expected = Arrays.asList("keyword", "keywordid");
		assertEquals(expected, fields);
	}
	@Test
	void testMapEmpty() {
		EntryKeywordMap dl = new EntryKeywordMap(null);
		Map<String, String> result = dl.attributeValues();
		assertTrue(result.isEmpty());
	}
	@Test
	void testMap() {
		List<Keyword> keywords = new ArrayList<>();
		keywords.add(new KeywordBuilder().id("KW-0002").value("3D-structure").build());
		keywords.add(new KeywordBuilder().id("KW-0106").value("Calcium").build());
		EntryKeywordMap dl = new EntryKeywordMap(keywords);
		Map<String, String> result = dl.attributeValues();
		assertEquals(2, result.size());
		verify("KW-0002; KW-0106", "keywordid", result );
		verify("3D-structure;Calcium", "keyword", result );
	}
	
	private void verify(String expected, String field, Map<String, String> result) {
		String evaluated = result.get(field);
		assertEquals(expected, evaluated);
	}
}
