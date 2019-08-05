package org.uniprot.core.parser.tsv.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.parser.tsv.uniprot.EntrySequenceMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntrySequenceMapTest {
	@Test
	void testFields() {
		List<String> fields =  EntrySequenceMap.FIELDS;
		List<String> expected =Arrays.asList("sequence", "mass", "length");
		assertEquals(expected, fields);
	}
	@Test
	void testGetDataWithEmptySeq() {
		Sequence sequence = new SequenceBuilder("").build();
		EntrySequenceMap dl = new EntrySequenceMap(sequence);
		Map<String, String> result = dl.attributeValues();
		assertEquals(3, result.size());
		verify("sequence", "", result);
		verify("mass", "18", result);
		verify("length", "0", result);
	}
	private void verify(String field, String expected, Map<String, String> result) {
		assertEquals(expected, result.get(field));
	}
	
	@Test
	void testGetDataWithValidSequence() {
		Sequence sequence = new SequenceBuilder("AAAAAAAAAAAAAAA").build();
		EntrySequenceMap dl = new EntrySequenceMap(sequence);
		Map<String, String> result = dl.attributeValues();
		assertEquals(3, result.size());
		verify("sequence", "AAAAAAAAAAAAAAA", result);
		verify("mass", "1084", result);
		verify("length", "15", result);
	}

}
