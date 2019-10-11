package org.uniprot.core.literature.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createCompleteLiteratureMappedReference;
import static org.uniprot.core.ObjectsForTests.createCompleteLiteratureMappedReferenceWithAdd;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureMappedReference;

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
}
