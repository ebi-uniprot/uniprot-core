package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class MultiValueConverterTest {
    private static ObjectFactory objectFactory;
    private static MultiValueConverter converter;
    private static String random;

    @BeforeAll
    static void setUp() {
        objectFactory = getObjectFactory();
        converter = new MultiValueConverter(objectFactory);
        random = UUID.randomUUID().toString().substring(0, 4);
    }

    @Test
    void testFromNullXmlToNullObj() {
        List<String> uniObj = converter.fromXml(null);
        assertNull(uniObj);
    }

    @Test
    void testXmlToObject() {
        MultiValueType xmlType = createObject();
        List<String> uniObj = converter.fromXml(xmlType);
        assertNotNull(uniObj);
        assertEquals(xmlType.getValue(), uniObj);
    }

    @Test
    void testObjectToXml() {
        List<String> uniObj =
                IntStream.rangeClosed(1, 3)
                        .mapToObj(i -> i + "-val-" + random)
                        .collect(Collectors.toList());
        MultiValueType xmlType = converter.toXml(uniObj);
        assertNotNull(xmlType);
        assertEquals(uniObj, xmlType.getValue());
    }

    @ParameterizedTest
    @NullSource
    void testNullObjectToXml(List<String> uniObj) {
        MultiValueType xmlType = converter.toXml(uniObj);
        assertNull(xmlType);
    }

    @ParameterizedTest
    @EmptySource
    void testEmptyObjectToXml(List<String> uniObj) {
        MultiValueType xmlType = converter.toXml(uniObj);
        assertNotNull(xmlType);
        assertTrue(xmlType.getValue().isEmpty());
    }

    public static MultiValueType createObject(int listSize) {
        MultiValueType multiValueType = getObjectFactory().createMultiValueType();
        String random = UUID.randomUUID().toString().substring(0, 4);
        List<String> values =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "-val-" + random)
                        .collect(Collectors.toList());

        multiValueType.getValue().addAll(values);

        return multiValueType;
    }

    public static MultiValueType createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<MultiValueType> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }

    private static ObjectFactory getObjectFactory() {
        if (objectFactory == null) {
            objectFactory = new ObjectFactory();
        }
        return objectFactory;
    }
}
