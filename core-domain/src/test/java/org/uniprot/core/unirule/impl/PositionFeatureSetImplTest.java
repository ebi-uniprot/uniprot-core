package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.*;

public class PositionFeatureSetImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        PositionFeatureSet positionFeatureSet = new PositionFeatureSetImpl();
        assertNotNull(positionFeatureSet);
        assertTrue(positionFeatureSet.getAnnotations().isEmpty());
        assertTrue(positionFeatureSet.getConditions().isEmpty());
        assertTrue(positionFeatureSet.getPositionalFeatures().isEmpty());
        assertTrue(positionFeatureSet.getRuleExceptions().isEmpty());
        assertNull(positionFeatureSet.getAlignmentSignature());
        assertNull(positionFeatureSet.getUniProtKBAccession());
        assertNull(positionFeatureSet.getTag());
    }

    @Test
    void testCreateObject() {
        Condition condition1 = new ConditionBuilder("type1").build();
        Condition condition2 = new ConditionBuilder("type2").build();
        Condition condition3 = new ConditionBuilder("type3").build();
        List<Condition> conditions = Arrays.asList(condition1, condition2, condition3);

        Annotation annotation1 = new AnnotationBuilder().build();
        Annotation annotation2 = new AnnotationBuilder().build();
        List<Annotation> annotations = Arrays.asList(annotation1, annotation2);
        Range position = new Range(1, 2);
        PositionalFeature feature1 = new PositionalFeatureBuilder(position).value("f1").build();
        PositionalFeature feature2 = new PositionalFeatureBuilder(position).value("f2").build();
        PositionalFeature feature3 = new PositionalFeatureBuilder(position).value("f3").build();
        List<PositionalFeature> positionalFeatures = Arrays.asList(feature1, feature2, feature3);

        String note = "sample note";
        String category = "sample category";
        String accessionValue = "P12345";
        Annotation annotation = new AnnotationBuilder().build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder(accessionValue).build();
        List<UniProtKBAccession> accessionList = Arrays.asList(accession);
        RuleException<Annotation> ruleException1 =
                new AnnotationRuleExceptionImpl(note, category, annotation, accessionList);
        Range position1 = new Range(1, 2);
        PositionalFeature positionalFeature = new PositionalFeatureBuilder(position1).build();
        RuleException<PositionalFeature> ruleException2 =
                new PositionalRuleExceptionImpl(note, category, positionalFeature, accessionList);
        List<RuleException> ruleExceptions = Arrays.asList(ruleException1, ruleException2);

        UniProtKBAccession uniProtKBAccession = new UniProtKBAccessionBuilder("P12345").build();
        String alignmentSignature = "sample alignment signature";
        String tag = "sample tag";

        PositionFeatureSet positionFeatureSet =
                new PositionFeatureSetImpl(
                        conditions,
                        annotations,
                        positionalFeatures,
                        ruleExceptions,
                        uniProtKBAccession,
                        alignmentSignature,
                        tag);

        assertNotNull(positionFeatureSet);
        assertEquals(conditions, positionFeatureSet.getConditions());
        assertEquals(annotations, positionFeatureSet.getAnnotations());
        assertEquals(positionalFeatures, positionFeatureSet.getPositionalFeatures());
        assertEquals(ruleExceptions, positionFeatureSet.getRuleExceptions());
        assertEquals(uniProtKBAccession, positionFeatureSet.getUniProtKBAccession());
        assertEquals(alignmentSignature, positionFeatureSet.getAlignmentSignature());
        assertEquals(tag, positionFeatureSet.getTag());
    }
}
