package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.builder.PositionalFeatureBuilder;

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
}
