package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcSequenceMapTest {

    @Test
    void testAttributeValues() {
        String seq = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
        String checksum = "0C69420967F56414";
        int length = 40;

        Sequence sequence = new SequenceBuilder(seq).build();

        UniParcSequenceMap seqMap = new UniParcSequenceMap(sequence);

        Map<String, String> result = seqMap.attributeValues();
        assertEquals(3, result.size());
        assertEquals("" + length, result.get("length"));
        assertEquals(checksum, result.get("checksum"));
        assertEquals(seq, result.get("sequence"));
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("gene", "upi", "length");
        assertTrue(UniParcSequenceMap.contains(fields));
    }

    @Test
    void testFields() {
        List<String> fields = Arrays.asList("checksum", "length", "sequence");
        assertEquals(UniParcSequenceMap.FIELDS, fields);
    }
}
