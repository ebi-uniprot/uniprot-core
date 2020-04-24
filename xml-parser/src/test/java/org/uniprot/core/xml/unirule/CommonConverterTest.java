package org.uniprot.core.xml.unirule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.uniprot.core.xml.Converter;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNull;

public class CommonConverterTest {

    void testObjectToXml(){
        // create object using builder test class create object method
        // call toXml method
        // get xml type
        // check xmltype object is not null
        // call each getter and check value is not null
    }

    void testXmlToObject(){
        // create xmlType using converter test class create object method
        // call fromXml method
        // get uniObject type
        // check uniObject object is not null
        // call each getter and check value is not null
    }

    @DisplayName("Test convert null uniObj to xmlObject")
    @ParameterizedTest(name = "[{index}] using converter ''{0}''")
    @MethodSource("provideConverterClass")
    void testNullObjectToXml(Class<? extends Converter> converterClass) throws Exception {
        // get no-arg converter constructor
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        // instantiate the object
        Converter converter = noArgConstructor.newInstance();
        Object xmlObject = converter.toXml(null);
        assertNull(xmlObject);
    }

    @DisplayName("Test convert null xmlObj to uniObj")
    @ParameterizedTest(name = "[{index}] using converter ''{0}''")
    @MethodSource("provideConverterClass")
    void testNullXmlToObject(Class<? extends Converter> converterClass) throws Exception{
        // get no-arg converter constructor
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        // instantiate the converter object
        Converter converter = noArgConstructor.newInstance();
        Object uniObj = converter.fromXml(null);
        assertNull(uniObj);
    }

    static Stream<Arguments> provideConverterClass() {
        return Stream.of(
                Arguments.of(FusionConverter.class),
                Arguments.of(MultiValueConverter.class),
                Arguments.of(InformationConverter.class));
    }


}
