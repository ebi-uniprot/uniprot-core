package org.uniprot.core.xml.unirule;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class MultiValueConverterTest extends AbstractConverterTest {
    private static MultiValueConverter converter;

    @BeforeAll
    static void setUp() {
        converter = new MultiValueConverter(uniRuleObjectFactory);
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

    public static MultiValueType createObject() {
        MultiValueType multiValueType = objectCreator.createLoremIpsumObject(MultiValueType.class);
        multiValueType.getValue().addAll(objectCreator.createLoremIpsumObject(StringList.class));
        return multiValueType;
    }

    public static class StringList extends ArrayList<String> {
    }
}
