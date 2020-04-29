package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.jvnet.jaxb2_commons.lang.Equals2;
import org.uniprot.core.RangeTest;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilderTest;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentImpl;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilderTest;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.unirule.impl.*;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.*;
import org.uniprot.core.xml.jaxb.unirule.*;

public class UniRuleConvertersTest extends AbstractConverterTest {
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
        verifyBusinessConstraints(xmlObject);
        verifyBean(xmlObject);
    }

    @DisplayName("Test convert xmlObject to uniObject")
    @ParameterizedTest(name = "[{index}] using converter ''{0}''")
    @MethodSource("provideConverterXMLAndTestClass")
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

    private void verifyBusinessConstraints(Object xmlObject) {
        // inject some value to satisfy the test. e.g. annotation and positionalfeature are mutually
        // exclusive
        if (xmlObject instanceof RuleExceptionType) {
            if (Objects.isNull(((RuleExceptionType) xmlObject).getPositionalFeature())) {
                assertNotNull(((RuleExceptionType) xmlObject).getAnnotation());
                ((RuleExceptionType) xmlObject)
                        .setPositionalFeature(uniRuleObjectFactory.createPositionalFeatureType());
            } else if (Objects.isNull(((RuleExceptionType) xmlObject).getAnnotation())) {
                assertNotNull(((RuleExceptionType) xmlObject).getPositionalFeature());
                ((RuleExceptionType) xmlObject)
                        .setAnnotation(uniRuleObjectFactory.createAnnotationType());
            }
        } else if (xmlObject instanceof SamTriggerType) { // one field should be set
            boolean isOneSet =
                    (Objects.isNull(((SamTriggerType) xmlObject).getCoiledCoil())
                            || Objects.isNull(((SamTriggerType) xmlObject).getSignal())
                            || Objects.isNull(((SamTriggerType) xmlObject).getTransmembrane()));
            assertTrue(isOneSet, "one of the SamTriggerType should be set");
            ((SamTriggerType) xmlObject)
                    .setTransmembrane(uniRuleObjectFactory.createSamTransmembraneConditionType());
            ((SamTriggerType) xmlObject)
                    .setSignal(uniRuleObjectFactory.createSamSignalConditionType());
            ((SamTriggerType) xmlObject)
                    .setCoiledCoil(uniRuleObjectFactory.createSamCoiledCoilConditionType());
        } else if (xmlObject instanceof CommentType) {
            CommentType commentType = objectCreator.createLoremIpsumObject(CommentType.class);
            CommentType convertedComment = (CommentType) xmlObject;
            convertedComment.setAbsorption(commentType.getAbsorption());
            convertedComment
                    .getCofactor()
                    .add(objectCreator.createLoremIpsumObject(CofactorType.class));
            convertedComment.setConflict(commentType.getConflict());
            convertedComment.setKinetics(
                    objectCreator.createLoremIpsumObject(CommentType.Kinetics.class));
            convertedComment.setPhDependence(
                    objectCreator.createLoremIpsumObject(CommentType.PhDependence.class));
            convertedComment.setRedoxPotential(
                    objectCreator.createLoremIpsumObject(CommentType.RedoxPotential.class));
            convertedComment.setTemperatureDependence(
                    objectCreator.createLoremIpsumObject(CommentType.TemperatureDependence.class));
            convertedComment.setReaction(objectCreator.createLoremIpsumObject(ReactionType.class));
            convertedComment
                    .getPhysiologicalReaction()
                    .add(objectCreator.createLoremIpsumObject(PhysiologicalReactionType.class));
            convertedComment
                    .getLink()
                    .add(objectCreator.createLoremIpsumObject(CommentType.Link.class));
            convertedComment.getEvent().add(objectCreator.createLoremIpsumObject(EventType.class));
            convertedComment
                    .getIsoform()
                    .add(objectCreator.createLoremIpsumObject(IsoformType.class));
            convertedComment.setError("error");
            convertedComment.setExperiments(12);
            convertedComment
                    .getInteractant()
                    .add(objectCreator.createLoremIpsumObject(InteractantType.class));
            convertedComment.setOrganismsDiffer(true);
            convertedComment.setLocationType("loc");
            convertedComment.setName("dummy");
            convertedComment.setMass(12.0f);
            convertedComment.setMethod("method");
            convertedComment
                    .getLocation()
                    .add(objectCreator.createLoremIpsumObject(LocationType.class));
            convertedComment
                    .getSubcellularLocation()
                    .add(objectCreator.createLoremIpsumObject(SubcellularLocationType.class));
        }
    }

    private static void verifyBean(Object xmlObject) throws Exception {
        for (PropertyDescriptor pd :
                Introspector.getBeanInfo(xmlObject.getClass()).getPropertyDescriptors()) {
            if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                Object fieldVal = pd.getReadMethod().invoke(xmlObject);
                assertNotNull(
                        fieldVal, xmlObject.getClass() + " field `" + pd.getName() + "` is null");
                if (fieldVal instanceof Collection) {
                    assertFalse(
                            ((List) fieldVal).isEmpty(),
                            xmlObject.getClass() + " field `" + pd.getName() + " is empty");
                } else if (fieldVal instanceof Map) {
                    assertFalse(((Map) fieldVal).isEmpty(), pd.getName() + " is empty");
                }
            }
        }
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
                Arguments.of(DiseaseCommentBuilderTest.class, CommentConverter.class),
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

    static Stream<Arguments> provideConverterXMLAndTestClass() {
        return Stream.of(
                Arguments.of(
                        AnnotationConverter.class,
                        AnnotationConverterTest.class,
                        AnnotationType.class),
                Arguments.of(CaseTypeConverter.class, CaseTypeConverterTest.class, CaseType.class),
                Arguments.of(CommentConverter.class, CommentConverterTest.class, CommentType.class),
                Arguments.of(
                        ConditionConverter.class, ConditionConverterTest.class, Condition.class),
                Arguments.of(
                        ConditionSetConverter.class,
                        ConditionSetConverterTest.class,
                        ConditionSetType.class),
                Arguments.of(
                        ConditionValueConverter.class,
                        ConditionValueConverterTest.class,
                        ConditionValue.class),
                Arguments.of(FusionConverter.class, FusionConverterTest.class, FusionType.class),
                Arguments.of(
                        FtagConditionConverter.class,
                        FtagConditionConverterTest.class,
                        FtagConditionValue.class),
                Arguments.of(
                        InformationConverter.class,
                        InformationConverterTest.class,
                        InformationType.class),
                Arguments.of(MainTypeConverter.class, MainTypeConverterTest.class, MainType.class),
                Arguments.of(
                        MultiValueConverter.class,
                        MultiValueConverterTest.class,
                        MultiValueType.class),
                Arguments.of(
                        PositionalFeatureConverter.class,
                        PositionalFeatureConverterTest.class,
                        PositionalFeatureType.class),
                Arguments.of(
                        PositionalFeatureSetConverter.class,
                        PositionalFeatureSetConverterTest.class,
                        PositionalFeatureSetType.class),
                Arguments.of(RangeConverter.class, RangeConverterTest.class, RangeType.class),
                Arguments.of(
                        RuleExceptionConverter.class,
                        RuleExceptionConverterTest.class,
                        RuleExceptionType.class),
                Arguments.of(
                        RuleStatusConverter.class,
                        RuleStatusConverterTest.class,
                        RuleStatusType.class),
                Arguments.of(
                        SamFeatureSetConverter.class,
                        SamFeatureSetConverterTest.class,
                        SamFeatureSetType.class),
                Arguments.of(
                        SamTriggerConverter.class,
                        SamTriggerConverterTest.class,
                        SamTriggerType.class),
                Arguments.of(
                        UniRuleEntryConverter.class,
                        UniRuleEntryConverterTest.class,
                        UniRuleEntry.class));
    }
}
