package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.builder.AnnotationBuilder;

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
        String note = "sample note";
        String category = "sample category";
        String accessionValue = "P12345";
        Annotation annotation = new AnnotationBuilder().build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder(accessionValue).build();
        List<UniProtKBAccession> accessionList = Arrays.asList(accession);
        RuleException<Annotation> ruleException =
                new AnnotationRuleExceptionImpl(note, category, annotation, accessionList);
        assertNotNull(ruleException);
        assertEquals(note, ruleException.getNote());
        assertEquals(category, ruleException.getCategory());
        assertEquals(annotation, ruleException.getAnnotation());
        assertEquals(accessionList, ruleException.getAccessions());
    }
}
