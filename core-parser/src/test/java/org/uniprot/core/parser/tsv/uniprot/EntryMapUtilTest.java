package org.uniprot.core.parser.tsv.uniprot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class EntryMapUtilTest {

    @Test
    void testConvertMapToTSVString() {
        Map<String, Integer> map = new HashMap<>();
        map.put("open", 2);
        map.put("yaml", 1);
        map.put("ada", 12);
        map.put("Zoo", 100);
        String tsvString = EntryMapUtil.convertMapToTSVString(map);
        Assertions.assertEquals("Zoo (100); ada (12); open (2); yaml (1)", tsvString);
    }

    @Test
    void testConvertEmptyMapToTSVString() {
        Map<String, Integer> map = new HashMap<>();
        String tsvString = EntryMapUtil.convertMapToTSVString(map);
        Assertions.assertTrue(tsvString.isEmpty());
    }

    @Test
    void testConvertNullMapToTSVString() {
        Map<String, Integer> map = null;
        String tsvString = EntryMapUtil.convertMapToTSVString(map);
        Assertions.assertTrue(tsvString.isEmpty());
    }

    @Test
    void testConvertNullObjectToTSVString() {
        String tsvString = EntryMapUtil.convertToTSVString(null);
        Assertions.assertTrue(tsvString.isEmpty());
    }

    @Test
    void testConvertObjectToTSVString() {
        String tsvString = EntryMapUtil.convertToTSVString(new Object());
        Assertions.assertFalse(tsvString.isEmpty());
    }
}
