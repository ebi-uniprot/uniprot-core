package org.uniprot.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PairImplTest {

    @Test
    void canCreatePair() {
        Pair<String, String> pair = new PairImpl<>("key", "value");
        assertEquals("key", pair.getKey());
        assertEquals("value", pair.getValue());
    }

    @Test
    void sameKeyValue_differentObject_hashCodeSame() {
        Pair<String, String> pair1 = new PairImpl<>("key", "value");
        Pair<String, String> pair2 = new PairImpl<>("key", "value");
        assertEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    void callingEqual_passingSameObject() {
        Pair<Integer, Integer> pair = new PairImpl<>(1, 2);
        assertEquals(pair, pair);
    }

    @Test
    void nullAndObjectNeverEqual() {
        Pair<Float, Float> pair = new PairImpl<>(1.0F, 2.0F);
        assertFalse(pair.equals(null));
    }

    @Test
    void simpleObjectAndPairCanNeverBeSame() {
        PairImpl<Boolean, Boolean> pair = new PairImpl<>(false, false);
        assertFalse(pair.equals(Boolean.FALSE));
    }

    @Test
    void sameKeyValue_differentObject_shouldBeEqual() {
        Pair<String, String> pair1 = new PairImpl<>("key", "value");
        Pair<String, String> pair2 = new PairImpl<>("key", "value");
        assertEquals(pair1, pair2);
        assertEquals(pair2, pair1);
    }

    @Test
    void nullKeyValue_differentObject_shouldBeEqual() {
        Pair<String, String> pair1 = new PairImpl<>(null, null);
        Pair<String, String> pair2 = new PairImpl<>(null, null);
        assertEquals(pair1, pair2);
        assertEquals(pair2, pair1);
    }

    @Test
    void nullAndEmptyKey_differentObject_shouldNotBeEqual() {
        Pair<String, String> pair1 = new PairImpl<>(null, null);
        Pair<String, String> pair2 = new PairImpl<>("", null);
        assertFalse(pair1.equals(pair2));
    }

    @Test
    void keyShouldBeSame_toBeEqual() {
        Pair<Boolean, String> pair1 = new PairImpl<>(true, "value");
        Pair<Boolean, String> pair2 = new PairImpl<>(false, "value");
        assertNotEquals(pair1, pair2);
        assertNotEquals(pair2, pair1);
    }
}
