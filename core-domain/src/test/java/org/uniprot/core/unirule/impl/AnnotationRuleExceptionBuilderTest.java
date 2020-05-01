package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.RuleException;

public class AnnotationRuleExceptionBuilderTest {

    @ParameterizedTest
    @NullAndEmptySource
    void testCreateObjectWithNullMandatoryParam(String category) {
        RuleExceptionBuilder<Annotation> builder = new RuleExceptionBuilder<>(category);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testUpdateNote() {
        RuleException<Annotation> ruleException = createObject();
        String oldNote = ruleException.getNote();
        RuleExceptionBuilder<Annotation> builder = RuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        String newNote = "new Note";
        builder.note(newNote);
        RuleException<Annotation> updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertNotEquals(oldNote, updatedRuleException.getNote());
        assertEquals(newNote, updatedRuleException.getNote());
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(ruleException.getAccessions(), updatedRuleException.getAccessions());
    }

    @Test
    void testUpdateCategory() {
        RuleException<Annotation> ruleException = createObject();
        String oldCat = ruleException.getCategory();
        RuleExceptionBuilder<Annotation> builder = RuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        String newCat = "new Cat";
        builder.category(newCat);
        RuleException<Annotation> updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertNotEquals(oldCat, updatedRuleException.getCategory());
        assertEquals(newCat, updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(ruleException.getAccessions(), updatedRuleException.getAccessions());
    }

    @Test
    void testUpdateAnnotation() {
        RuleException<Annotation> ruleException = createObject();
        RuleExceptionBuilder<Annotation> builder = RuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        Annotation newAnn = AnnotationBuilderTest.createObject();
        builder.annotation(newAnn);
        RuleException<Annotation> updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(newAnn, updatedRuleException.getAnnotation());
        assertEquals(ruleException.getAccessions(), updatedRuleException.getAccessions());
    }

    @Test
    void testAppendAccession() {
        RuleException<Annotation> ruleException = createObject();
        List<UniProtKBAccession> oldAccessions = ruleException.getAccessions();
        RuleExceptionBuilder<Annotation> builder = RuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        UniProtKBAccession newAcc = UniProtKBAccessionBuilderTest.createObject();
        builder.accessionsAdd(newAcc);
        RuleException<Annotation> updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(oldAccessions.size() + 1, updatedRuleException.getAccessions().size());
        assertTrue(updatedRuleException.getAccessions().containsAll(oldAccessions));
    }

    @Test
    void testSetAccessions() {
        RuleException<Annotation> ruleException = createObject();
        RuleExceptionBuilder<Annotation> builder = RuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        List<UniProtKBAccession> newAccessions = UniProtKBAccessionBuilderTest.createObjects(4);
        builder.accessionsSet(newAccessions);
        RuleException<Annotation> updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(newAccessions, updatedRuleException.getAccessions());
    }

    public static RuleException<Annotation> createObject() {
        String random = UUID.randomUUID().toString();
        String note = "note-" + random;
        String cat = "cat-" + random;
        Annotation annotation = AnnotationBuilderTest.createObject();
        List<UniProtKBAccession> accessions = UniProtKBAccessionBuilderTest.createObjects(3);
        RuleExceptionBuilder<Annotation> builder = new RuleExceptionBuilder(cat);
        builder.note(note).annotation(annotation).accessionsSet(accessions);
        RuleException<Annotation> ruleException = builder.build();
        assertNotNull(ruleException);
        assertEquals(cat, ruleException.getCategory());
        assertEquals(note, ruleException.getNote());
        assertEquals(accessions, ruleException.getAccessions());
        assertEquals(annotation, ruleException.getAnnotation());
        return ruleException;
    }

    public static List<RuleException<Annotation>> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
