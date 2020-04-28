package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.jvnet.jaxb2_commons.lang.Equals2;
import org.uniprot.core.RangeTest;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilderTest;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.impl.*;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;

public class CommonConverterTest {
    private static List<String> skippedJaxbFields = new ArrayList<>();

    static {
        skippedJaxbFields.add("absorption");
        skippedJaxbFields.add("isoform");
        skippedJaxbFields.add("link");
        skippedJaxbFields.add("method");
        skippedJaxbFields.add("location");
        skippedJaxbFields.add("mass");
        skippedJaxbFields.add("locationType");
        skippedJaxbFields.add("kinetics");
        skippedJaxbFields.add("conflict");
        skippedJaxbFields.add("error");
        skippedJaxbFields.add("experiments");
        skippedJaxbFields.add("cofactor"); // Put this exception with class type
        skippedJaxbFields.add("positionalFeature");
        skippedJaxbFields.add("annotation");
        skippedJaxbFields.add("signal");
        skippedJaxbFields.add("event");
        skippedJaxbFields.add("transmembrane");
        skippedJaxbFields.add("interactant");
        skippedJaxbFields.add("coiledCoil");
    }

    @DisplayName("Test convert uniObject to xmlObject")
    @ParameterizedTest(name = "[{index}] using converter ''{1}''")
    @MethodSource("provideBuilderTestAndConverterClass")
    void testObjectToXml(Class builderTestClass, Class<? extends Converter> converterClass)
            throws Exception {
        // create object using builder test class createObject method
        Method createObjectMethod = builderTestClass.getMethod("createObject");
        Object uniObj = createObjectMethod.invoke(null);
        assertNotNull(uniObj);
        // create converter object using no-arg constructor and call toXml method
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        Converter converter = noArgConstructor.newInstance();
        Object xmlObject = converter.toXml(uniObj);
        assertNotNull(xmlObject);
        // call each getter and check value is not null
        for (PropertyDescriptor pd :
                Introspector.getBeanInfo(xmlObject.getClass()).getPropertyDescriptors()) {
            if (canSkip(pd)) {
                Object fieldVal = pd.getReadMethod().invoke(xmlObject);
                assertNotNull(
                        fieldVal, xmlObject.getClass() + " field `" + pd.getName() + "` is null");
                if (fieldVal instanceof Collection) {
                    assertFalse(((List) fieldVal).isEmpty(), pd.getName() + " is empty");
                } else if (fieldVal instanceof Map) {
                    assertFalse(((Map) fieldVal).isEmpty(), pd.getName() + " is empty");
                }
            }
        }
    }

    @DisplayName("Test convert xmlObject to uniObject")
    @ParameterizedTest(name = "[{index}] using converter ''{1}''")
    @MethodSource("provideConverterAndXMLClass")
    void testFromXmlToObject(
            Class<? extends Converter> converterClass, Class<? extends Equals2> xmlClass) {
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
    void testNullXmlToObject(Class<? extends Converter> converterClass) throws Exception {
        // get no-arg converter constructor
        Constructor<? extends Converter> noArgConstructor = converterClass.getConstructor();
        // instantiate the converter object
        Converter converter = noArgConstructor.newInstance();
        Object uniObj = converter.fromXml(null);
        assertNull(uniObj);
    }

    static Stream<Arguments> provideConverterClass() {
        return Stream.of(
                Arguments.of(AnnotationConverter.class),
                Arguments.of(CaseTypeConverter.class),
                Arguments.of(CommentConverter.class),
                Arguments.of(ConditionConverter.class),
                Arguments.of(ConditionSetConverter.class),
                Arguments.of(ConditionValueConverter.class),
                Arguments.of(FtagConditionConverter.class),
                Arguments.of(FusionConverter.class),
                Arguments.of(InformationConverter.class),
                Arguments.of(MainTypeConverter.class),
                Arguments.of(MultiValueConverter.class),
                Arguments.of(PositionalFeatureConverter.class),
                Arguments.of(PositionalFeatureSetConverter.class),
                Arguments.of(ProteinConverter.class),
                Arguments.of(RangeConverter.class),
                Arguments.of(RuleExceptionConverter.class),
                Arguments.of(RuleStatusConverter.class),
                Arguments.of(SamFeatureSetConverter.class),
                Arguments.of(SamTriggerConverter.class),
                Arguments.of(UniProtKBAccessionConverter.class),
                Arguments.of(UniRuleEntryConverter.class));
    }

    static Stream<Arguments> provideBuilderTestAndConverterClass() {
        return Stream.of(
                Arguments.of(AnnotationBuilderTest.class, AnnotationConverter.class),
                Arguments.of(CaseRuleBuilderTest.class, CaseTypeConverter.class),
                //                Arguments.of(DiseaseCommentBuilderTest.class,
                // CommentConverter.class),
                Arguments.of(ConditionBuilderTest.class, ConditionConverter.class),
                Arguments.of(ConditionSetBuilderTest.class, ConditionSetConverter.class),
                Arguments.of(ConditionValueBuilderTest.class, ConditionValueConverter.class),
                Arguments.of(FtagConditionValueBuilderTest.class, FtagConditionConverter.class),
                Arguments.of(FusionBuilderTest.class, FusionConverter.class),
                Arguments.of(InformationBuilderTest.class, InformationConverter.class),
                Arguments.of(RuleBuilderTest.class, MainTypeConverter.class),
                Arguments.of(MultiValueBuilderTest.class, MultiValueConverter.class),
                Arguments.of(PositionalFeatureBuilderTest.class, PositionalFeatureConverter.class),
                Arguments.of(
                        PositionFeatureSetBuilderTest.class, PositionalFeatureSetConverter.class),
                Arguments.of(ProteinDescriptionBuilderTest.class, ProteinConverter.class),
                Arguments.of(RangeTest.class, RangeConverter.class),
                Arguments.of(
                        AnnotationRuleExceptionBuilderTest.class, RuleExceptionConverter.class),
                Arguments.of(
                        PositionalRuleExceptionBuilderTest.class, RuleExceptionConverter.class),
                Arguments.of(RuleStatusTest.class, RuleStatusConverter.class),
                Arguments.of(SamFeatureSetBuilderTest.class, SamFeatureSetConverter.class),
                Arguments.of(SamTriggerBuilderTest.class, SamTriggerConverter.class),
                Arguments.of(
                        UniProtKBAccessionBuilderTest.class, UniProtKBAccessionConverter.class),
                Arguments.of(UniRuleEntryBuilderTest.class, UniRuleEntryConverter.class));
    }

    static Stream<Arguments> provideConverterAndXMLClass() {
        return Stream.of(Arguments.of(AnnotationConverter.class, AnnotationType.class));
    }

    private boolean canSkip(PropertyDescriptor pd) {
        return pd.getReadMethod() != null
                && !"class".equals(pd.getName())
                && !skippedJaxbFields.contains(pd.getName());
    }
}
