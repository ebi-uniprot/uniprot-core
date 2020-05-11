package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.uniprot.core.Builder;
import org.uniprot.core.unirule.*;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/** @author sahmad */
public class BuilderCommonTest {

    /**
     * Test creation of object via Builder's no-arg constructor. To test a new builder just add the
     * following line in method {@link #provideBuilderObjectClass} <code>
     * Arguments.of(ExampleBuilder.class, ExampleImpl.class)</code>
     *
     * @param builderClass Builder's Class
     * @param objectClass XYZImpl class
     * @throws Exception
     */
    @DisplayName("Test skinny object creation")
    @ParameterizedTest(name = "[{index}] of type ''{1}''  by ''{0}''")
    @MethodSource("provideBuilderObjectClass")
    void testCreateSkinnyObject(
            Class<? extends Builder> builderClass, Class<? extends Serializable> objectClass)
            throws Exception {
        // get no-arg builder constructor
        Constructor<? extends Builder> noArgConstructor = builderClass.getConstructor();
        // instantiate the object
        Builder builder = noArgConstructor.newInstance();
        Object object = builder.build();
        assertNotNull(object);
        assertEquals(objectClass, object.getClass());
        // test field values
        for (PropertyDescriptor pd :
                Introspector.getBeanInfo(objectClass).getPropertyDescriptors()) {
            if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                Object fieldVal = pd.getReadMethod().invoke(object);
                if (fieldVal instanceof Collection) {
                    assertTrue(((List) fieldVal).isEmpty(), pd.getName() + " is not empty");
                } else if (fieldVal instanceof Map) {
                    assertTrue(((Map) fieldVal).isEmpty(), pd.getName() + " is not empty");
                } else if (fieldVal instanceof Boolean) {
                    assertFalse((Boolean) fieldVal, pd.getName() + " is not false");
                } else if (!(fieldVal instanceof RuleExceptionAnnotationType)) {
                    assertNull(fieldVal, pd.getName() + " is not null");
                }
            }
        }
    }

    /**
     * Test creation of object with all the attributes set.
     *
     * @param testClass The Class of the class with the helper method
     *     <p>createObject where full object is being created. e.g. see {@link
     *     InformationBuilderTest#createObject} Add the class with method createObject in {@link
     *     #provideTestClassWithCreateObjectMethod}
     * @throws Exception
     */
    @DisplayName("Test full object creation")
    @ParameterizedTest(name = "[{index}] in ''{0}''")
    @MethodSource("provideTestClassWithCreateObjectMethod")
    void testCreateFullObject(Class testClass) throws Exception {
        Method createObjectMethod = testClass.getMethod("createObject");
        Object object = createObjectMethod.invoke(null);
        assertNotNull(object);
    }

    /**
     * Test object creation via constructor and from method of a Builder class e.g. {@link
     * UniRuleEntryBuilder#from}
     *
     * @param interfaceType The Class of the interface which is implemented by the class returned by
     *     {@link Builder#build()} method
     * @param builderClass The Class of the builder class
     * @param testClass The test Class where createObject is implemented See {@link
     *     #provideTypeBuilderTestClass} to test new Builder class
     * @throws Exception
     */
    @DisplayName("Test object creation by from method")
    @ParameterizedTest(name = "[{index}] of type ''{0}''")
    @MethodSource("provideTypeBuilderTestClass")
    void testCreateObjectByFrom(
            Class<? extends Serializable> interfaceType,
            Class<? extends Builder> builderClass,
            Class testClass)
            throws Exception {
        // get method which creates full object and test it
        Method createObjectMethod = testClass.getMethod("createObject");
        Object object = createObjectMethod.invoke(null);
        assertNotNull(object);
        // invoke from method and test the returned value
        Method fromMethod = builderClass.getMethod("from", interfaceType);
        Builder builderObject = (Builder) fromMethod.invoke(null, object);
        Object objectByBuilder = builderObject.build();
        assertEquals(object, objectByBuilder);
        assertEquals(object.hashCode(), objectByBuilder.hashCode());
        assertNotSame(object, objectByBuilder);
    }

    /**
     * Test adding null object in a list field of a builder class. Make sure the you follow Java
     * Bean naming convention in model class and our conventions in builder class(<listNames>Add)
     *
     * @param builderClass The builder Class where the list field is defined
     * @param objectClass The corresponding object Class for which builder is creating object See
     *     {@link #provideBuilderObjectClass} to test new Builder class
     * @throws Exception
     */
    @DisplayName("Test add null object to list")
    @ParameterizedTest(name = "[{index}] fields of  ''{0}''")
    @MethodSource("provideBuilderObjectClass")
    void testAddNullToList(
            Class<? extends Builder> builderClass, Class<? extends Serializable> objectClass)
            throws Exception {
        // get no-arg builder constructor
        Constructor<? extends Builder> noArgConstructor = builderClass.getConstructor();

        for (Method method : builderClass.getMethods()) {
            if (method.getName().endsWith("Add")) {
                // instantiate the builder class
                Builder builder = noArgConstructor.newInstance();
                Object arg = null;
                // call the <fieldName>Add method of builder class with null value
                builder = (Builder) method.invoke(builder, arg);
                Object object = builder.build(); // create the object
                assertNotNull(object, "object is null for type " + objectClass.getName());
                // get the builder's field name of type list
                String fieldName = method.getName().substring(0, method.getName().indexOf("Add"));
                String getMethodName = fieldToGetter(fieldName);
                Method getMethod = objectClass.getMethod(getMethodName);
                assertNotNull(getMethod, getMethodName + " doesn't exist");
                Object list = getMethod.invoke(object);
                assertTrue(list instanceof List, fieldName + " is not type of List");
                assertNotNull(list, fieldName + " is null");
                assertTrue(((List) list).isEmpty(), fieldName + " is not empty");
                // try to add item to immutable list it should throw UnsupportedOperationException
                assertThrows(
                        UnsupportedOperationException.class,
                        () -> ((List) list).add(new Object()),
                        fieldName + " is not immutable");
            }
        }
    }

    private String fieldToGetter(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    static Stream<Arguments> provideTestClassWithCreateObjectMethod() {
        return Stream.of(
                Arguments.of(AnnotationBuilderTest.class),
                Arguments.of(ConditionBuilderTest.class),
                Arguments.of(ConditionSetBuilderTest.class),
                Arguments.of(ConditionValueBuilderTest.class),
                Arguments.of(FusionBuilderTest.class),
                Arguments.of(InformationBuilderTest.class),
                Arguments.of(PositionalFeatureBuilderTest.class),
                Arguments.of(PositionFeatureSetBuilderTest.class),
                Arguments.of(RuleBuilderTest.class),
                Arguments.of(SamFeatureSetBuilderTest.class),
                Arguments.of(SamTriggerBuilderTest.class),
                Arguments.of(UniRuleEntryBuilderTest.class),
                Arguments.of(UniRuleIdBuilderTest.class),
                Arguments.of(CaseRuleBuilderTest.class),
                Arguments.of(AnnotationRuleExceptionBuilderTest.class),
                Arguments.of(PositionalRuleExceptionBuilderTest.class));
    }

    static Stream<Arguments> provideTypeBuilderTestClass() {
        return Stream.of(
                Arguments.of(
                        Annotation.class, AnnotationBuilder.class, AnnotationBuilderTest.class),
                Arguments.of(Condition.class, ConditionBuilder.class, ConditionBuilderTest.class),
                Arguments.of(
                        ConditionSet.class,
                        ConditionSetBuilder.class,
                        ConditionSetBuilderTest.class),
                Arguments.of(
                        ConditionValue.class,
                        ConditionValueBuilder.class,
                        ConditionValueBuilderTest.class),
                Arguments.of(Fusion.class, FusionBuilder.class, FusionBuilderTest.class),
                Arguments.of(
                        Information.class, InformationBuilder.class, InformationBuilderTest.class),
                Arguments.of(
                        PositionalFeature.class,
                        PositionalFeatureBuilder.class,
                        PositionalFeatureBuilderTest.class),
                Arguments.of(
                        PositionFeatureSet.class,
                        PositionFeatureSetBuilder.class,
                        PositionFeatureSetBuilderTest.class),
                Arguments.of(Rule.class, RuleBuilder.class, RuleBuilderTest.class),
                Arguments.of(
                        SamFeatureSet.class,
                        SamFeatureSetBuilder.class,
                        SamFeatureSetBuilderTest.class),
                Arguments.of(
                        SamTrigger.class, SamTriggerBuilder.class, SamTriggerBuilderTest.class),
                Arguments.of(
                        UniRuleEntry.class,
                        UniRuleEntryBuilder.class,
                        UniRuleEntryBuilderTest.class),
                Arguments.of(UniRuleId.class, UniRuleIdBuilder.class, UniRuleIdBuilderTest.class),
                Arguments.of(CaseRule.class, CaseRuleBuilder.class, CaseRuleBuilderTest.class));
    }

    static Stream<Arguments> provideBuilderObjectClass() {
        return Stream.of(
                Arguments.of(AnnotationBuilder.class, AnnotationImpl.class),
                Arguments.of(FusionBuilder.class, FusionImpl.class),
                Arguments.of(SamTriggerBuilder.class, SamTriggerImpl.class));
    }
}
