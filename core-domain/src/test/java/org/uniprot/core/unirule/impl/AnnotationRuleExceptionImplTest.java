package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.builder.AnnotationBuilderTest;

public class AnnotationRuleExceptionImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        RuleException<Annotation> ruleException = new AnnotationRuleExceptionImpl();
        assertNotNull(ruleException);
        assertTrue(ruleException.getAccessions().isEmpty());
        assertNull(ruleException.getCategory());
        assertNull(ruleException.getNote());
        assertNull(ruleException.getAnnotation());
    }

    @Test
    void testCreateObject() {
        createObject();
    }

    public static RuleException<Annotation> createObject() {
        String random = UUID.randomUUID().toString();
        String note = "sample note" + random;
        String category = "sample category" + random;
        Annotation annotation = AnnotationBuilderTest.createObject();
        List<UniProtKBAccession> accessionList = UniProtKBAccessionBuilderTest.createObjects(3);
        RuleException<Annotation> ruleException =
                new AnnotationRuleExceptionImpl(note, category, annotation, accessionList);
        assertNotNull(ruleException);
        assertEquals(note, ruleException.getNote());
        assertEquals(category, ruleException.getCategory());
        assertEquals(annotation, ruleException.getAnnotation());
        assertEquals(accessionList, ruleException.getAccessions());

        return ruleException;
    }

    public static List<RuleException<Annotation>> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
