package org.uniprot.core.literature.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

class LiteratureMappedReferenceBuilderTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureMappedReference mappedReference = new LiteratureMappedReferenceBuilder().build();
        assertFalse(mappedReference.hasAnnotation());
        assertFalse(mappedReference.hasSource());
        assertFalse(mappedReference.hasSourceCategory());
        assertFalse(mappedReference.hasSourceId());
        assertFalse(mappedReference.hasUniprotAccession());
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureMappedReference mappedReference = createCompleteLiteratureMappedReference();
        validateLiteratureMappedReference(mappedReference);
    }

    @Test
    void testCompleteLiteratureEntryWithAdd() {
        LiteratureMappedReference mappedReference =
                createCompleteLiteratureMappedReferenceWithAdd();
        validateLiteratureMappedReference(mappedReference);
    }

    private void validateLiteratureMappedReference(LiteratureMappedReference mappedReference) {
        assertTrue(mappedReference.hasAnnotation());
        assertEquals(mappedReference.getAnnotation(), "annotation value");

        assertTrue(mappedReference.hasSource());
        assertEquals(mappedReference.getSource(), "source value");

        assertTrue(mappedReference.hasSourceCategory());
        MatcherAssert.assertThat(
                mappedReference.getSourceCategory(), Matchers.contains("source category"));

        assertTrue(mappedReference.hasSourceId());
        assertEquals(mappedReference.getSourceId(), "source Id");

        assertTrue(mappedReference.hasUniprotAccession());
        assertEquals(mappedReference.getUniprotAccession().getValue(), "P12345");
    }

    static LiteratureMappedReference createCompleteLiteratureMappedReference() {
        return getBasicFields()
                .sourceCategory(Collections.singletonList("source category"))
                .uniprotAccession("P12345")
                .build();
    }

    static LiteratureMappedReference createCompleteLiteratureMappedReferenceWithAdd() {
        return getBasicFields()
                .addSourceCategory("source category")
                .uniprotAccession(new UniProtAccessionImpl("P12345"))
                .build();
    }

    private static LiteratureMappedReferenceBuilder getBasicFields() {
        return new LiteratureMappedReferenceBuilder()
                .annotation("annotation value")
                .source("source value")
                .sourceId("source Id");
    }
}
