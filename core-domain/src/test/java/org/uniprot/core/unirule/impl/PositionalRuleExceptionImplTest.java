package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PositionalRuleExceptionImplTest {
    @Test
    void testCreateObject() {
        String note = "sample note";
        String category = "sample category";
        String accessionValue = "P12345";
        Range position = new Range(1, 2);
        PositionalFeature positionalFeature = new PositionalFeatureBuilder(position).build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder(accessionValue).build();
        List<UniProtKBAccession> accessionList = Arrays.asList(accession);
        RuleException ruleException =
                new RuleExceptionImpl(note, category, positionalFeature, accessionList);
        assertNotNull(ruleException);
        assertEquals(note, ruleException.getNote());
        assertEquals(category, ruleException.getCategory());
        assertEquals(positionalFeature, ruleException.getAnnotation());
        assertEquals(accessionList, ruleException.getAccessions());
    }

    public static RuleException createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String note = "sample note" + random;
        String category = "sample category" + random;
        PositionalFeature positionalFeature = PositionalFeatureBuilderTest.createObject(listSize);
        List<UniProtKBAccession> accessionList =
                UniProtKBAccessionBuilderTest.createObjects(listSize);
        RuleException ruleException =
                new RuleExceptionImpl(note, category, positionalFeature, accessionList);
        assertNotNull(ruleException);
        assertEquals(note, ruleException.getNote());
        assertEquals(category, ruleException.getCategory());
        assertEquals(positionalFeature, ruleException.getAnnotation());
        assertEquals(accessionList, ruleException.getAccessions());

        return ruleException;
    }

    public static RuleException createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<RuleException> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
