package org.uniprot.core.unirule.builder;

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
import org.uniprot.core.unirule.impl.*;

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
        createObjectMethod.invoke(null);
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
                Arguments.of(UniRuleIdBuilderTest.class));
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
                Arguments.of(UniRuleId.class, UniRuleIdBuilder.class, UniRuleIdBuilderTest.class));
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
