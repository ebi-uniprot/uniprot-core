package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.InternalLine;
import org.uniprot.core.uniprot.InternalLineType;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.SourceLine;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.impl.EvidenceLineBuilder;

class InternalSectionBuilderTest {

    @Test
    void nullInternalLine_willBeIgnore() {
        InternalSection internalSection =
                new InternalSectionBuilder().internalLinesAdd(null).build();
        assertTrue(internalSection.getInternalLines().isEmpty());
    }

    @Test
    void singleInternalLine_canBeAdded() {
        InternalLine internalLine = new InternalLineBuilder(InternalLineType.PE, "abc").build();
        InternalSection internalSection =
                new InternalSectionBuilder().internalLinesAdd(internalLine).build();
        assertEquals(internalLine, internalSection.getInternalLines().get(0));
    }

    @Test
    void nullEvidence_willBeIgnore() {
        InternalSection internalSection =
                new InternalSectionBuilder().evidenceLinesAdd(null).build();
        assertTrue(internalSection.getInternalLines().isEmpty());
    }

    @Test
    void singleEvidenceLine_canBeAdded() {
        EvidenceLine evdLine = new EvidenceLineBuilder().build();
        InternalSection internalSection =
                new InternalSectionBuilder().evidenceLinesAdd(evdLine).build();
        assertEquals(evdLine, internalSection.getEvidenceLines().get(0));
    }

    @Test
    void nullSourceLine_willBeIgnore() {
        InternalSection internalSection = new InternalSectionBuilder().sourceLinesAdd(null).build();
        assertTrue(internalSection.getInternalLines().isEmpty());
    }

    @Test
    void singleSourceLine_canBeAdded() {
        SourceLine line = new SourceLineBuilder("line").build();
        InternalSection internalSection = new InternalSectionBuilder().sourceLinesAdd(line).build();
        assertEquals(line, internalSection.getSourceLines().get(0));
    }

    @Test
    void canCreateBuilderFromInstance() {
        InternalSection obj = new InternalSectionBuilder().build();
        InternalSectionBuilder builder = InternalSectionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        InternalSection obj = new InternalSectionBuilder().build();
        InternalSection obj2 = new InternalSectionBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
