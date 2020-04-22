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
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.RuleException;

public class PositionalRuleExceptionBuilderTest {

    @ParameterizedTest
    @NullAndEmptySource
    void testCreateObjectWithNullMandatoryParam(String category) {
        PositionalRuleExceptionBuilder builder = new PositionalRuleExceptionBuilder(category);
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void testUpdateNote() {
        RuleException<PositionalFeature> ruleException = createObject();
        String oldNote = ruleException.getNote();
        PositionalRuleExceptionBuilder builder = PositionalRuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        String newNote = "new Note";
        builder.note(newNote);
        PositionalRuleExceptionImpl updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertNotEquals(oldNote, updatedRuleException.getNote());
        assertEquals(newNote, updatedRuleException.getNote());
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(ruleException.getAccessions(), updatedRuleException.getAccessions());
    }

    @Test
    void testUpdateCategory() {
        RuleException<PositionalFeature> ruleException = createObject();
        String oldCat = ruleException.getCategory();
        PositionalRuleExceptionBuilder builder = PositionalRuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        String newCat = "new Cat";
        builder.category(newCat);
        PositionalRuleExceptionImpl updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertNotEquals(oldCat, updatedRuleException.getCategory());
        assertEquals(newCat, updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(ruleException.getAccessions(), updatedRuleException.getAccessions());
    }

    @Test
    void testUpdateAnnotation() {
        RuleException<PositionalFeature> ruleException = createObject();
        PositionalRuleExceptionBuilder builder = PositionalRuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        PositionalFeature newAnn = PositionalFeatureBuilderTest.createObject();
        builder.annotation(newAnn);
        PositionalRuleExceptionImpl updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(newAnn, updatedRuleException.getAnnotation());
        assertEquals(ruleException.getAccessions(), updatedRuleException.getAccessions());
    }

    @Test
    void testAppendAccession() {
        RuleException<PositionalFeature> ruleException = createObject();
        List<UniProtKBAccession> oldAccessions = ruleException.getAccessions();
        PositionalRuleExceptionBuilder builder = PositionalRuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        UniProtKBAccession newAcc = UniProtKBAccessionBuilderTest.createObject();
        builder.accessionsAdd(newAcc);
        PositionalRuleExceptionImpl updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(oldAccessions.size() + 1, updatedRuleException.getAccessions().size());
        assertTrue(updatedRuleException.getAccessions().containsAll(oldAccessions));
    }

    @Test
    void testSetAccessions() {
        RuleException<PositionalFeature> ruleException = createObject();
        PositionalRuleExceptionBuilder builder = PositionalRuleExceptionBuilder.from(ruleException);
        assertNotNull(builder);
        List<UniProtKBAccession> newAccessions = UniProtKBAccessionBuilderTest.createObjects(4);
        builder.accessionsSet(newAccessions);
        PositionalRuleExceptionImpl updatedRuleException = builder.build();
        assertNotNull(updatedRuleException);
        assertEquals(ruleException.getCategory(), updatedRuleException.getCategory());
        assertEquals(ruleException.getNote(), updatedRuleException.getNote());
        assertEquals(ruleException.getAnnotation(), updatedRuleException.getAnnotation());
        assertEquals(newAccessions, updatedRuleException.getAccessions());
    }

    public static RuleException<PositionalFeature> createObject() {
        String random = UUID.randomUUID().toString();
        String note = "note-" + random;
        String cat = "cat-" + random;
        PositionalFeature positionalFeature = PositionalFeatureBuilderTest.createObject();
        List<UniProtKBAccession> accessions = UniProtKBAccessionBuilderTest.createObjects(3);
        PositionalRuleExceptionBuilder builder = new PositionalRuleExceptionBuilder(cat);
        builder.note(note).annotation(positionalFeature).accessionsSet(accessions);
        RuleException<PositionalFeature> ruleException = builder.build();
        assertNotNull(ruleException);
        assertEquals(cat, ruleException.getCategory());
        assertEquals(note, ruleException.getNote());
        assertEquals(accessions, ruleException.getAccessions());
        assertEquals(positionalFeature, ruleException.getAnnotation());
        return ruleException;
    }

    public static List<RuleException<PositionalFeature>> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
