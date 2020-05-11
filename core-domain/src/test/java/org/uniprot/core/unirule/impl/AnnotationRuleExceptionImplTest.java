package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.RuleException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnnotationRuleExceptionImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        RuleException ruleException = new RuleExceptionImpl();
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

    public static RuleException createObject(int listSize, boolean includeEvidences) {
        String random = UUID.randomUUID().toString();
        String note = "sample note" + random;
        String category = "sample category" + random;
        Annotation annotation = AnnotationBuilderTest.createObject(listSize, includeEvidences);
        List<UniProtKBAccession> accessionList =
                UniProtKBAccessionBuilderTest.createObjects(listSize);
        RuleException ruleException =
                new RuleExceptionImpl(note, category, annotation, accessionList);
        assertNotNull(ruleException);
        assertEquals(note, ruleException.getNote());
        assertEquals(category, ruleException.getCategory());
        assertEquals(annotation, ruleException.getAnnotation());
        assertEquals(accessionList, ruleException.getAccessions());

        return ruleException;
    }

    public static RuleException createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static RuleException createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<RuleException> createObjects(int count) {
        return createObjects(count, false);
    }

    public static List<RuleException> createObjects(int count, boolean includeEvidences) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count, includeEvidences))
                .collect(Collectors.toList());
    }
}
