package org.uniprot.core.unirule.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.AnnotationRuleExceptionImplTest;

public class PositionFeatureSetBuilderTest {

    public static PositionFeatureSet createObject() {
        String random = UUID.randomUUID().toString();
        String alignmentSignature = "alignmentSignature-" + random;
        String tag = "tag-" + random;
        List<Condition> conditions = ConditionBuilderTest.createObjects(2);
        List<Annotation> annotations = AnnotationBuilderTest.createObjects(3);
        List<PositionalFeature> positionalFeatures = PositionalFeatureBuilderTest.createObjects(1);
        List<RuleException<Annotation>> ruleExceptions =
                AnnotationRuleExceptionImplTest.createObjects(3);
        UniProtKBAccession uniProtKBAccession = UniProtKBAccessionBuilderTest.createObject();

        PositionFeatureSetBuilder<Annotation> builder = new PositionFeatureSetBuilder<>();
        builder.alignmentSignature(alignmentSignature).tag(tag);
        builder.conditionsSet(conditions).annotationsSet(annotations);
        builder.ruleExceptionsSet(ruleExceptions).positionalFeaturesSet(positionalFeatures);
        builder.uniProtKBAccession(uniProtKBAccession);

        PositionFeatureSet positionFeatureSet = builder.build();
        assertNotNull(positionFeatureSet);
        assertEquals(alignmentSignature, positionFeatureSet.getAlignmentSignature());
        assertEquals(tag, positionFeatureSet.getTag());
        assertEquals(conditions, positionFeatureSet.getConditions());
        assertEquals(annotations, positionFeatureSet.getAnnotations());
        assertEquals(positionalFeatures, positionFeatureSet.getPositionalFeatures());
        assertEquals(ruleExceptions, positionFeatureSet.getRuleExceptions());
        assertEquals(uniProtKBAccession, positionFeatureSet.getUniProtKBAccession());

        return positionFeatureSet;
    }

    public static List<PositionFeatureSet> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
