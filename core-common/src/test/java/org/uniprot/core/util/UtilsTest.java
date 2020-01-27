package org.uniprot.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UtilsTest {

    @Nested
    class Strings {
        @Nested
        class emptyOrString {

            @Test
            void whenNull_returnEmptyString() {
                assertEquals("", Utils.emptyOrString(null));
            }

            @ParameterizedTest
            @ValueSource(
                    strings = {
                        "a",
                        "AB",
                        "a%^b",
                        "()+^£$^%%",
                        "aB",
                        "Ab",
                        "",
                        "null",
                        "   ",
                        "123432"
                    })
            void notNull_returnAsItIs(String test) {
                assertEquals(test, Utils.emptyOrString(test));
            }
        }

        @Nested
        class nullOrEmpty {
            @Test
            void whenNull_returnTrue() {
                assertTrue(Utils.nullOrEmpty((String) null));
            }

            @Test
            void whenEmpty_returnTrue() {
                assertTrue(Utils.nullOrEmpty(""));
            }

            @ParameterizedTest
            @ValueSource(
                    strings = {
                        "1",
                        "AB",
                        "a%^b",
                        "()+^£$^%%",
                        "aB",
                        "Ab",
                        " s ",
                        "NULL",
                        "   ",
                        "123432"
                    })
            void nonEmpty_shouldAlwaysReturnFalse(String test) {
                assertFalse(Utils.nullOrEmpty(test));
            }
        }

        @Nested
        class upperFirstChar {
            @Test
            void whenNullOrEmpty_returnSame() {
                assertAll(
                        () -> assertNull(Utils.upperFirstChar(null)),
                        () -> assertEquals("", Utils.upperFirstChar("")));
            }

            @Test
            void capitalizeFirstChar() {
                assertAll(
                        () -> assertEquals("Protein", Utils.upperFirstChar("protein")),
                        () -> assertEquals(" protein", Utils.upperFirstChar(" protein")),
                        () -> assertEquals("Protein abc", Utils.upperFirstChar("protein abc")),
                        () -> assertEquals("$protein", Utils.upperFirstChar("$protein")),
                        () -> assertEquals("P!rotein", Utils.upperFirstChar("p!rotein")),
                        () -> assertEquals("Protein Def", Utils.upperFirstChar("protein Def")),
                        () -> assertEquals("12345", Utils.upperFirstChar("12345")));
            }
        }

        @Nested
        class lowerFirstChar {
            @Test
            void whenNullOrEmpty_returnSame() {
                assertAll(
                        () -> assertNull(Utils.lowerFirstChar(null)),
                        () -> assertEquals("", Utils.lowerFirstChar("")));
            }

            @Test
            void uncapitalizeFirstChar() {
                String str = "Protein";
                String result = Utils.lowerFirstChar(str);
                assertEquals("protein", result);
                assertAll(
                        () -> assertEquals("protein", Utils.lowerFirstChar("Protein")),
                        () -> assertEquals(" protein", Utils.lowerFirstChar(" protein")),
                        () -> assertEquals("protein abc", Utils.lowerFirstChar("Protein abc")),
                        () -> assertEquals("$protein", Utils.lowerFirstChar("$protein")),
                        () -> assertEquals("p!rotein", Utils.lowerFirstChar("P!rotein")),
                        () -> assertEquals("protein Def", Utils.lowerFirstChar("Protein Def")),
                        () -> assertEquals("12345", Utils.lowerFirstChar("12345")));
            }
        }

        @Nested
        class notNullOrEmpty {

            @Test
            void whenNullOrEmpty_returnFalse() {
                assertAll(
                        () -> assertFalse(Utils.notNullOrEmpty((String) null)),
                        () -> assertFalse(Utils.notNullOrEmpty("")));
            }

            @ParameterizedTest
            @ValueSource(
                    strings = {
                        "1",
                        "AB",
                        "a%^b",
                        "()+^£$^%%",
                        "aB",
                        "Ab",
                        " s ",
                        "NULL",
                        "   ",
                        "123432"
                    })
            void nonEmpty_shouldAlwaysReturnTrue(String test) {
                assertTrue(Utils.notNullOrEmpty(test));
            }
        }
    }

    @Nested
    class Lists {
        @Nested
        class notNullOrEmpty {
            @Test
            void whenNullOrEmpty_returnFalse() {
                assertAll(
                        () -> assertFalse(Utils.notNullOrEmpty((Collection) null)),
                        () -> assertFalse(Utils.notNullOrEmpty(Collections.emptyList())));
            }

            @Test
            void nonEmpty_shouldAlwaysReturnTrue() {
                List<String> list = new LinkedList<>();
                list.add("test1");
                list.add("test2");
                assertAll(
                        () -> assertTrue(Utils.notNullOrEmpty(Collections.singletonList(123))),
                        () ->
                                assertTrue(
                                        Utils.notNullOrEmpty(
                                                Collections.checkedList(list, String.class))),
                        () -> assertTrue(Utils.notNullOrEmpty(list)),
                        () -> assertTrue(Utils.notNullOrEmpty(Collections.synchronizedList(list))),
                        () -> assertTrue(Utils.notNullOrEmpty(Collections.unmodifiableList(list))));
            }
        }

        @Nested
        class nullOrEmpty {
            @Test
            void whenNullOrEmpty_returnTrue() {
                assertTrue(Utils.nullOrEmpty((Collection) null));
                assertTrue(Utils.nullOrEmpty(Collections.emptyList()));
            }

            @Test
            void nonEmpty_shouldAlwaysReturnFalse() {
                List<Integer> list = new LinkedList<>();
                list.add(-12321);
                list.add(454353);
                assertAll(
                        () -> assertFalse(Utils.nullOrEmpty(Collections.singletonList("test"))),
                        () ->
                                assertFalse(
                                        Utils.nullOrEmpty(
                                                Collections.checkedList(list, Integer.class))),
                        () -> assertFalse(Utils.nullOrEmpty(list)),
                        () -> assertFalse(Utils.nullOrEmpty(Collections.synchronizedList(list))),
                        () -> assertFalse(Utils.nullOrEmpty(Collections.unmodifiableList(list))));
            }
        }

        @Nested
        class unmodifiableList {
            @Test
            void passingNullReturnEmptyList() {
                List l = Utils.unmodifiableList(null);
                assertTrue(l.isEmpty());
            }

            @Test
            void passing_emptyList_returnEmptyList() {
                List l = Utils.unmodifiableList(new ArrayList<>());
                assertTrue(l.isEmpty());
            }

            @Test
            void passingNullReturnEmptyList_unmodifiable() {
                List<String> l = Utils.unmodifiableList(null);
                assertThrows(UnsupportedOperationException.class, () -> l.add("abc"));
            }

            @Test
            void passing_emptyList_returnEmptyList_unmodifiable() {
                List<String> l = Utils.unmodifiableList(new ArrayList<>());
                assertThrows(UnsupportedOperationException.class, () -> l.add("abc"));
            }

            @Test
            void passingList_returnUnmodifiable() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                List<String> l = Utils.unmodifiableList(list);
                assertThrows(UnsupportedOperationException.class, () -> l.add("c"));
            }
        }

        @Nested
        class addOrIgnoreNull {
            @Test
            void addingNullValueInNullList_nullList() {
                List<String> l = null;
                Utils.addOrIgnoreNull(null, l);
                assertNull(l);
            }

            @Test
            void addingNotNullValueInNullList_NPE() {
                List<String> l = null;
                assertThrows(NullPointerException.class, () -> Utils.addOrIgnoreNull("test", l));
            }

            @Test
            void nonNulValue() {
                List<String> l = new ArrayList<>();
                Utils.addOrIgnoreNull("abc", l);
                assertNotNull(l);
                assertEquals(1, l.size());
                assertEquals("abc", l.get(0));
            }
        }

        @Nested
        class emptyOrList {
            @Test
            void whenNull_returnsEmptyList() {
                assertAll(
                        () -> assertNotNull(Utils.emptyOrList((null))),
                        () -> assertTrue(Utils.emptyOrList((null)).isEmpty()));
            }

            @Test
            void whenNotNull_shouldReturnActualList() {
                List<String> list = new LinkedList<>();
                list.add("test1");
                list.add("test2");
                List<String> synchronizedList = Collections.synchronizedList(list);
                List<String> unmodifiableList = Collections.unmodifiableList(list);
                List<String> checkedList = Collections.checkedList(list, String.class);
                List<Integer> singletonList = Collections.singletonList(123);
                assertAll(
                        () -> assertSame(singletonList, Utils.emptyOrList(singletonList)),
                        () -> assertSame(checkedList, Utils.emptyOrList(checkedList)),
                        () -> assertSame(list, Utils.emptyOrList(list)),
                        () -> assertSame(synchronizedList, Utils.emptyOrList(synchronizedList)),
                        () -> assertSame(unmodifiableList, Utils.emptyOrList(unmodifiableList)));
            }
        }

        @Nested
        class modifiableList {
            @Test
            void whenNull_returnsEmptyList() {
                assertAll(
                        () -> assertNotNull(Utils.modifiableList((null))),
                        () -> assertTrue(Utils.modifiableList((null)).isEmpty()));
            }

            @Test
            void canAddElementsInEmptyList() {
                List<String> nullList = null;
                List<String> list = Utils.modifiableList(nullList);
                list.add("a");
                assertAll(
                        () -> assertEquals("a", list.get(0)),
                        () -> assertFalse(list.isEmpty()),
                        () -> assertNotSame(nullList, list));
            }

            @Test
            void whenUnModifiable_returnModifiable() {
                List<String> list = new LinkedList<>();
                list.add("test1");
                list.add("test2");
                List<String> unmodifiableList = Collections.unmodifiableList(list);
                assertThrows(
                        UnsupportedOperationException.class,
                        () -> unmodifiableList.add("shouldNotBeAdded"));

                assertAll(
                        () -> assertNotSame(list, Utils.modifiableList(list)),
                        () ->
                                assertNotSame(
                                        unmodifiableList, Utils.modifiableList(unmodifiableList)),
                        () -> {
                            List<String> retList = Utils.modifiableList(unmodifiableList);
                            retList.add("1");
                            assertEquals(3, retList.size());
                            assertEquals("1", retList.get(2));
                        });
            }
        }
    }

    @Nested
    class Maps {

        @Nested
        class notNullOrEmpty {
            @Test
            void whenNullOrEmpty_returnFalse() {
                assertAll(
                        () -> assertFalse(Utils.notNullOrEmpty((Map) null)),
                        () -> assertFalse(Utils.notNullOrEmpty(Collections.emptyMap())));
            }

            @Test
            void nonEmpty_shouldAlwaysReturnTrue() {
                Map<String, Boolean> map = new HashMap<>();
                map.put("test1", true);
                map.put("test2", false);
                assertAll(
                        () -> assertTrue(Utils.notNullOrEmpty(Collections.singletonMap(1, 2))),
                        () ->
                                assertTrue(
                                        Utils.notNullOrEmpty(
                                                Collections.checkedMap(
                                                        map, String.class, Boolean.class))),
                        () -> assertTrue(Utils.notNullOrEmpty(map)),
                        () -> assertTrue(Utils.notNullOrEmpty(Collections.synchronizedMap(map))),
                        () -> assertTrue(Utils.notNullOrEmpty(Collections.unmodifiableMap(map))));
            }
        }

        @Nested
        class nullOrEmpty {
            @Test
            void whenNullOrEmpty_returnTrue() {
                assertTrue(Utils.nullOrEmpty((Map) null));
                assertTrue(Utils.nullOrEmpty(Collections.emptyMap()));
            }

            @Test
            void nonEmpty_shouldAlwaysReturnFalse() {
                Map<Integer, Double> map = new HashMap<>();
                map.put(-12321, 1D);
                map.put(454353, 2D);
                assertAll(
                        () -> assertFalse(Utils.nullOrEmpty(Collections.singletonMap("test", 1))),
                        () ->
                                assertFalse(
                                        Utils.nullOrEmpty(
                                                Collections.checkedMap(
                                                        map, Integer.class, Double.class))),
                        () -> assertFalse(Utils.nullOrEmpty(map)),
                        () -> assertFalse(Utils.nullOrEmpty(Collections.synchronizedMap(map))),
                        () -> assertFalse(Utils.nullOrEmpty(Collections.unmodifiableMap(map))));
            }
        }
    }

    @Nested
    class Others {

        @Nested
        class notNull {

            @Test
            void objectsNull_ReturnFalse() {
                Object o = null;
                Collection c = null;
                Map m = null;
                Integer i = null;
                Pair p = null;
                assertAll(
                        () -> assertFalse(Utils.notNull(o)),
                        () -> assertFalse(Utils.notNull(c)),
                        () -> assertFalse(Utils.notNull(m)),
                        () -> assertFalse(Utils.notNull(i)),
                        () -> assertFalse(Utils.notNull(p)));
            }

            @Test
            void objectsNotNull_ReturnTrue() {
                Object o = new Object();
                Collection c = Collections.emptyList();
                Map m = Collections.emptyMap();
                Integer i = 5;
                Pair<Object, Collection> p = new PairImpl(o, c);
                assertAll(
                        () -> assertTrue(Utils.notNull(o)),
                        () -> assertTrue(Utils.notNull(c)),
                        () -> assertTrue(Utils.notNull(m)),
                        () -> assertTrue(Utils.notNull(i)),
                        () -> assertTrue(Utils.notNull(p)));
            }
        }

        @Nested
        class loadPropertyInput {
            @Test
            void canReadEveryThingFromInputStream() {
                String file = "abc";
                assertEquals(
                        "abc", Utils.loadPropertyInput(new ByteArrayInputStream(file.getBytes())));
            }

            @Test
            void nothingToRead_returnEmpty() {
                String file = "";
                assertEquals(
                        "", Utils.loadPropertyInput(new ByteArrayInputStream(file.getBytes())));
            }
        }
    }
}
