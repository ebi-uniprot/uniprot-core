package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.builder.AnnotationBuilder;
import org.uniprot.core.unirule.builder.ConditionBuilder;
import org.uniprot.core.unirule.builder.PositionalFeatureBuilder;

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
        Condition condition1 = new ConditionBuilder().type("type1").build();
        Condition condition2 = new ConditionBuilder().type("type2").build();
        Condition condition3 = new ConditionBuilder().type("type3").build();
        List<Condition> conditions = Arrays.asList(condition1, condition2, condition3);

        Annotation annotation1 = new AnnotationBuilder().build();
        Annotation annotation2 = new AnnotationBuilder().build();
        List<Annotation> annotations = Arrays.asList(annotation1, annotation2);

        PositionalFeature feature1 = new PositionalFeatureBuilder().value("f1").build();
        PositionalFeature feature2 = new PositionalFeatureBuilder().value("f2").build();
        PositionalFeature feature3 = new PositionalFeatureBuilder().value("f3").build();
        List<PositionalFeature> positionalFeatures = Arrays.asList(feature1, feature2, feature3);

        String note = "sample note";
        String category = "sample category";
        String accessionValue = "P12345";
        Annotation annotation = new AnnotationBuilder().build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder(accessionValue).build();
        List<UniProtKBAccession> accessionList = Arrays.asList(accession);
        RuleException<Annotation> ruleException1 =
                new AnnotationRuleExceptionImpl(note, category, annotation, accessionList);

        PositionalFeature positionalFeature = new PositionalFeatureBuilder().build();
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
