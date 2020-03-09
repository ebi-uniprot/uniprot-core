package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.impl.KeywordBuilder;

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
        keywords.add(new KeywordBuilder().id("KW-0002").name("3D-structure").build());
        keywords.add(new KeywordBuilder().id("KW-0106").name("Calcium").build());
        EntryKeywordMap dl = new EntryKeywordMap(keywords);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("KW-0002; KW-0106", "keywordid", result);
        verify("3D-structure;Calcium", "keyword", result);
    }

    private void verify(String expected, String field, Map<String, String> result) {
        String evaluated = result.get(field);
        assertEquals(expected, evaluated);
    }
}
