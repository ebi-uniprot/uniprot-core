package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleException;

public class PositionalRuleExceptionImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        RuleException<PositionalFeature> ruleException = new PositionalRuleExceptionImpl();
        assertNotNull(ruleException);
        assertTrue(ruleException.getAccessions().isEmpty());
        assertNull(ruleException.getCategory());
        assertNull(ruleException.getNote());
        assertNull(ruleException.getAnnotation());
    }

    @Test
    void testCreateObject() {
        String note = "sample note";
        String category = "sample category";
        String accessionValue = "P12345";
        PositionalFeature positionalFeature = new PositionalFeatureBuilder().build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder(accessionValue).build();
        List<UniProtKBAccession> accessionList = Arrays.asList(accession);
        RuleException<PositionalFeature> ruleException =
                new PositionalRuleExceptionImpl(note, category, positionalFeature, accessionList);
        assertNotNull(ruleException);
        assertEquals(note, ruleException.getNote());
        assertEquals(category, ruleException.getCategory());
        assertEquals(positionalFeature, ruleException.getAnnotation());
        assertEquals(accessionList, ruleException.getAccessions());
    }

    public static RuleException<PositionalFeature> createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String note = "sample note" + random;
        String category = "sample category" + random;
        PositionalFeature positionalFeature = PositionalFeatureBuilderTest.createObject(listSize);
        List<UniProtKBAccession> accessionList =
                UniProtKBAccessionBuilderTest.createObjects(listSize);
        RuleException<PositionalFeature> ruleException =
                new PositionalRuleExceptionImpl(note, category, positionalFeature, accessionList);
        assertNotNull(ruleException);
        assertEquals(note, ruleException.getNote());
        assertEquals(category, ruleException.getCategory());
        assertEquals(positionalFeature, ruleException.getAnnotation());
        assertEquals(accessionList, ruleException.getAccessions());

        return ruleException;
    }

    public static RuleException<PositionalFeature> createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<RuleException<PositionalFeature>> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
