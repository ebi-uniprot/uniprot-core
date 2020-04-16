package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.uniprot.core.Builder;
import org.uniprot.core.unirule.*;

/** @author sahmad */
public class BuilderCommonTest {

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
                } else {
                    assertNull(fieldVal, pd.getName() + " is not null");
                }
            }
        }
    }

    @DisplayName("Test full object creation")
    @ParameterizedTest(name = "[{index}] in ''{0}''")
    @MethodSource("provideTestClassWithCreateObjectMethod")
    void testCreateFullObject(Class testClass) throws Exception {
        Method createObjectMethod = testClass.getMethod("createObject");
        Object object = createObjectMethod.invoke(null);
        assertNotNull(object);
    }

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

    @DisplayName("Test add null object to list")
    @ParameterizedTest(name = "[{index}] fields of  ''{0}''")
    @MethodSource("provideBuilderObjectClass")
    void testAddNullToList(
            Class<? extends Builder> builderClass, Class<? extends Serializable> objectClass)
            throws Exception {
        // get no-arg builder constructor
        Constructor<? extends Builder> noArgConstructor = builderClass.getConstructor();

        for (Method method : builderClass.getDeclaredMethods()) {
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
                Method getMethod = objectClass.getDeclaredMethod(getMethodName);
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
                Arguments.of(CaseRuleBuilderTest.class));
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
                Arguments.of(ConditionSetBuilder.class, ConditionSetImpl.class),
                Arguments.of(FusionBuilder.class, FusionImpl.class),
                Arguments.of(InformationBuilder.class, InformationImpl.class),
                Arguments.of(PositionalFeatureBuilder.class, PositionalFeatureImpl.class),
                Arguments.of(PositionFeatureSetBuilder.class, PositionFeatureSetImpl.class),
                Arguments.of(SamFeatureSetBuilder.class, SamFeatureSetImpl.class),
                Arguments.of(SamTriggerBuilder.class, SamTriggerImpl.class));
    }
}
