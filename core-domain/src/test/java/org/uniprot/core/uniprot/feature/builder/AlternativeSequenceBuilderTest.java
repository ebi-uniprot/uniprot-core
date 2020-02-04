package org.uniprot.core.uniprot.feature.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.feature.AlternativeSequence;

class AlternativeSequenceBuilderTest {

    @Test
    void canSetOriginalFromBuilder() {
        AlternativeSequence obj = new AlternativeSequenceBuilder().original("org").build();
        assertEquals("org", obj.getOriginalSequence());
    }

    @Test
    void nullWillIgnoreInAlternatives() {
        AlternativeSequence obj =
                new AlternativeSequenceBuilder().alternativeSequencesAdd(null).build();
        assertTrue(obj.getAlternativeSequences().isEmpty());
    }

    @Test
    void canAddSingleAlternative() {
        AlternativeSequence obj =
                new AlternativeSequenceBuilder().alternativeSequencesAdd("seq").build();
        assertEquals("seq", obj.getAlternativeSequences().get(0));
    }

    @Test
    void canAddListOfAlternatives() {
        AlternativeSequence obj =
                new AlternativeSequenceBuilder()
                        .alternativeSequencesSet(Arrays.asList("1", "2", "3"))
                        .build();
        assertEquals(3, obj.getAlternativeSequences().size());
    }

    @Test
    void canCreateBuilderFromInstance() {
        AlternativeSequence obj = new AlternativeSequenceBuilder().build();
        AlternativeSequenceBuilder builder = AlternativeSequenceBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        AlternativeSequence obj = new AlternativeSequenceBuilder().build();
        AlternativeSequence obj2 = new AlternativeSequenceBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
