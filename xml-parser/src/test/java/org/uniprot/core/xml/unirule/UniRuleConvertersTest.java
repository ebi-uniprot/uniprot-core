package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.jvnet.jaxb2_commons.lang.Equals2;
import org.uniprot.core.uniprotkb.comment.impl.*;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.*;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.*;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author sahmad The test has been written using reflection because all the tests for converter
 *     would be doing the same things: 1. create uniObject of a type and call Convert's toXml method
 *     to convert to xml and verify the fields are correctly set. 2. create xmlObject of a xml type
 *     and call Convert's fromXml method to convert to uniObj and verify the fields are correctly
 *     set.
 */
public class UniRuleConvertersTest extends AbstractUniRuleConvertersTest {
    /**
     * This parametrized test method tests conversion of uniObj to xmlObject. To test conversion
     * from a new uniObj type to xmlObj see {@link
     * UniRuleConvertersTestHelper#provideBuilderTestAndConverterClass()} to add new Arguments
     *
     * @param builderTestClass Builder's test Class where method
     *     <p>createObject is defined See sample BuilderTest
     *     <p>createObject method in {@link PositionalFeatureBuilderTest#createObject()}
     * @param converterClass Converter's Class where
     *     <p>toXml is defined.
     * @throws Exception Mostly Reflection related
     */
    @DisplayName("Test convert uniObject to xmlObject")
    @ParameterizedTest(name = "[{index}] using converter ''{1}''")
    @MethodSource(
            "org.uniprot.core.xml.unirule.UniRuleConvertersTestHelper#provideBuilderTestAndConverterClass")
    void testObjectToXml(Class builderTestClass, Class<? extends Converter> converterClass)
            throws Exception {
        // create uni object using builder test class' createObject method
        Method createObjectMethod = builderTestClass.getMethod("createObject");
        Object uniObj = createObjectMethod.invoke(null);
        assertNotNull(uniObj);
        uniObj =
                updateObject(
                        uniObj); // update the object in a particular way to execute the business
        // logic
        // create converter object using no-arg constructor and call toXml method
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        Converter converter = noArgConstructor.newInstance();
        Object xmlObject = converter.toXml(uniObj);
        assertNotNull(xmlObject);
        // convert xmlObject to a uniObject and verify round-trip
        Object convertedUniObject = converter.fromXml(xmlObject);
        assertEquals(uniObj, convertedUniObject);
        verifyBusinessConstraints(xmlObject);
        verifyBean(xmlObject);
    }

    /**
     * This parametrized test method tests conversion of xmlObject to uniObject. To test conversion
     * from a new xmlObj type to uniObj see {@link
     * UniRuleConvertersTestHelper#provideConverterXMLAndTestClass()} to add new Arguments
     *
     * @param converterClass The converter's Class where fromXml method is defined
     * @param converterTestClass The convertertest Class where the method
     *     <p>createObject is defined See sample ConverterTest's
     *     <p>createObject method in {@link InformationConverterTest#createObject()}
     * @param xmlClass The jaxb generated XML class's Class which is being converted to uniObj
     * @throws Exception
     */
    @DisplayName("Test convert xmlObject to uniObject")
    @ParameterizedTest(name = "[{index}] using converter ''{0}''")
    @MethodSource(
            "org.uniprot.core.xml.unirule.UniRuleConvertersTestHelper#provideConverterXMLAndTestClass")
    void testFromXmlToObject(
            Class<? extends Converter> converterClass,
            Class<? extends AbstractConverterTest> converterTestClass,
            Class<? extends Equals2> xmlClass)
            throws Exception {
        // create xmlType using converter test class createObject method
        Method createObjectMethod = converterTestClass.getMethod("createObject");
        Object xmlObject = createObjectMethod.invoke(null);
        assertNotNull(xmlObject);
        // create converter object using no-arg constructor and call toXml method
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        Converter converter = noArgConstructor.newInstance();
        Object uniObject = converter.fromXml(xmlObject);
        assertNotNull(uniObject);
        // populate diseasecomment note. parent interface doesnt have note field
        if (uniObject instanceof DiseaseCommentImpl) {
            uniObject =
                    DiseaseCommentBuilder.from((DiseaseCommentImpl) uniObject)
                            .note(new NoteBuilder(null).build())
                            .build();
        }
        verifyBean(uniObject);
    }

    /**
     * This method tests to convert null uniObj to xmlObject. The code should handle gracefully.
     *
     * @param converterClass The converter's Class where toXml method is defined
     * @throws Exception
     */
    @DisplayName("Test convert null uniObj to xmlObject")
    @ParameterizedTest(name = "[{index}] using converter ''{0}''")
    @MethodSource("org.uniprot.core.xml.unirule.UniRuleConvertersTestHelper#provideConverterClass")
    void testNullObjectToXml(Class<? extends Converter> converterClass) throws Exception {
        // get no-arg converter constructor
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        // instantiate the object
        Converter converter = noArgConstructor.newInstance();
        Object xmlObject = converter.toXml(null);
        assertNull(xmlObject);
    }

    /**
     * This method tests to convert null xmlObject to uniObj. The code should handle gracefully.
     *
     * @param converterClass The converter's Class where fromXml method is defined
     * @throws Exception
     */
    @DisplayName("Test convert null xmlObj to uniObj")
    @ParameterizedTest(name = "[{index}] using converter ''{0}''")
    @MethodSource("org.uniprot.core.xml.unirule.UniRuleConvertersTestHelper#provideConverterClass")
    void testNullXmlToObject(Class<? extends Converter> converterClass) throws Exception {
        // get no-arg converter constructor
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        // instantiate the converter object
        Converter converter = noArgConstructor.newInstance();
        Object uniObj = converter.fromXml(null);
        assertNull(uniObj);
    }
}
