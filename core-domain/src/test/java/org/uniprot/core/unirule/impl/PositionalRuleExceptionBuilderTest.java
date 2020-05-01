package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleException;

public class PositionalRuleExceptionBuilderTest {

    public static RuleException createObject() {
        String random = UUID.randomUUID().toString();
        String note = "note-" + random;
        String cat = "cat-" + random;
        PositionalFeature positionalFeature = PositionalFeatureBuilderTest.createObject();
        List<UniProtKBAccession> accessions = UniProtKBAccessionBuilderTest.createObjects(3);
        RuleExceptionBuilder builder = new RuleExceptionBuilder(cat);
        builder.note(note).annotation(positionalFeature).accessionsSet(accessions);
        RuleException ruleException = builder.build();
        assertNotNull(ruleException);
        assertEquals(cat, ruleException.getCategory());
        assertEquals(note, ruleException.getNote());
        assertEquals(accessions, ruleException.getAccessions());
        assertEquals(positionalFeature, ruleException.getAnnotation());
        return ruleException;
    }

    public static List<RuleException> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
