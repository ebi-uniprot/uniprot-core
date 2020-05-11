package org.uniprot.core.xml.unirule;

import org.junit.jupiter.params.provider.Arguments;
import org.uniprot.core.RangeTest;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilderTest;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilderTest;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.unirule.impl.*;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.util.stream.Stream;

public class UniRuleConvertersTestHelper {
    public static Stream<Arguments> provideConverterClass() {
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

    public static Stream<Arguments> provideBuilderTestAndConverterClass() {
        return Stream.of(
                Arguments.of(AnnotationBuilderTest.class, AnnotationConverter.class),
                Arguments.of(CaseRuleBuilderTest.class, CaseTypeConverter.class),
                Arguments.of(FreeTextCommentBuilderTest.class, CommentConverter.class),
                Arguments.of(ConditionBuilderTest.class, ConditionConverter.class),
                Arguments.of(ConditionSetBuilderTest.class, ConditionSetConverter.class),
                Arguments.of(ConditionValueBuilderTest.class, ConditionValueConverter.class),
                Arguments.of(
                        FeatureTagConditionValueBuilderTest.class, FtagConditionConverter.class),
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

    public static Stream<Arguments> provideConverterXMLAndTestClass() {
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
