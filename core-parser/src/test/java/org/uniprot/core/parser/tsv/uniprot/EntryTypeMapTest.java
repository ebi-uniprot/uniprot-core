package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;

class EntryTypeMapTest {
    @Test
    void testFields() {
        List<String> fields = EntryTypeMap.FIELDS;
        List<String> expected = Collections.singletonList("reviewed");
        assertEquals(expected, fields);
    }

    @Test
    void testSWISSPROT() {
        UniProtKBEntryType info = UniProtKBEntryType.SWISSPROT;
        EntryTypeMap downloadable = new EntryTypeMap(info);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(1, result.size());
        verify("reviewed", "reviewed", result);
    }

    @Test
    void testTREMBL() {
        UniProtKBEntryType info = UniProtKBEntryType.TREMBL;
        EntryTypeMap downloadable = new EntryTypeMap(info);
        Map<String, String> result = downloadable.attributeValues();
        assertEquals(1, result.size());
        verify("reviewed", "unreviewed", result);
    }

    private void verify(String field, String expected, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }
}
