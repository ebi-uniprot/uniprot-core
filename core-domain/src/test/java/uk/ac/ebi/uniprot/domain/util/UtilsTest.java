package uk.ac.ebi.uniprot.domain.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

class UtilsTest {

    @Test
    void testResetNull() {
        String value = null;
        String result = Utils.nullToEmpty(value);
        assertEquals("", result);
        value = "some value";
        result = Utils.nullToEmpty(value);
        assertTrue(value == result);
    }

    @Test
    void testUnmodifierList() {
        List<String> list = null;

        List<String> result = Utils.nonNullUnmodifiableList(list);
        assertTrue(result.isEmpty());

        list = Collections.emptyList();
        result = Utils.nonNullUnmodifiableList(list);
        assertTrue(result.isEmpty());

        list = asList("val1", "val2");
        result = Utils.nonNullUnmodifiableList(list);
        assertEquals(list, result);

    }

    @Test
    void canSetList() {
        List<String> first = asList("one", "two");
        List<String> second = new ArrayList<>(asList("three", "four"));

        nonNullList(first);

        System.out.println(first);
        System.out.println(second);

    }

}
